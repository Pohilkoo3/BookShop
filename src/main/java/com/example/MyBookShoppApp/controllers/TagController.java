package com.example.MyBookShoppApp.controllers;

import com.example.MyBookShoppApp.model.oldEntity.Book;
import com.example.MyBookShoppApp.responseEntity.PagingBooksResponse;
import com.example.MyBookShoppApp.services.BookService;
import com.example.MyBookShoppApp.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TagController {
    private BookService bookService;
    private TagService tagService;

    @Autowired
    public TagController(BookService bookService, TagService tagService) {
        this.bookService = bookService;
        this.tagService = tagService;
    }

    @GetMapping("/books/tag/{id}")
    @ResponseBody
    public PagingBooksResponse getPageByTagsId(@PathVariable(name = "id") Integer tagsId,
                                               @RequestParam(name = "offset") Integer offset,
                                               @RequestParam(name = "limit") Integer limit)
    {
       return new PagingBooksResponse(bookService.getBooksByTagsId(tagsId, offset, limit).getContent());
    }


    @GetMapping("/tags/SLUG/{tagsId}")
    public String tagsPage(@PathVariable(name = "tagsId") Integer tagsId,
                           Model model) {

        Page<Book> page = bookService.getBooksByTagsId(tagsId, 0, 5);
        model.addAttribute("booksData", page);
        model.addAttribute("tag", tagService.getTagById(tagsId));
        return "/tags/index";
    }
}
