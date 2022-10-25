package com.example.MyBookShoppApp.interfaces;

import com.example.MyBookShoppApp.model.user.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestPropertySource("/application-test.properties")
class InterfaceUserRepoTest {
    private InterfaceUserRepo userRepo;

    @Autowired
    public InterfaceUserRepoTest(InterfaceUserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Test
    void addNewUser() {
        UserEntity user = new UserEntity();
        user.setName("TestAddUser");
        user.setHash("HashTestAddUser");
        user.setRegTime(LocalDateTime.now());
        user.setPass("753");
        user.setBalance(1500);
        assertNotNull(userRepo.save(user));


    }

    @Test
    void findUserByContact() {
    }
}