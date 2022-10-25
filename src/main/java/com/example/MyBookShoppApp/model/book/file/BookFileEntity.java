package com.example.MyBookShoppApp.model.book.file;

import com.example.MyBookShoppApp.model.oldEntity.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "book_file")
public class BookFileEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String hash;

    @Column(nullable = false)
    private String path;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;


    @ManyToOne
    @JoinColumn(name = "type_id")
    private BookFileTypeEntity bookFileType;
}
