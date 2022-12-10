package com.example.MyBookShoppApp.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@ControllerAdvice
public class ControllerAdviceCommonsModel {

    private boolean isMoneyEnough =true;

    @ModelAttribute("cartSize")
    public Integer cartSize(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            String slugs = Arrays.stream(cookies).filter(c -> c.getName().equals("cartContents")).map(c -> c.getValue()).findFirst().orElse("");
            return slugs.equals("") ? 0 : slugs.split("/").length;
        } else {
            return 0;
        }
    }

    @ModelAttribute("postponedSize")
    public Integer postponedSize(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            String slugs = Arrays.stream(cookies).filter(c -> c.getName().equals("postponedContents")).map(c -> c.getValue()).findFirst().orElse("");
            return slugs.equals("") ? 0 : slugs.split("/").length;
        } else {
            return 0;
        }
    }

    @ModelAttribute("isMoneyEnough")
    public Boolean isMoneyEnough() {
        return isMoneyEnough;
    }

    public void setIsMoneyEnough(boolean isEnough){
        isMoneyEnough = isEnough;
    }


}
