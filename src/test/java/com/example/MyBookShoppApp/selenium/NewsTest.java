package com.example.MyBookShoppApp.selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NewsTest {
    private static ChromeDriver driver;

    @BeforeAll
    static void setup() throws IOException {
        System.setProperty("webdriver.chrome.driver", "C:/Users/Oleg/Desktop/YandexDriver/yandexdriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
    }

    @AfterAll
    static void tearDown(){
        driver.quit();
    }

    @Test
    public void testRedirectOnNewsPage() throws InterruptedException {
        News newsPage = new News(driver);
        newsPage
                .callPage()
                .pause()
                .redirectOnBook()
                .pause();
        assertEquals("Book", driver.getTitle());
    }

}