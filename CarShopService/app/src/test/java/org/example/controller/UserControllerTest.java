package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.dto.UserDTO;
import org.example.domain.model.User;
import org.example.domain.model.UserRole;
import org.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("order controller test")
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    @Autowired
    private UserController userController;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testListUsers() throws Exception {
        when(userService.getAll()).thenReturn(List.of(new User())); // Adjust as necessary

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    @DisplayName("test get user by id")
    void getById() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Kolya")));
    }

    @Test
    @DisplayName("test update user data")
    void update() throws Exception {
        UserDTO userDTO = new UserDTO("Kolya", 33, "Kolya@gmail.com", UserRole.CUSTOMER);
        String userJson = objectMapper.writeValueAsString(userDTO);
        mockMvc.perform(put("/users/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("Kolya")));
    }
}
