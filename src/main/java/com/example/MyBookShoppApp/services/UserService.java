package com.example.MyBookShoppApp.services;

import com.example.MyBookShoppApp.dao.UserDao;
import com.example.MyBookShoppApp.model.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserEntity getUserById(Integer id){
        return userDao.getUserById(id);
    }

    public void addNewUser(UserEntity user)
    {
        userDao.addNewUser(user);
    }


    public UserEntity findUserByContact(String email)
    {
        UserEntity userEntity = userDao.findUserByContact(email);
        return userEntity;
    }


}
