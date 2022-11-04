package com.example.MyBookShoppApp.model.user;

import com.example.MyBookShoppApp.model.book.file.FileDownloadEntity;
import com.example.MyBookShoppApp.model.book.links.Book2UserEntity;
import com.example.MyBookShoppApp.model.book.review.BookReviewEntity;
import com.example.MyBookShoppApp.model.book.review.BookReviewLikeEntity;
import com.example.MyBookShoppApp.model.book.review.MessageEntity;
import com.example.MyBookShoppApp.model.marks.Book2UsersMark;
import com.example.MyBookShoppApp.model.payments.BalanceTransactionEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "VARCHAR(255)")
    private String hash;

    @Column(columnDefinition = "VARCHAR(255)")
    private String name;

    @Column(columnDefinition = "date ")
    private LocalDateTime regTime;

    @Column(columnDefinition = "INT")
    private int balance;

    @Column(name = "password")
    String pass;

    @OneToMany(mappedBy = "pk.user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Book2UserEntity> book2UserEntitySet = new HashSet<>(0);

    @OneToMany(mappedBy = "pk.user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<FileDownloadEntity> fileDownloadEntitySet = new HashSet<>(0);

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<BalanceTransactionEntity> balanceTransactionEntities = new HashSet<>(0);

    @OneToMany(mappedBy = "pk.user")
    @JsonIgnore
    private Set<BookReviewEntity> bookReviewEntitySet = new HashSet<>(0);

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<MessageEntity> messagesSet = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<BookReviewLikeEntity> bookReviewLikeEntitySet = new HashSet<>();

    @OneToMany(mappedBy = "pk.user")
    @JsonIgnore
    private Set<Book2UsersMark> book2UsersMarkSet = new HashSet<>(0);


}
