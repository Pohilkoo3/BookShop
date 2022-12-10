package com.example.MyBookShoppApp.controllers;

import com.example.MyBookShoppApp.responseEntity.PagingBooksResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestPropertySource("/application-test.properties")
class MainPageControllerTest {

    private MainPageController mainPageController;
    private PagingBooksResponse pagingBooksResponse;

    @Autowired
    public MainPageControllerTest(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        pagingBooksResponse = null;
    }

    @Test
    void getBooksPage() {
        pagingBooksResponse = mainPageController.getBooksPage(0, 6);
        assertNotNull(pagingBooksResponse);
        pagingBooksResponse.getBooks().stream().map(b -> b.getTitle()).forEach(System.out::println);
        assertTrue(pagingBooksResponse.getBooks().size() == 6);
    }
}