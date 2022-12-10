package com.example.MyBookShoppApp.errs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserNoRegistrated extends Exception {
    private String customMessage;
    private String slugBook;
    public UserNoRegistrated(String slugBook) {
       this.slugBook = slugBook;
    }
}
