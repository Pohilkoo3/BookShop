package com.example.MyBookShoppApp.controllers;

import com.example.MyBookShoppApp.model.oldEntity.Book;
import com.example.MyBookShoppApp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/books")
public class BookShopPostponedController {

    private final BookService bookService;

    @Autowired
    public BookShopPostponedController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute(name = "booksData")
    public List<Book> booksFromCookies() {
        return new ArrayList<>();
    }

    @PostMapping("/changeBookStatus/postponed/{slug}")
    public String postponedPage(@PathVariable("slug") String slug,
                                HttpServletResponse response,
                                @CookieValue(name = "postponedContents", required = false) String postponedContents,
                                Model model) {
        if (postponedContents == null || postponedContents.equals("")) {
            Cookie cookie = new Cookie("postponedContents", slug);
            cookie.setPath("/");
            response.addCookie(cookie);
            model.addAttribute("isEmpty", false);
        } else if (!postponedContents.contains(slug)) {
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(postponedContents).add(slug);
            Cookie cookie = new Cookie("postponedContents", stringJoiner.toString());
            cookie.setPath("/");
            response.addCookie(cookie);
            model.addAttribute("isEmpty", false);
        }
        return  "redirect:/books/" + slug;
    }

    @GetMapping("/postponed")
    public String postponedPage(@CookieValue(name = "postponedContents", required = false) String postponedContents,
                                Model model) {
        if (postponedContents != null) {
            if (!postponedContents.equals("")){
                String[] arraySlugs = postponedContents.split("/");
                List<Book> books = bookService.getBooksByListSlug(arraySlugs);
                int sumAllBooks = books.stream().map(Book::getPriceWithDiscount).reduce(Integer::sum).orElse(0);
                String stringSlugs = postponedContents.replaceAll("/", ",");
                model.addAttribute("booksData", books);
                model.addAttribute("sumAllBooks", sumAllBooks);
                model.addAttribute("allSlugs", stringSlugs);
            }
        }
        return "/postponed";
    }

    @PostMapping("/changeBookStatus/postponed/byAll/{slug}")
    public String handleBuyAll(@PathVariable("slug") String slug, Model model, @CookieValue(name = "cartContents", required = false) String cartContents
            , HttpServletResponse response) {
        String[] slugs = slug.split(",");
        StringJoiner stringJoiner = new StringJoiner("/");
        for (String slugNew : slugs) {
            if (!cartContents.contains(slugNew)) {
                stringJoiner.add(slugNew);
            }
        }
        if (cartContents == null || cartContents.equals("")){
            Cookie cookie = new Cookie("cartContents", stringJoiner.toString());
            cookie.setPath("/");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        } else {
            StringJoiner stringJoiner2 = new StringJoiner("/");
            stringJoiner2.add(cartContents).add(stringJoiner.toString());
            Cookie cookie = new Cookie("cartContents", stringJoiner2.toString());
            cookie.setPath("/");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        }


        return "redirect:/books/postponed";
    }

    @PostMapping("/changeBookStatus/postponed/remove/{slug}")
    public String handleByAll(@PathVariable("slug") String slug,
                              @CookieValue(name = "postponedContents", required = false) String postponedContents
            , HttpServletResponse response) {
        if (postponedContents != null || !postponedContents.equals("")) {
            List<String> bookSlugs = new ArrayList<>(Arrays.stream(postponedContents.split("/")).toList());
            bookSlugs.remove(slug);
            String slugs = bookSlugs.stream().collect(Collectors.joining("/"));
            Cookie cookie = new Cookie("postponedContents", slugs);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return "redirect:/books/postponed";
    }

}
