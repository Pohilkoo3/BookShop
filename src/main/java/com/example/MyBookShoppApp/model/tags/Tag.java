package com.example.MyBookShoppApp.model.tags;


import com.example.MyBookShoppApp.model.oldEntity.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "book2tag",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> bookSet;

    public void addBook(Book book) {
        bookSet.add(book);
    }
}
