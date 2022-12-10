package com.example.MyBookShoppApp.interfaces;

import com.example.MyBookShoppApp.model.book.file.BookFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfaceBookFileRepo extends JpaRepository<BookFileEntity, Integer>
{

    public BookFileEntity findBookFileEntityByHash(String hash);
}
