package com.example.MyBookShoppApp.model.book.links;

import com.example.MyBookShoppApp.keys.BookGenreId;
import com.example.MyBookShoppApp.model.genre.GenreEntity;
import com.example.MyBookShoppApp.model.oldEntity.Book;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "book2genre")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Book2GenreEntity implements Serializable {

    @EmbeddedId
    private BookGenreId pk;

    public Book getBook() {
        return getPk().getBook();
    }

    public long getBookId(){
        return getPk().getBook().getId();
    }

    public GenreEntity getGenreEntity() {
        return getPk().getGenre();
    }

    public long getGenreId(){
        return getPk().getGenre().getId();
    }

    public void setBook(Book book){
        getPk().setBook(book);
    }

    public void setGenre(GenreEntity genreEntity){
        getPk().setGenre(genreEntity);
    }


}
