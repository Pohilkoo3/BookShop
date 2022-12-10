package com.example.MyBookShoppApp.dao;

import com.example.MyBookShoppApp.interfaces.InterfaceTagRepo;
import com.example.MyBookShoppApp.model.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagDao
{
    @Autowired
    private InterfaceTagRepo tagRepo;


    public List<Tag> getTags()
    {
      return tagRepo.findAll();
    }

    public Tag getTagById(Integer tagsId)
    {
        return tagRepo.findById(tagsId).orElse(null);
    }
}
