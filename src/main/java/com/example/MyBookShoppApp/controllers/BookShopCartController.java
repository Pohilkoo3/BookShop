package com.example.MyBookShoppApp.controllers;

import com.example.MyBookShoppApp.model.oldEntity.Book;
import com.example.MyBookShoppApp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@Controller
@RequestMapping("/books")
public class BookShopCartController {

    @ModelAttribute(name = "bookCart")
    public List<Book> bookCart(){
        return new ArrayList<>();
    }

    private final BookService bookService;


    @Autowired
    public BookShopCartController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/cart")
    public String handlerCartRequest(@CookieValue(value = "cartContents", required = false) String cartContents, Model model){
        if (cartContents == null || cartContents.equals("")){
            model.addAttribute("isCartEmpty", true);
        }else {
            model.addAttribute("isCartEmpty", false);
            cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
            cartContents = cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length() -1 ) : cartContents;
            String[] cookieSlugs = cartContents.split("/");
            List<Book> booksFromSlug = bookService.getBooksByListSlug(cookieSlugs);
            int sumAllBooks = booksFromSlug.stream().map(b -> b.getPriceWithDiscount()).reduce((s1, s2) ->s1 + s2).orElse(0);
            int sumOldPrice = booksFromSlug.stream().map(b -> b.getPrice()).reduce((p1, p2) -> p1 + p2).orElse(0);
            model.addAttribute("sumAllBooks", sumAllBooks);
            model.addAttribute("bookCart", booksFromSlug);
            model.addAttribute("sumOldPrice", sumOldPrice);
        }
        return "cart";
    }


    @PostMapping("/changeBookStatus/cart/remove/{slug}")
    public String handleRemoveBookStatus(@PathVariable("slug") String slug, Model model, @CookieValue(name = "cartContents", required = false) String cartContents
            , HttpServletResponse response) {
        if (cartContents != null || !cartContents.equals("")){
            ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(cartContents.split("/")));
            cookieBooks.remove(slug);
            Cookie cookie = new Cookie("cartContents", String.join("/", cookieBooks));
            cookie.setPath("/");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        }else {
            model.addAttribute("isCartEmpty", true);
        }



        return "redirect:/books/cart";
    }

    @PostMapping("/changeBookStatus/{slug}")
    public String handleChangeBookStatus(@PathVariable("slug") String slug, Model model, @CookieValue(name = "cartContents", required = false) String cartContents
            , HttpServletResponse response) {
        if (cartContents == null || cartContents.equals("")){
            Cookie cookie = new Cookie("cartContents", slug);
            cookie.setPath("/");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        } else if (!cartContents.contains(slug)) {
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(cartContents).add(slug);
            Cookie cookie = new Cookie("cartContents", stringJoiner.toString());
            cookie.setPath("/");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);

        }
        return "redirect:/books/" + slug;
    }
}
