package com.example.MyBookShoppApp.security;

import com.example.MyBookShoppApp.interfaces.InterfaceUserContact;
import com.example.MyBookShoppApp.interfaces.InterfaceUserRepo;
import com.example.MyBookShoppApp.model.user.UserEntity;
import com.example.MyBookShoppApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BookstoreUserDetailsService implements UserDetailsService
{

    private final UserService userService;

    @Autowired
    public BookstoreUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String contact) throws UsernameNotFoundException {
        UserEntity userEntity = userService.findUserByContact(contact);
        if (userEntity != null){
            return new BookStoreUserDetails(userEntity);
        }else {
            throw new UsernameNotFoundException("user not found doh!");
        }
    }



}
