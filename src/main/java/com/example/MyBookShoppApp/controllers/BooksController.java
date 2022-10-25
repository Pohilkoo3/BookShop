package com.example.MyBookShoppApp.controllers;

import com.example.MyBookShoppApp.data.ResourceStorage;
import com.example.MyBookShoppApp.errs.UserNoRegistrated;
import com.example.MyBookShoppApp.model.oldEntity.Book;
import com.example.MyBookShoppApp.responseEntity.PagingBooksResponse;
import com.example.MyBookShoppApp.services.BookService;
import com.example.MyBookShoppApp.util_object.SearchIdForGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

@Controller
public class BooksController {

    private BookService bookService;
    private SearchIdForGenre searchIdForGenre;

    private ResourceStorage storage;

    @Autowired
    public BooksController(BookService bookService, SearchIdForGenre searchIdForGenre, ResourceStorage storage) {
        this.bookService = bookService;
        this.searchIdForGenre = searchIdForGenre;
        this.storage = storage;
    }

    @ModelAttribute("newBooks")
    public List<Book> newBooks() {
        Date now = new Date();
        List<Book> bookList = bookService.getNewBooks(new Date(now.getYear(), now.getMonth() - 6, now.getDay()), new Date(), 0, 20).getContent();
        return bookList;
    }


    @GetMapping("/books/recent")
    public String getRecent() {
        return "books/recent";
    }

    @GetMapping("/books/popular")
    public String getPopular(Model model) {
        model.addAttribute("dataBooksPopular", bookService.getPagePopularBook(0, 10));
        return "books/popular";
    }


    @GetMapping("/books/nextPage/popular")
    @ResponseBody
    public PagingBooksResponse getNextPagePopular( @RequestParam("offset") Integer offset,
                                                   @RequestParam("limit") Integer limit){
        return new PagingBooksResponse(bookService.getPopularBooks(offset, limit).getContent());
    }


    @GetMapping("/books/date/recent")
    @ResponseBody
    public PagingBooksResponse getBooksByDate(@RequestParam(value = "from") @DateTimeFormat(pattern = "dd.MM.yyyy") Date dateFrom,
                                              @RequestParam(value = "to") @DateTimeFormat(pattern = "dd.MM.yyyy") Date dateTo,
                                              @RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        PagingBooksResponse pagingBooksResponse = new PagingBooksResponse(bookService.getNewBooks(dateFrom, dateTo, offset, limit).getContent());
        return pagingBooksResponse;
    }

    @GetMapping("/books/genre/{genreId}")
    @ResponseBody
    public PagingBooksResponse getBookByGenre(@PathVariable(value = "genreId", required = false) String searchIdForGenre,
                                              @RequestParam("offset") Integer offset,
                                              @RequestParam("limit") Integer limit) {
        Integer searchId = Integer.valueOf(searchIdForGenre);
        return new PagingBooksResponse(bookService.getBooksByGenreId(searchId, offset, limit).getContent());
    }

    @GetMapping("/books/{slugId}")
    public String getInfoAboutBook(@PathVariable(name = "slugId") String slugBook, Model model, HttpServletRequest request)
    {

        Book book = bookService.getBookBySlug(slugBook);
        model.addAttribute("bookForSlug", book);
        return "/books/slug";
    }


    @PostMapping ("/books/{slugId}/img/save")
    public String saveNewBookImage(@RequestParam("file")MultipartFile file,
                                   @PathVariable(name = "slugId") String slug) throws IOException {

            String savePath = storage.saveNewBookImage(file, slug);

        Book bookToUpdate = bookService.getBookBySlug(slug);
        bookToUpdate.setImage(savePath);
        bookService.save(bookToUpdate);
        return "redirect:/books/" + slug;
    }

    @GetMapping("/books/download/{hash}")
    public ResponseEntity<ByteArrayResource> bookFile(@PathVariable("hash") String hash) throws IOException {
        Path path =  storage.getBookFilePath(hash);
        MediaType mediaType = storage.getBookFileMime(hash);
        byte[] data = storage.getBookFileByteArray(hash);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + path.getFileName().toString())
                .contentType(mediaType)
                .contentLength(data.length)
                .body(new ByteArrayResource(data));

    }
}
