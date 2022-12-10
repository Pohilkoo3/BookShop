package com.example.MyBookShoppApp.services;

import com.example.MyBookShoppApp.dao.BookFileEntityDao;
import com.example.MyBookShoppApp.model.book.file.BookFileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class BookFileEntityService
{
    private BookFileEntityDao bookFileEntityDao;

    @Autowired
    public BookFileEntityService(BookFileEntityDao bookFileEntityDao) {
        this.bookFileEntityDao = bookFileEntityDao;
    }


    public BookFileEntity getBookFilePath(String hash)
    {
      return bookFileEntityDao.getBookFilePath(hash);
    }
}
