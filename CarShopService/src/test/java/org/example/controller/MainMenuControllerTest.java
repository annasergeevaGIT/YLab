package org.example.controller;

import org.example.domain.dto.UserDTO;
import org.example.domain.model.UserRole;
import org.example.service.AuthServiceJdbc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MainMenuControllerTest {

    @Mock
    private AuthServiceJdbc authService;

    @InjectMocks
    private MainMenuController mainMenuController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(mainMenuController).build();
    }

    @Test
    public void testStartApp() throws Exception {
        mockMvc.perform(post("/api/menu/start"))
                .andExpect(status().isOk())
                .andExpect(content().string("Application started successfully."));
    }

    @Test
    public void testMainMenu() throws Exception {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Executed command for role: ADMIN with choice: 1");

        mockMvc.perform(get("/api/menu")
                        .param("role", "ADMIN")
                        .param("choice", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value("Executed command for role: ADMIN with choice: 1"));
    }

    @Test
    public void testRegister() throws Exception {
        mockMvc.perform(post("/api/menu/register")
                        .contentType("application/json")
                        .content("{ \"username\": \"user\", \"password\": \"pass\", \"email\": \"user@example.com\", \"age\": 25, \"role\": \"USER\" }"))
                .andExpect(status().isCreated())
                .andExpect(content().string("User registered successfully."));
    }

    @Test
    public void testLogin() throws Exception {
        UserDTO userDTO = new UserDTO(1, "user", "pass", "user@example.com", 25, UserRole.CUSTOMER);
        when(authService.login(anyString(), anyString())).thenReturn(userDTO);

        mockMvc.perform(post("/api/menu/login")
                        .param("username", "user")
                        .param("password", "pass"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    public void testExitApp() throws Exception {
        mockMvc.perform(post("/api/menu/exit"))
                .andExpect(status().isOk())
                .andExpect(content().string("Application closed."));
    }
}
