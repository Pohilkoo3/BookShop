package com.example.MyBookShoppApp.dao;

import com.example.MyBookShoppApp.interfaces.InterfaceGenreRepo;
import com.example.MyBookShoppApp.model.genre.GenreEntity;
import com.example.MyBookShoppApp.responseEntity.PagingBooksResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class GenreDao
{
    @Autowired
    private InterfaceGenreRepo genreRepo;



    public List<GenreEntity> getGenres()
    {
       return genreRepo.findAll();
    }

    public GenreEntity getGenreByID(int id){
       return genreRepo.findById(id).orElse(null);
    }

    public void setParent(GenreEntity genre) {
        GenreEntity parent = getGenreByID(genre.getParentId());
        if (parent == null) {
            return;
        }
        genre.setParentEntity(parent);
        setParent(parent);
    }

        public LinkedList<GenreEntity> getPath(GenreEntity genre, LinkedList<GenreEntity> list) {
        list.add(genre);
       GenreEntity parent = genre.getParentEntity();
        if (parent != null) {
            getPath(parent, list);
        }

        return list;
    }


}
