package com.example.MyBookShoppApp.controllers;

import com.example.MyBookShoppApp.model.other.SmsCode;
import com.example.MyBookShoppApp.model.user.UserContactEntity;
import com.example.MyBookShoppApp.model.user.UserEntity;
import com.example.MyBookShoppApp.security.*;
import com.example.MyBookShoppApp.security.jwt.JWTUtil;
import com.example.MyBookShoppApp.services.PaymentService;
import com.example.MyBookShoppApp.services.SmsService;
import com.example.MyBookShoppApp.services.UserContactService;
import com.example.MyBookShoppApp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class AuthUserController {

    private final BookstoreUserRegister userRegister;
    private final UserContactService userContactService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final SmsService smsService;
    private final JavaMailSender javaMailSender;
    private final PaymentService paymentService;
    private final JWTUtil jwtUtil;
    private final UserContactService contactService;

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
        model.addAttribute("changePas", 0);
        model.addAttribute("transactions", paymentService.getAllTransactionsByUserId(user.getId()));
        return "profile";
    }


    @PostMapping(value = "/profile")
    public String handleProfileChangeCredentials(Model model, HttpServletRequest request,
                                                 ChangePasswordForm passwordForm) {
        String email = jwtUtil.getEmailFromToken(request);
        String token = jwtUtil.getTokenFromRequest(request);

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("bookstore-oleg@mail.ru");
        message.setTo(email);
        message.setSubject("Bookstore email verification");
        String url = "http://localhost:8085/profile/change-credentials" +
                "?token=" + token +
                "&emailOld=" + email +
                "&emailNew=" + passwordForm.getMail() +
                "&name=" + passwordForm.getName() +
                "&password=" + passwordForm.getPassword() +
                "&phone=" + passwordForm.getPhone() +
                "&passwordReply=" + passwordForm.getPasswordReply();

        message.setText("Для подтверждения изменения учетных данных перейдите по ссылке: " + url);
        javaMailSender.send(message);
        return "redirect:/logout";
    }


    @GetMapping(value = "/profile/change-credentials")
    public String handleProfileChangePassword(@RequestParam("token") String token,
                                              @RequestParam("emailOld") String emailOld,
                                              @RequestParam("emailNew") String emailNew,
                                              @RequestParam("name") String name,
                                              @RequestParam("password") String password,
                                              @RequestParam("phone") String phone,
                                              @RequestParam("passwordReply") String passwordReply,
                                              Model model, HttpServletRequest request) {
        UserEntity user = userService.findUserByContact(emailOld);
        UserContactEntity userContact = userContactService.getContactById(user.getId());
        if (!password.isBlank() & password.equals(passwordReply)) {
            user.setPass(passwordEncoder.encode(password));
            user.setName(name.isBlank() ? user.getName() : name);
            userService.addNewUser(user);

            userContact.setContact(emailNew.isBlank() ? emailOld : emailNew);
            userContact.setApproved(1);
            userContact.setUser(user);
            userContact.setCode("111111");
            userContact.setCodeTrails(3);
            userContact.setCodeTime(LocalDateTime.now());

            contactService.addNewContact(userContact);
            model.addAttribute("changePas", 1);
        } else {
            model.addAttribute("changePas", 2);
        }
        model.addAttribute("curUsr", user);
        model.addAttribute("usrContact", userContact);
        model.addAttribute("transactions", paymentService.getAllTransactionsByUserId(user.getId()));
        return "/signin";
    }


}
