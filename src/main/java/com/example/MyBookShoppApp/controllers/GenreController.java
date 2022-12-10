package com.example.MyBookShoppApp.controllers;

import com.example.MyBookShoppApp.model.genre.GenreEntity;
import com.example.MyBookShoppApp.services.BookService;
import com.example.MyBookShoppApp.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Controller
public class GenreController {
    private GenreService genreService;
    private BookService bookService;


    @Autowired
    public GenreController(GenreService genreService, BookService bookService) {
        this.genreService = genreService;
        this.bookService = bookService;
    }

    @ModelAttribute("genresData")
    public List<GenreEntity> getGenres() {

        List<GenreEntity> genreEntityList = genreService.getGenres();
        for (GenreEntity genre : genreEntityList) {
            for (GenreEntity genre2 : genreEntityList) {
                if (genre2.getParentId() == genre.getId()) {
                    genre.addNewChild(genre2);
                }
            }
        }
        return genreEntityList;
    }

    @GetMapping("/genres")
    public String genresPage() {
        return "genres/index";
    }


    @GetMapping("/genres/slug/{genreId}")
    public String slugPage(@PathVariable("genreId") Integer genresId,
                           Model model) {
        GenreEntity genre = genreService.getGenreByID(genresId);
        genreService.setParent(genre);
        LinkedList<GenreEntity> path = genreService.getPath(genre, new LinkedList<>());
        Collections.reverse(path);
        model.addAttribute("booksDataByGenreId", bookService.getBooksByGenreId(genresId, 0, 10).getContent());
        model.addAttribute("genreName", genre);
        model.addAttribute("path", path);
        return "genres/slug";
    }


}
