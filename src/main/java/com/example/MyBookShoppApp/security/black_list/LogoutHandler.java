package com.example.MyBookShoppApp.security.black_list;

import com.example.MyBookShoppApp.security.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class LogoutHandler implements org.springframework.security.web.authentication.logout.LogoutHandler {


    private final BlackListRepository blackListRepository;
    private final JWTUtil jwtUtil;


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = jwtUtil.getTokenFromRequest(request);

        BlackTokenEntity blackToken = new BlackTokenEntity();
        blackToken.setBlackToken(token);
        blackListRepository.save(blackToken);
    }
}
