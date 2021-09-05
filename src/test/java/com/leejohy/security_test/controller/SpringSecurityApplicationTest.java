package com.leejohy.security_test.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(MainController.class)
public class SpringSecurityApplicationTest {
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void 모든_유저_접근_허용_should_return_200() throws Exception {
        mockMvc.perform(
                get("/")
        ).andExpect(
                status().isOk()
        );
    }

    @Test
    public void givenRequestOnPrivateAndAdminPageWithoutLogin_shouldReturn401() throws Exception {
        mockMvc.perform(
                get("/private")
        ).andExpect(
                status().isUnauthorized()
        );

        mockMvc.perform(
                get("/admin")
        ).andExpect(
                status().isUnauthorized()
        );
    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenRequestOnPrivatePageWithCredential_should_return200() throws Exception {
        mockMvc.perform(
                get("/private")
        ).andExpect(
                status().isOk()
        );
    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenRequestOnAdminPageWithCredential_should_return403() throws Exception {
        mockMvc.perform(
                get("/admin")
        ).andExpect(
                status().isForbidden()
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenRequestOnAdminPageWithCredential_should_return200() throws Exception {
        mockMvc.perform(
                get("/admin")
        ).andExpect(
                status().isOk()
        );
    }


}
