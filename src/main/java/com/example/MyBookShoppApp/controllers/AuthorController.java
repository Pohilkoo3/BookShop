package com.example.MyBookShoppApp.controllers;

import com.example.MyBookShoppApp.model.oldEntity.Author;
import com.example.MyBookShoppApp.responseEntity.PagingBooksResponse;
import com.example.MyBookShoppApp.services.AuthorService;
import com.example.MyBookShoppApp.services.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@Api(description = "authors data")
public class AuthorController {
    private AuthorService authorService;
    private BookService bookService;

    @Autowired
    public AuthorController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @ModelAttribute("authorsMap")
    public Map<String, List<Author>> authorsMap() {
        Map<String, List<Author>> authorsMap = authorService.getAuthorsMap();
        return authorsMap;
    }

    @GetMapping("/authors")
    @ApiOperation("method to get the author's page")
    public String authorsPage() {
        return "/authors/index";
    }

    @ApiOperation("method to get map of authors")
    @GetMapping("/api/authors")
    @ResponseBody
    public Map<String, List<Author>> authors() {
        return authorService.getAuthorsMap();
    }

    @GetMapping("/authors/slug/{idAuthor}")
    public String pageAboutAuthor(@PathVariable(name = "idAuthor") Integer idAuthor,
                                  Model model) {
        Author author = authorService.getAuthorById(idAuthor);
        model.addAttribute("author", author);
        model.addAttribute("authorsBook", bookService.getBooksDataByAuthor(author.getName()));
        return "authors/slug";
    }

    @GetMapping("/books/author/{idAuthor}")
    public String allBooksByAuthor(@PathVariable(name = "idAuthor") Integer idAuthor,
                                   Model model) {
        Author author = authorService.getAuthorById(idAuthor);
        model.addAttribute("author", author);
        model.addAttribute("authorsBook", bookService.getBooksDataByAuthorId(idAuthor, 0, 10));
        return "books/author";
    }

    @GetMapping("/books/page/author/{idAuthor}")
    @ResponseBody
    public PagingBooksResponse getPageBooksByAuthorId(@PathVariable(name = "idAuthor") Integer idAuthor,
                                                      @RequestParam("offset") Integer offset,
                                                      @RequestParam("limit") Integer limit) {
        return new PagingBooksResponse(bookService.getBooksDataByAuthorId(idAuthor, offset, limit).getContent());
    }


}
