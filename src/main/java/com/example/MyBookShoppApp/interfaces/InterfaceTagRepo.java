package com.example.MyBookShoppApp.interfaces;

import com.example.MyBookShoppApp.model.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfaceTagRepo extends JpaRepository<Tag, Integer> {
}
