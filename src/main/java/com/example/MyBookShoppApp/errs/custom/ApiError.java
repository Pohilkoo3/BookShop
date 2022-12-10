package com.example.MyBookShoppApp.errs.custom;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError {
    private int code;
    private String message;
   private String customMessage;
    public ApiError(int code, String message) {
        this.code = code;
        this.message = message;
        this.customMessage = "Вот такая бля ошибка";
    }
    public ApiError() {
    }
}
