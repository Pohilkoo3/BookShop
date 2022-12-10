package com.example.MyBookShoppApp.model.book.file;

import com.example.MyBookShoppApp.keys.BookUserId;
import com.example.MyBookShoppApp.model.oldEntity.Book;
import com.example.MyBookShoppApp.model.user.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "file_download")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class FileDownloadEntity implements Serializable {

    @EmbeddedId
    private BookUserId pk;

    @Column(columnDefinition = "INT NOT NULL DEFAULT 1")
    private int count;

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
