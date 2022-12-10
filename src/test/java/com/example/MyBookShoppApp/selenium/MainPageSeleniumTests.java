package com.example.MyBookShoppApp.selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MainPageSeleniumTests {

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
    public void testMainPageAccess() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage
                .callPage()
                .pause();

        assertTrue(driver.getPageSource().contains("Main Page"));
    }

    @Test
    public void testMainPageSearchByQuerry() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage
                .callPage()
                .pause()
                .setUpSearchToken("Oleg")
                .pause()
                .submit()
                .pause();

        assertTrue(driver.getPageSource().contains("Oleg Star"));

    }

}