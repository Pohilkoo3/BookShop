package com.example.MyBookShoppApp.dao;

import com.example.MyBookShoppApp.model.user.UserEntity;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserDaoTest {
    String email;
    String expected;
    private UserDao userDao;

    @Autowired
    public UserDaoTest(UserDao userDao) {
        this.userDao = userDao;
    }

    @BeforeEach
    void setUp() {
        email = "yna@mail.ru";
        expected = "Yana";
    }

    @AfterEach
    void tearDown() {
        email = null;
        expected = null;
    }

    @Test
    void findUserByContact() {
        UserEntity user = userDao.findUserByContact(email);
        assertEquals(expected, user.getName());
        assertNotNull(user);


    }
}