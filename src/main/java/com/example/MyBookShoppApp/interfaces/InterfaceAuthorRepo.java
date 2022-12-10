package com.example.MyBookShoppApp.interfaces;

import com.example.MyBookShoppApp.model.oldEntity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfaceAuthorRepo extends JpaRepository<Author, Integer> {
}
