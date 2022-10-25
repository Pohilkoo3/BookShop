package com.example.MyBookShoppApp.controllers;

import com.example.MyBookShoppApp.errs.BookStoreApiWrongParameterException;
import com.example.MyBookShoppApp.model.oldEntity.Book;
import com.example.MyBookShoppApp.services.BookService;
import com.example.MyBookShoppApp.responseEntity.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(description = "API authors data")
public class ApiBookController {
    private BookService bookService;

    @Autowired
    public ApiBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ApiOperation("API application get books data by author's name")
    @GetMapping("/books/by-author")
    public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam("author") String author) {
        return ResponseEntity.ok(bookService.getBooksDataByAuthor(author));
    }



    @ApiOperation("API application get books data by title")
    @GetMapping("/books/by-title")
    public ResponseEntity<ApiResponse<Book>> getBooksByTitle(@RequestParam("title") String title) throws BookStoreApiWrongParameterException {
        List<Book> bookList = bookService.getBooksDataByTitle(title);
        ApiResponse<Book> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("data got " + bookList.size() + " elements");
        apiResponse.setHttpStatus(HttpStatus.OK);
        apiResponse.setData(bookList);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/books/by-price-range")
    @ApiOperation("API application get books data by price range")
    public ResponseEntity<List<Book>> getBooksDataByPriceBetween(@RequestParam("min") Integer min, @RequestParam("max") Integer max) {
        return ResponseEntity.ok(bookService.getBooksDataByPriceBetween(min, max));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Book>> handlerMissingServletRequestParameterException(Exception exception){
       return new ResponseEntity<>(new ApiResponse<>(HttpStatus.BAD_REQUEST, "Missing required parameters", exception), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookStoreApiWrongParameterException.class)
    public ResponseEntity<ApiResponse<Book>> handlerBookStoreApiWrongParameterException(Exception exception){
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.BAD_REQUEST, exception.getMessage(), exception), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/books/by-price")
    @ApiOperation("API application get books data by price")
    public ResponseEntity<List<Book>> getBooksDataByPriceIs(@RequestParam("price") Integer price) {
        return ResponseEntity.ok(bookService.getBooksDataByPriceIs(price));
    }

    @GetMapping("/books/bestsellers")
    @ApiOperation("API application get bestseller books")
    public ResponseEntity<List<Book>> getBestsellers() {
        return ResponseEntity.ok(bookService.getBestsellers());
    }

    @GetMapping("/books/max-discount")
    @ApiOperation("API application get books by max discount")
    public ResponseEntity<List<Book>> getBooksMaxDiscount() {
        return ResponseEntity.ok(bookService.getBooksMaxDiscount());
    }

}
