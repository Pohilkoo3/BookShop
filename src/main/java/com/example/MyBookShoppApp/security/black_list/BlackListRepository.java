package com.example.MyBookShoppApp.security.black_list;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListRepository extends JpaRepository<BlackTokenEntity, Integer>
{

    BlackTokenEntity findBlackTokenEntityByBlackToken(String token);
}
