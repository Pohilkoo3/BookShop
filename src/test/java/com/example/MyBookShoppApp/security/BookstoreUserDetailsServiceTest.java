package com.example.MyBookShoppApp.security;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookstoreUserDetailsServiceTest {
    private String contact;
    private BookstoreUserDetailsService bookstoreUserDetailsService;

    @Autowired
    public BookstoreUserDetailsServiceTest(BookstoreUserDetailsService bookstoreUserDetailsService) {
        this.bookstoreUserDetailsService = bookstoreUserDetailsService;
    }

    @BeforeEach
    void setUp() {
       contact = "yana@mail.ru";
    }

    @AfterEach
    void tearDown() {
        contact = null;
    }

    @Test
    void loadUserByUsername() {
        UserDetails userDetails = bookstoreUserDetailsService.loadUserByUsername(contact);
        assertNotNull(userDetails);
        assertEquals(userDetails.getUsername(), "Yana");
        assertEquals(userDetails.getPassword(), "$2a$10$f7llkQuzoss8uoHU9Rm7nOs72KNam.DwkMQ.c/xgmME6noQzibP/2");
        assertEquals(userDetails.getAuthorities(), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isEnabled());
        assertTrue(userDetails.isCredentialsNonExpired());

    }
}