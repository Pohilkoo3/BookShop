package com.example.MyBookShoppApp.security.black_list;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class BlackListServiceTest {

    private BlackListService blackListService;
    private BlackListRepository listRepository;

    @Autowired
    public BlackListServiceTest(BlackListService blackListService, BlackListRepository listRepository) {
        this.blackListService = blackListService;
        this.listRepository = listRepository;
    }

    @Test
    void isTokenIsBlack() {
        String token = "2jxljkfsl343lkj324k542l5k2";
        boolean isTokenBlack = blackListService.isTokenIsBlack(token);
        assertTrue(isTokenBlack);
    }

    @Test
    public void findBlackTokenByToken(){
        String token = "2jxljkfsl343lkj324k542l5k2";
        BlackTokenEntity blackToken = listRepository.findBlackTokenEntityByBlackToken(token);
        assertNotNull(blackToken);
        assertTrue(blackToken.getId() > 0);
        assertEquals("2jxljkfsl343lkj324k542l5k2", blackToken.getBlackToken());
    }


}