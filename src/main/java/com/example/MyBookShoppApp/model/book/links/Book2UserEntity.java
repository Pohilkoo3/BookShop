package com.example.MyBookShoppApp.model.book.links;

import com.example.MyBookShoppApp.keys.BookUserId;
import com.example.MyBookShoppApp.model.genre.GenreEntity;
import com.example.MyBookShoppApp.model.oldEntity.Book;
import com.example.MyBookShoppApp.model.user.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "book2user")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Book2UserEntity implements Serializable {

    @EmbeddedId
    private BookUserId pk;


    @Column(columnDefinition = "date NOT NULL")
    private LocalDateTime time;

    @Column(columnDefinition = "INT NOT NULL")
    private int typeId;

    public Book getBook() {
        return getPk().getBook();
    }

    public UserEntity getUserEntity() {
        return getPk().getUser();
    }

    public void setBook(Book book){
        getPk().setBook(book);
    }

    public void setUser(UserEntity user){
        getPk().setUser(user);
    }




}
