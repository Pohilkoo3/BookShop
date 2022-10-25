package com.example.MyBookShoppApp.custom_annotation;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Getter
@Setter
public class CustomAnnotationException extends Exception {

    private String customMessage;

    private String exception;

    private LocalDateTime timeException;

    private String nameMethodInvoked;

    public CustomAnnotationException(String customMessage) {
        this.customMessage = customMessage;
        this.timeException = LocalDateTime.now();
    }

    public String getFormatTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        String time = timeException.format(formatter);
        return time;
    }
}

