package com.example.MyBookShoppApp.controllers;

import com.example.MyBookShoppApp.security.BookstoreUserRegister;
import com.example.MyBookShoppApp.security.RegistrationForm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class AuthUserControllerTest {

    private final MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private RegistrationForm registrationForm;
    @MockBean
    private BookstoreUserRegister userRegister;


    @Autowired
    public AuthUserControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @BeforeEach
    void setUp() {
        registrationForm = new RegistrationForm();
        registrationForm.setName("TestBd");
        registrationForm.setPhone("+77777777777");
        registrationForm.setPass("123456");
        registrationForm.setEmail("TestBD@email.ru");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void handlerUserRegistration() throws Exception {

        mockMvc.perform(post("/reg")
                        .param("name", registrationForm.getName())
                        .param("pass", registrationForm.getPass())
                        .param("phone", registrationForm.getPhone())
                        .param("email", registrationForm.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.view().name("signin"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("regOk"));
        Mockito.verify(userRegister, Mockito.times(1)).registerNewUser(Mockito.any(RegistrationForm.class));


    }
}