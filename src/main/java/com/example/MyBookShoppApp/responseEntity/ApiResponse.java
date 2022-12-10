package com.example.MyBookShoppApp.responseEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import springfox.documentation.spring.web.json.Json;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
public class ApiResponse<T>
{
    private HttpStatus httpStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-mm-yyyy hh:mm:ss")
    private LocalDateTime timeStamp;

    private String message;
    private String debugMessage;
    @JsonProperty(value = "books")
    private Collection<T> data;

    public ApiResponse()
    {
        this.timeStamp = LocalDateTime.now();
    }


    public ApiResponse(HttpStatus httpStatus, String message, Throwable ex) {
        this();
        this.httpStatus = httpStatus;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }
}


