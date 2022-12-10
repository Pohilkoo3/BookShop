package com.example.MyBookShoppApp.model.payments;


import com.example.MyBookShoppApp.keys.BookTransactionId;
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
@Table(name = "book2balance_transaction")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Book2BalanceTransaction implements Serializable {

    @EmbeddedId
    private BookTransactionId pk = new BookTransactionId();


    public Book getBook(){
        return pk.getBook();
    }

    public BalanceTransactionEntity getBalanceTransactionEntity(){
        return pk.getBalanceTransaction();
    }

    public void setBalanceTransactionEntity(BalanceTransactionEntity balanceTransaction){
        pk.setBalanceTransaction(balanceTransaction);
    }
    public void setBook(Book book){
        pk.setBook(book);
    }




}
