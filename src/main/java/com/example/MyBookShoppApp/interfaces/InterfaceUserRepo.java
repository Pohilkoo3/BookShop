package com.example.MyBookShoppApp.interfaces;

import com.example.MyBookShoppApp.model.oldEntity.Book;
import com.example.MyBookShoppApp.model.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;

@Repository
public interface InterfaceUserRepo extends JpaRepository<UserEntity, Integer>
{

    List<UserEntity> findAllById(Integer id);

    @Query(value = "SELECT * FROM users WHERE id=(SELECT user_id FROM user_contact WHERE contact=?1)", nativeQuery = true)
    List<UserEntity> findUserByContact(String contact);

}
