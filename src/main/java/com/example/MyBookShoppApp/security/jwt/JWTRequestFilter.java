package com.example.MyBookShoppApp.security.jwt;

import com.example.MyBookShoppApp.security.BookStoreUserDetails;
import com.example.MyBookShoppApp.security.BookstoreUserDetailsService;
import com.example.MyBookShoppApp.security.black_list.BlackListService;
import com.example.MyBookShoppApp.services.UserContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private BookstoreUserDetailsService bookstoreUserDetailsService;
    private JWTUtil jwtUtil;
    private UserContactService contactService;
    private BlackListService blackListService;

    @Autowired
    public JWTRequestFilter(BookstoreUserDetailsService bookstoreUserDetailsService, JWTUtil jwtUtil, UserContactService contactService, BlackListService blackListService) {
        this.bookstoreUserDetailsService = bookstoreUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.contactService = contactService;
        this.blackListService = blackListService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        String username = null;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    username = jwtUtil.extractUsername(token);
                }
                boolean isTokenBlack = blackListService.isTokenIsBlack(token);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null && !isTokenBlack) {
                    BookStoreUserDetails userDetails = (BookStoreUserDetails) bookstoreUserDetailsService.loadUserByUsername(username);
                    int contactId = userDetails.getUser().getId();
                    String contact = contactService.getContactById(contactId).getContact();
                    if (jwtUtil.validateToken(token, contact)) {
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails, null, userDetails.getAuthorities());

                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
        }

        filterChain.doFilter(request,response);
    }
}