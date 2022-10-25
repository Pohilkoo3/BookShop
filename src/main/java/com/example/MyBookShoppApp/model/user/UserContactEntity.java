package com.example.MyBookShoppApp.model.user;

import com.example.MyBookShoppApp.model.enums.ContactType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_contact")
@Data
public class UserContactEntity {

    @Id
    private Integer id;


    private ContactType type;

    @Column(columnDefinition = "SMALLINT")
    private int approved;

    @Column(columnDefinition = "VARCHAR(255)")
    private String code;

    @Column(columnDefinition = "INT")
    private int codeTrails;

    @Column(columnDefinition = "date")
    private LocalDateTime codeTime;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String contact;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JsonIgnore
    private UserEntity user;


}
