package com.example.MyBookShoppApp.dao;

import com.example.MyBookShoppApp.model.oldEntity.Author;
import com.example.MyBookShoppApp.interfaces.InterfaceAuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorsDao
{
    @Autowired
    private InterfaceAuthorRepo authorRepo;

    public List<Author> getAllAuthors()
    {
        return authorRepo.findAll();
    }

    public Author getAuthorById(Integer idAuthor)
    {
        return authorRepo.findById(idAuthor).orElse(null);
    }
}
