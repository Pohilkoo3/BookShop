package com.example.MyBookShoppApp.services;

import com.example.MyBookShoppApp.dao.TagDao;
import com.example.MyBookShoppApp.model.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService
{
    private TagDao tagDao;

    @Autowired
    public TagService(TagDao tagDao) {
        this.tagDao = tagDao;
    }


    public List<Tag> getAllTags() {
        return tagDao.getTags();
    }

    public Tag getTagById(Integer tagsId)
    {
      return tagDao.getTagById(tagsId);
    }
}
