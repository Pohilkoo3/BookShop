package com.example.MyBookShoppApp.dao;

import com.example.MyBookShoppApp.interfaces.InterfaceBookFileRepo;
import com.example.MyBookShoppApp.model.book.file.BookFileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class BookFileEntityDao {
    @Autowired
    private InterfaceBookFileRepo bookFileRepo;


    public BookFileEntity getBookFilePath(String hash) {
        return bookFileRepo.findBookFileEntityByHash(hash);
    }
}
