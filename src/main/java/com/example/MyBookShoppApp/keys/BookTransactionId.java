package com.example.MyBookShoppApp.keys;

import com.example.MyBookShoppApp.model.oldEntity.Book;
import com.example.MyBookShoppApp.model.payments.BalanceTransactionEntity;
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
public class BookTransactionId implements Serializable {

    @ManyToOne
    private Book book;

    @ManyToOne
    private BalanceTransactionEntity balanceTransaction;
}
