package com.example.MyBookShoppApp.model.marks;

import com.example.MyBookShoppApp.keys.BookMarkId;
import com.example.MyBookShoppApp.model.oldEntity.Book;
import com.example.MyBookShoppApp.model.user.UserEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "book2mark")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Book2UsersMark implements Serializable
{
    @EmbeddedId
    private BookMarkId pk = new BookMarkId();

    @Column(name = "mark")
    private int mark;

    public Book getBook(){
        return getPk().getBook();
    }

    public UserEntity getUser(){
        return getPk().getUser();
    }

    public void setBook(Book book){
        getPk().setBook(book);
    }

    public void setUser(UserEntity user){
        getPk().setUser(user);
    }



}
