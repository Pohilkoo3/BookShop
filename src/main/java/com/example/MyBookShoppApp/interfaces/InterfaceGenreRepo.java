package com.example.MyBookShoppApp.interfaces;

import com.example.MyBookShoppApp.model.genre.GenreEntity;
import com.example.MyBookShoppApp.model.oldEntity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterfaceGenreRepo extends JpaRepository<GenreEntity, Integer> {



}
