package com.example.MyBookShoppApp.services;

import com.example.MyBookShoppApp.dao.GenreDao;
import com.example.MyBookShoppApp.model.genre.GenreEntity;
import com.example.MyBookShoppApp.responseEntity.PagingBooksResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class GenreService
{
    private GenreDao genreDao;

    @Autowired
    public GenreService(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    public List<GenreEntity> getGenres()
    {
        return genreDao.getGenres();
    }

    public GenreEntity getGenreByID(int id){
        return genreDao.getGenreByID(id);
    }

    public void setParent(GenreEntity genre) {
        genreDao.setParent(genre);
    }

    public LinkedList<GenreEntity> getPath(GenreEntity genre, LinkedList<GenreEntity> list)
    {
       return genreDao.getPath(genre, list);
    }
}
