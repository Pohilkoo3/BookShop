package com.example.MyBookShoppApp.interfaces;

import com.example.MyBookShoppApp.model.user.UserContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterfaceUserContact extends JpaRepository<UserContactEntity, Integer>
{

    UserContactEntity findUserContactEntityByContact(String contact);
}
