package com.example.MyBookShoppApp.security;

import com.example.MyBookShoppApp.model.user.UserContactEntity;
import com.example.MyBookShoppApp.model.user.UserEntity;
import com.example.MyBookShoppApp.services.UserContactService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class BookStoreUserDetails implements UserDetails {

    private UserEntity user;
    private UserContactService userContactService;

    @Autowired
    public BookStoreUserDetails(UserContactService userContactService) {
        this.userContactService = userContactService;
    }
    public BookStoreUserDetails(UserEntity user) {
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return user.getPass();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    public String getContact(){
        UserContactEntity contact = userContactService.getContactById(user.getId());
        return contact.getContact();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
