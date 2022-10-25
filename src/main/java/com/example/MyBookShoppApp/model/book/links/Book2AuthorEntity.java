package com.example.MyBookShoppApp.model.book.links;


import com.example.MyBookShoppApp.keys.BookAuthorId;
import com.example.MyBookShoppApp.model.oldEntity.Author;
import com.example.MyBookShoppApp.model.oldEntity.Book;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "book2author")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Book2AuthorEntity implements Serializable {

    @EmbeddedId
    private BookAuthorId pk;

    @Column(columnDefinition = "INT NOT NULL DEFAULT 0")
    private int sortIndex;

   public Book getBook(){
       return getPk().getBook();
   }

   public Author getAuthor(){
       return getPk().getAuthor();
   }

   public void setBook(Book book){
       getPk().setBook(book);
    }

    public void setAuthor(Author author){
       getPk().setAuthor(author);
    }
}
