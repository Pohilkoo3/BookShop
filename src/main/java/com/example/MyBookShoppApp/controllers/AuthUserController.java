package com.example.MyBookShoppApp.controllers;

import com.example.MyBookShoppApp.model.other.SmsCode;
import com.example.MyBookShoppApp.model.user.UserContactEntity;
import com.example.MyBookShoppApp.model.user.UserEntity;
import com.example.MyBookShoppApp.security.BookstoreUserRegister;
import com.example.MyBookShoppApp.security.ContactConfirmationPayload;
import com.example.MyBookShoppApp.security.ContactConfirmationResponse;
import com.example.MyBookShoppApp.security.RegistrationForm;
import com.example.MyBookShoppApp.services.SmsService;
import com.example.MyBookShoppApp.services.UserContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthUserController {

    private final BookstoreUserRegister userRegister;
    private final UserContactService userContactService;

    private SmsService smsService;
    private JavaMailSender javaMailSender;

    @Autowired
    public AuthUserController(BookstoreUserRegister userRegister, UserContactService userContactService, SmsService smsService, JavaMailSender javaMailSender) {
        this.userRegister = userRegister;
        this.userContactService = userContactService;
        this.smsService = smsService;
        this.javaMailSender = javaMailSender;
    }

    @GetMapping("/signin")
    public String signinPage() {
        return "signin";
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("regForm", new RegistrationForm());
        return "signup";
    }

    @PostMapping("/requestContactConfirmation")
    @ResponseBody
    public ContactConfirmationResponse getConfirmation(@RequestBody ContactConfirmationPayload payload) {
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }


    @PostMapping("/requestEmailContactConfirmation")
    @ResponseBody
    public ContactConfirmationResponse handlerRequestEmailConfirmation(@RequestBody ContactConfirmationPayload payload) {
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("bookstore-oleg@mail.ru");
        message.setTo(payload.getContact());
        SmsCode smsCode = new SmsCode(smsService.generateCode(), 300); // 5 minutes
        smsService.saveNewCode(smsCode);
        message.setSubject("Bookstore email verification");
        message.setText("Verification code is: " + smsCode.getCode());
        javaMailSender.send(message);
        response.setResult("true");
        return response;
    }


    @PostMapping("/reg")
    public String handlerUserRegistration(HttpServletRequest request, RegistrationForm registrationForm, Model model) {
        userRegister.registerNewUser(registrationForm);
        model.addAttribute("regOk", "true");
        return "signin";
    }

    @PostMapping("/approveContact")
    @ResponseBody
    public ContactConfirmationResponse handlerApproveContact(@RequestBody ContactConfirmationPayload payload) {
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }

    @PostMapping("/login")
    @ResponseBody
    public ContactConfirmationResponse handlerLogin(@RequestBody ContactConfirmationPayload payload, HttpServletResponse response) {
        ContactConfirmationResponse loginResponse = userRegister.jwtLogin(payload);
        Cookie cookie = new Cookie("token", loginResponse.getResult());
        response.addCookie(cookie);
        return loginResponse;
    }

    @GetMapping("/my")
    public String handleMy(HttpServletRequest request, HttpServletResponse response) {
        return "my";
    }

    @GetMapping("/profile")
    public String handleProfile(Model model) {
        UserEntity user = (UserEntity) userRegister.getCurrentUser();
        UserContactEntity userContact = userContactService.getContactById(user.getId());
        model.addAttribute("curUsr", user);
        model.addAttribute("usrContact", userContact);
        return "profile";
    }


}
