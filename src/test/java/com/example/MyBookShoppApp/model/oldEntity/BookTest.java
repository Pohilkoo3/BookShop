package com.example.MyBookShoppApp.model.oldEntity;

import com.example.MyBookShoppApp.services.BookService;
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
class BookTest {

    private Book book;
    private String slug;
    private BookService bookService;

    @Autowired
    public BookTest(BookService bookService) {
        this.bookService = bookService;
    }

    @BeforeEach
    void setUp(){
        slug = "pViLugy8o1";
        book = bookService.getBookBySlug(slug);

    }

    @AfterEach
    void tearDown(){
        book = null;
        slug = null;
    }

    @Test
    void getRatingBook()
    {
        int rating = book.getRatingBook();
        assertNotNull(rating);
        assertEquals(4, rating);
    }
}