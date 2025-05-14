package com.example.expenseTracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ExpenseTrackerApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        // Checks if Spring context loads successfully
    }

    @Test
    void accessSecuredPageWithoutAuth_shouldRedirectToLogin() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection()) // 302 redirect to login
                .andExpect(redirectedUrlPattern("**/login.html"));
    }

    @Test
    void accessSecuredPageWithHttpBasic_shouldSucceed() throws Exception {
        mockMvc.perform(get("/").with(httpBasic("admin", "admin")))
                .andExpect(status().isOk());
    }

    @Test
    void accessApiWithoutAuth_shouldFail() throws Exception {
        mockMvc.perform(get("/api/expenses"))
                .andExpect(status().is3xxRedirection()); // Or isUnauthorized() if REST
    }

    @Test
    void accessApiWithHttpBasic_shouldSucceed() throws Exception {
        mockMvc.perform(get("/api/expenses")
                .with(httpBasic("admin", "admin"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
