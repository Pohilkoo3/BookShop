package com.example.MyBookShoppApp.security.black_list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class LogoutHandler implements org.springframework.security.web.authentication.logout.LogoutHandler {

    @Autowired
    private BlackListRepository blackListRepository;


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = null;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                }
            }
        }
        BlackTokenEntity blackToken = new BlackTokenEntity();
        blackToken.setBlackToken(token);
        blackListRepository.save(blackToken);
    }
}
