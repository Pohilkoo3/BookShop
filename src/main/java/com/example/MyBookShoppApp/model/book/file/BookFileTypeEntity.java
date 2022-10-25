package com.example.MyBookShoppApp.model.book.file;

import com.fasterxml.jackson.annotation.JsonIgnore;
import liquibase.pro.packaged.A;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book_file_type")
@Data
public class BookFileTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "bookFileType", cascade = CascadeType.ALL)
    private List<BookFileEntity> list = new ArrayList<>();


}
