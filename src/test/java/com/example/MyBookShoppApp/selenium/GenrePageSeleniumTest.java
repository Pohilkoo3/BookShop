package com.example.MyBookShoppApp.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class GenrePageSeleniumTest {
    private static ChromeDriver driver;

    @BeforeAll
    static void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/Oleg/Desktop/YandexDriver/yandexdriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }
    @Test
    public void genrePageTest() throws InterruptedException {
        GenrePage genrePage = new GenrePage(driver);
        genrePage
                .callPage()
                .pause();
        assertTrue(driver.getPageSource().contains("Genres"));
    }

    @Test
    public void genrePageNewGenre() throws InterruptedException {
        GenrePage genrePage = new GenrePage(driver);
        genrePage
                .callPage()
                .pause()
                .refOnGenreFantastic()
                .pause();
        WebElement element = driver.findElement(By.xpath("/html/body/div[3]/div/main/ul/li[4]/a"));
        assertEquals(element.getText(), "Фантастика");


    }
}