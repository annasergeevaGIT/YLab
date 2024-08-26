package org.example.controller;

import org.example.domain.model.Car;
import org.example.domain.model.CarStatus;
import org.example.domain.model.User;
import org.example.service.AuditService;
import org.example.service.AuthServiceJdbc;
import org.example.service.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SearchControllerTest {

    @Mock
    private SearchService searchService;

    @Mock
    private AuditService auditService;

    @Mock
    private AuthServiceJdbc authService;

    @InjectMocks
    private SearchController searchController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(searchController).build();
    }

    @Test
    public void testSearchCars() throws Exception {
        when(searchService.searchCars(anyString(), anyString(), anyInt(), anyDouble(), any(CarStatus.class)))
                .thenReturn(List.of(new Car())); // Adjust as necessary

        when(authService.getCurrentUser()).thenReturn(new User());

        mockMvc.perform(get("/api/search/cars")
                        .param("brand", "Toyota")
                        .param("model", "Camry"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));

        verify(auditService, times(1)).logAction(anyInt(), anyString());
    }

    // Similarly implement tests for searchOrders, searchUsers, searchAuditLogs, and exportLogs
}
