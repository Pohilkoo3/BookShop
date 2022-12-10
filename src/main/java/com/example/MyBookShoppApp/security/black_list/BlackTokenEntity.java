package com.example.MyBookShoppApp.security.black_list;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "black_list")
public class BlackTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String blackToken;
}
