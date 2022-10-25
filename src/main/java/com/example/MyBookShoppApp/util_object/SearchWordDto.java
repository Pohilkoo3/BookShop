package com.example.MyBookShoppApp.util_object;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class SearchWordDto
{
    public String example;

    public SearchWordDto(String example) {
        this.example = example;
    }
}
