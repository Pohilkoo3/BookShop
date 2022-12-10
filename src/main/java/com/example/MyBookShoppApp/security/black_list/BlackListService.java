package com.example.MyBookShoppApp.security.black_list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlackListService {
    @Autowired
    private BlackListRepository blackListRepository;

    public boolean isTokenIsBlack(String token){
        BlackTokenEntity blackToken = blackListRepository.findBlackTokenEntityByBlackToken(token);
        return blackToken != null;
    }
}
