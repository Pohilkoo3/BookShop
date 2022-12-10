package com.example.MyBookShoppApp.errs.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class AppException {

    @ExceptionHandler(value={NoHandlerFoundException.class})
    public String badRequest(Exception e, HttpServletRequest request, HttpServletResponse response) {
        e.printStackTrace();
        new ApiError(400, HttpStatus.BAD_REQUEST.getReasonPhrase());
        return "404";
    }
}
