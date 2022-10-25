package com.example.MyBookShoppApp.controllers;

import com.example.MyBookShoppApp.model.oldEntity.Book;
import com.example.MyBookShoppApp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class OtherControllers
{

    private BookService bookService;

    @Autowired
    public OtherControllers(BookService bookService) {
        this.bookService = bookService;
    }


    @ModelAttribute("threeBooksData")
    public List<Book> get3Books(){
        return bookService.getBooksData().subList(0,3);
    }

    @ModelAttribute("booksData")
    public List<Book> getBooksData(){
        return bookService.getBooksData();
    }







    @GetMapping("/about")
    public String aboutPage(){
        return "about";
    }

    @GetMapping("/faq")
    public String helpPage(){
        return "faq";
    }

    @GetMapping("/contacts")
    public String contactsPage(){
        return "contacts";
    }

    @GetMapping("/search")
    public String searchPage(){
        return "search/index";
    }


}
