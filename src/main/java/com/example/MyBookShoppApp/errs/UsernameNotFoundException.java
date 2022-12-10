package com.example.MyBookShoppApp.errs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsernameNotFoundException extends Exception
{
    private String customMessage;

    public UsernameNotFoundException(String customMessage) {
        this.customMessage = customMessage;
    }
}
