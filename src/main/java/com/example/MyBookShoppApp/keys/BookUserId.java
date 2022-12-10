package com.example.MyBookShoppApp.keys;

import com.example.MyBookShoppApp.model.oldEntity.Book;
import com.example.MyBookShoppApp.model.user.UserEntity;
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
public class BookUserId implements Serializable
{
    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private Book book;
}
