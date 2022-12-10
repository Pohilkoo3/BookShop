package com.example.MyBookShoppApp.dao;

import com.example.MyBookShoppApp.interfaces.InterfaceUserContact;
import com.example.MyBookShoppApp.model.user.UserContactEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserContactDao {

    private final InterfaceUserContact userContactRepo;

    @Autowired
    public UserContactDao(InterfaceUserContact userContactRepo) {
        this.userContactRepo = userContactRepo;
    }

    public void addNewContact(UserContactEntity userContact)
    {
        userContactRepo.save(userContact);
    }

    public UserContactEntity getContactByContact(String contact)
    {
        return userContactRepo.findUserContactEntityByContact(contact);
    }

    public UserContactEntity getUserContactById(Integer id)
    {
        return userContactRepo.findById(id).orElse(null);
    }
}
