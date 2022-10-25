package com.example.MyBookShoppApp.errs;

public class BookStoreApiWrongParameterException extends Exception {
    public BookStoreApiWrongParameterException(String message) {
        super(message);
    }
}
