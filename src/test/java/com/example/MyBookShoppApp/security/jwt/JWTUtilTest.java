package com.example.MyBookShoppApp.security.jwt;

import com.example.MyBookShoppApp.model.user.UserEntity;
import com.example.MyBookShoppApp.security.BookStoreUserDetails;
import com.example.MyBookShoppApp.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class JWTUtilTest {

    @MockBean
    private UserService userService;
    private BookStoreUserDetails userDetails;
    private UserEntity user;
    private JWTUtil jwtUtil;

    @Autowired
    JWTUtilTest(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @BeforeEach
    void setUp() {
        user = userService.findUserByContact("yana@mail.ru");
        userDetails = new BookStoreUserDetails(user);
    }

    @AfterEach
    void tearDown() {
        user = null;
        userDetails = null;
    }

    @Test
    void generateToken() {
        String token = jwtUtil.generateToken(userDetails, "yana@mail.ru");
        assertNotNull(token);
        assertFalse(jwtUtil.isTokenExpired(token));
        assertTrue(jwtUtil.validateToken(token, "yana@mail.ru"));
    }


}