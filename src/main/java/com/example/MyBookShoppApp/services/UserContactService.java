package com.example.MyBookShoppApp.services;

import com.example.MyBookShoppApp.dao.UserContactDao;
import com.example.MyBookShoppApp.model.user.UserContactEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserContactService
{
    private UserContactDao contactDao;

    @Autowired
    public UserContactService(UserContactDao contactDao) {
        this.contactDao = contactDao;
    }

    public void addNewContact(UserContactEntity userContact)
    {
        contactDao.addNewContact(userContact);
    }

    public UserContactEntity getContactByContact(String email)
    {
      return contactDao.getContactByContact(email);
    }

    public UserContactEntity getContactById(Integer id)
    {
        return contactDao.getUserContactById(id);
    }
}
