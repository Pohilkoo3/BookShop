package com.example.MyBookShoppApp.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;

public class News {
    private String url = "http://localhost:8085/books/recent";
    private ChromeDriver driver;

    @Autowired
    public News(ChromeDriver driver) {
        this.driver = driver;
    }

    public News callPage() {
        driver.get(url);
        return this;
    }

    public News pause() throws InterruptedException {
        Thread.sleep(3000);
        return this;
    }

    public News redirectOnBook() {
        WebElement element = driver.findElement(By.xpath("/html/body/div[3]/div/main/div/div[2]/div[1]/div[1]/a/img"));
        element.click();
        return this;
    }

}
