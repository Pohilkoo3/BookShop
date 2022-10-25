package com.example.MyBookShoppApp.services;
import com.example.MyBookShoppApp.dao.AuthorsDao;
import com.example.MyBookShoppApp.model.oldEntity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private AuthorsDao authorsDao;

    @Autowired
    public AuthorService(AuthorsDao authorsDao) {
        this.authorsDao = authorsDao;
    }

    public Map<String, List<Author>> getAuthorsMap() {
        Map<String, List<Author>> authorsMap = new HashMap<>();
        List<Author> authorList = authorsDao.getAllAuthors();
        authorsMap.putAll(authorList.stream().collect(Collectors.groupingBy(a -> a.getName().substring(0, 1))));
        return authorsMap;
    }

    public Author getAuthorById(Integer idAuthor)
    {
      return authorsDao.getAuthorById(idAuthor);
    }
}
