package com.example.MyBookShoppApp.security;

import com.example.MyBookShoppApp.model.user.UserEntity;
import com.example.MyBookShoppApp.services.UserContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class BookStoreUserDetailsTest {

    private BookStoreUserDetails userDetails;
    private UserEntity user;
    private UserContactService userContactService;

    @Autowired
    public BookStoreUserDetailsTest(UserContactService userContactService) {
        this.userContactService = userContactService;
    }

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setId(2);
        userDetails = new BookStoreUserDetails();
        userDetails.setUser(user);
        userDetails.setUserContactService(userContactService);

    }


    @Test
    void getContact() {
        String contact = userDetails.getContact();
        assertNotNull(contact);
        assertEquals("yana@mail.ru", contact);
    }
}