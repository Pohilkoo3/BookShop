package com.example.MyBookShoppApp;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class MyBookShoppAppApplicationTests {

    @Value("${auth.secret}")
    private String secret;
    private final MyBookShoppAppApplication appApplication;

    @Autowired
    public MyBookShoppAppApplicationTests(MyBookShoppAppApplication appApplication) {
        this.appApplication = appApplication;
    }

    @Test
    void contextLoads() {
        assertNotNull(appApplication);
    }

    @Test
    void verifyAuthSecret(){
        assertThat(secret, Matchers.containsString("box"));
    }



}
