package com.example.MyBookShoppApp.custom_annotation;

import com.example.MyBookShoppApp.dao.UserDao;
import com.example.MyBookShoppApp.model.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TryCustomExceptionController {


    private UserDao userDao;

    @Autowired
    public TryCustomExceptionController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/try/contact")
    @ResponseBody
    public UserEntity getUserByEmail(@RequestParam("email") String email){
        UserEntity user = userDao.findUserByContact(email);
        return user;
    }

    @GetMapping("/try/id")
    @ResponseBody
    public UserEntity getUserById(@RequestParam("id") Integer id){
        UserEntity user = userDao.getUserById(id);
        return user;
    }


}
