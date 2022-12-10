package com.example.MyBookShoppApp.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.Cookie;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
class BookShopCartControllerTest {
    private final MockMvc mockMvc;
    private Cookie cookie;

    @Autowired
    public BookShopCartControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @BeforeEach
    void setUp() {
        cookie = new Cookie("cartContents", "pViLugy8o1");
    }

    @AfterEach
    void tearDown() {
        cookie = null;
    }

    @Test
    void handleChangeBookStatus() throws Exception {
        mockMvc.perform(post("/books/changeBookStatus/pViLugy8o1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/books/pViLugy8o1"))
                .andExpect(MockMvcResultMatchers.cookie().exists("cartContents"))
                .andExpect(MockMvcResultMatchers.cookie().value("cartContents", "pViLugy8o1"));
    }

    @Test
    void handleRemoveBookStatus() throws Exception {

        mockMvc.perform(post("/books/changeBookStatus/cart/remove/pViLugy8o1")
                        .cookie(cookie))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/books/cart"))
                .andExpect(MockMvcResultMatchers.cookie().exists("cartContents"))
                .andExpect(MockMvcResultMatchers.cookie().value("cartContents", ""));
    }
}