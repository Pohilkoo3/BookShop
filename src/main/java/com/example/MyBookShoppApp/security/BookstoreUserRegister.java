package com.example.MyBookShoppApp.security;

import com.example.MyBookShoppApp.security.jwt.JWTUtil;
import com.example.MyBookShoppApp.model.user.UserContactEntity;
import com.example.MyBookShoppApp.model.user.UserEntity;
import com.example.MyBookShoppApp.services.UserContactService;
import com.example.MyBookShoppApp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookstoreUserRegister {
    private final UserService userService;
    private final UserContactService contactService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private  final BookstoreUserDetailsService bookstoreUserDetailsService;

    private final JWTUtil jwtUtil;


    public UserEntity registerNewUser(RegistrationForm registrationForm) {
        UserEntity user = new UserEntity();
        if (userService.findUserByContact(registrationForm.getEmail()) == null) {

            user.setName(registrationForm.getName());
            user.setPass(passwordEncoder.encode(registrationForm.getPass()));
            user.setRegTime(LocalDateTime.now());
            user.setHash(user.getHash() + user.getName());
            user.setBalance(0);
            userService.addNewUser(user);

            UserContactEntity userContact = new UserContactEntity();
            userContact.setContact(registrationForm.getEmail().length() > 0 ? registrationForm.getEmail() : registrationForm.getPhone());
            userContact.setApproved(1);
            userContact.setUser(user);
            userContact.setCode("111111");
            userContact.setCodeTrails(3);
            userContact.setCodeTime(LocalDateTime.now());

            contactService.addNewContact(userContact);
        } return user;
    }

    public ContactConfirmationResponse jwtLogin(ContactConfirmationPayload payload){
       authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(), payload.getCode()));
       BookStoreUserDetails userDetails = (BookStoreUserDetails) bookstoreUserDetailsService.loadUserByUsername(payload.getContact());
       String jwtToken = jwtUtil.generateToken(userDetails, payload.getContact());
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult(jwtToken);
        return response;
    }


    public ContactConfirmationResponse login(ContactConfirmationPayload payload)
    {
        System.out.println("h");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(),
                payload.getCode()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }

    public Object getCurrentUser() {
        BookStoreUserDetails userDetails = (BookStoreUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }
}
