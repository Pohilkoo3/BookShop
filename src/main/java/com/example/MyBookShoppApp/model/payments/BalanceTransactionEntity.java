package com.example.MyBookShoppApp.model.payments;

import com.example.MyBookShoppApp.keys.BookUserId;
import com.example.MyBookShoppApp.model.oldEntity.Book;
import com.example.MyBookShoppApp.model.user.UserEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "balance_transaction")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class BalanceTransactionEntity implements Serializable {

    @EmbeddedId
    private BookUserId pk;

    @Column(columnDefinition = "date NOT NULL")
    private LocalDateTime time;

    @Column(columnDefinition = "INT NOT NULL  DEFAULT 0")
    private int value;

     @Column(columnDefinition = "TEXT NOT NULL")
    private String description;
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
