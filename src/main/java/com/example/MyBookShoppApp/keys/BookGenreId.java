package com.example.MyBookShoppApp.keys;

import com.example.MyBookShoppApp.model.genre.GenreEntity;
import com.example.MyBookShoppApp.model.oldEntity.Book;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class BookGenreId implements Serializable {
    @ManyToOne
    private Book book;
    @ManyToOne
    private GenreEntity genre;
}
