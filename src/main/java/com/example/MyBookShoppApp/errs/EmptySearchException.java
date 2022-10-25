package com.example.MyBookShoppApp.errs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmptySearchException extends Exception {

    private String customMessage;

    public EmptySearchException(String message) {
        super(message);
    }
}
