package com.example.MyBookShoppApp.dao;

import com.example.MyBookShoppApp.custom_annotation.ObjectException;
import com.example.MyBookShoppApp.interfaces.InterfaceUserContact;
import com.example.MyBookShoppApp.interfaces.InterfaceUserRepo;
import com.example.MyBookShoppApp.model.user.UserEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDao {

    @Autowired
    private InterfaceUserRepo userRepo;
    @Autowired
    private InterfaceUserContact userContactRepo;

//    @ObjectException
    public UserEntity getUserById(Integer id) {
        List<UserEntity> user = userRepo.findAllById(id);
        return user.get(0);
    }

    public void addNewUser(UserEntity user) {
        userRepo.save(user);
    }

//    @ObjectException
    public UserEntity findUserByContact(String email) {
        List<UserEntity> userEntityList = userRepo.findUserByContact(email);
        return  userEntityList.size() != 0 ? userEntityList.get(0) : null;
    }
}
