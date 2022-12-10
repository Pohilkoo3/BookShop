package com.example.MyBookShoppApp.controllers;

import com.example.MyBookShoppApp.errs.UserNoRegistrated;
import com.example.MyBookShoppApp.interfaces.InterfaceBookReview;
import com.example.MyBookShoppApp.model.book.review.BookReviewEntity;
import com.example.MyBookShoppApp.model.oldEntity.Book;
import com.example.MyBookShoppApp.model.user.UserContactEntity;
import com.example.MyBookShoppApp.model.user.UserEntity;
import com.example.MyBookShoppApp.security.jwt.JWTUtil;
import com.example.MyBookShoppApp.services.BookService;
import com.example.MyBookShoppApp.services.UserContactService;
import com.example.MyBookShoppApp.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Controller
@Api(description = "controller for review")
@RequestMapping("/api")
public class ReviewController
{
    @Autowired
    private InterfaceBookReview interfaceBookReview;

    private final BookService bookService;
    private final UserService userService;
    private JWTUtil jwtUtil;
    private UserContactService contactService;

    @Autowired
    public ReviewController(BookService bookService, UserService userService, JWTUtil jwtUtil, UserContactService contactService) {
        this.bookService = bookService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.contactService = contactService;
    }

    @PostMapping("/bookReview/{slug}")
    @ApiOperation("Save review for book from users")
    public String bookReview(@PathVariable("slug") String slug, @RequestParam("textReview") String textReview, HttpServletRequest request) throws UserNoRegistrated {
        Cookie[] cookies = request.getCookies();
        String token = "";
        String username = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")){
                token = cookie.getValue();
            }
        }
        if (token.equals("")){
            throw new UserNoRegistrated(slug);
        } else {
            username = jwtUtil.extractUsername(token);
            UserContactEntity userContact = contactService.getContactByContact(username);
            int idUser = userContact.getUser().getId();

            BookReviewEntity bookReview = new BookReviewEntity();
            Book book = bookService.getBookBySlug(slug);
            UserEntity user = userService.getUserById(idUser);
            bookReview.setBook(book);
            bookReview.setUser(user);
            bookReview.setText(textReview);
            bookReview.setTime(LocalDateTime.now());
            book.getBookReviewEntitySet().add(bookReview);
            bookService.save(book);

            return "redirect:/books/" + slug;
        }
    }
//    @ExceptionHandler(UserNoRegistrated.class)
//    public String getUserNoRegistrated(UserNoRegistrated ex, RedirectAttributes redirectAttributes) {
//        UserNoRegistrated userNoRegistrated= new UserNoRegistrated();
//        userNoRegistrated.setCustomMessage("User not register. Please signup.!");
//        redirectAttributes.addFlashAttribute("isUserNotRegister", true);
//        redirectAttributes.addFlashAttribute("userNotRegister", userNoRegistrated);
//        return "redirect:/books/" + ex.getSlugBook();
//    }
}
