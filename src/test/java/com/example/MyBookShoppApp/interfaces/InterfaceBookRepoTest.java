package com.example.MyBookShoppApp.interfaces;

import com.example.MyBookShoppApp.model.oldEntity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class InterfaceBookRepoTest {

    private InterfaceBookRepo bookRepo;

    @Autowired
    public InterfaceBookRepoTest(InterfaceBookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Test
    void findBookByAuthorName() {
        String token = "Rosalie Gordon";
        List<Book> bookListByAuthor = bookRepo.findBookByAuthorName(token);
       assertNotNull(bookListByAuthor);
       assertFalse(bookListByAuthor.isEmpty());
       for (Book book : bookListByAuthor){
      String authors = book.getBook2AuthorEntitySet().stream().map(b -> b.getAuthor().getName()).collect(Collectors.joining(","));
      assertTrue(authors.contains(token));
       }
    }

    @Test
    void findBooksByTitleContaining() {
        String token = "Oleg";
        List<Book> listBookByTitleContaining = bookRepo.findBooksByTitleContaining(token);
        assertNotNull(listBookByTitleContaining);
        assertFalse(listBookByTitleContaining.isEmpty());
        for (Book book : listBookByTitleContaining) {
           assertThat(book.getTitle().contains(token));
        }
    }

    @Test
    void findBestsellers() {
        List<Book> booksBestsellers = bookRepo.findBestsellers();
        assertNotNull(booksBestsellers);
        assertFalse(booksBestsellers.isEmpty());
        for (Book book : booksBestsellers) {
            assertThat(book.getIsBestseller());
        }
        assertThat(booksBestsellers.size()).isGreaterThan(1);

    }
}