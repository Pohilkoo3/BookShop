package com.example.MyBookShoppApp.util_object;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class SearchIdForGenre
{

    public String idGenre;

    public SearchIdForGenre(String id) {
        this.idGenre = id;
    }

}
