package org.example.controller;

import org.example.domain.model.User;
import org.example.domain.model.UserRole;
import org.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testListUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(new User())); // Adjust as necessary

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    public void testChangeUserRole() throws Exception {
        when(userService.userExists(anyInt())).thenReturn(true);

        mockMvc.perform(put("/api/users/1/role")
                        .param("newRole", "ADMIN"))
                .andExpect(status().isOk())
                .andExpect(content().string("User role is updated."));

        verify(userService, times(1)).updateUserRole(anyInt(), any(UserRole.class));
    }

    @Test
    public void testAddUser() throws Exception {
        User user = new User(); // Populate as necessary

        mockMvc.perform(post("/api/users")
                        .contentType("application/json")
                        .content("{ \"username\": \"test\", \"password\": \"pass\", \"email\": \"test@example.com\", \"age\": 30, \"role\": \"USER\" }"))
                .andExpect(status().isCreated())
                .andExpect(content().string("User is added."));

        verify(userService, times(1)).addUser(anyString(), anyString(), anyString(), anyInt(), any(UserRole.class));
    }

    @Test
    public void testRemoveUser() throws Exception {
        when(userService.userExists(anyInt())).thenReturn(true);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(anyInt());
    }
}
