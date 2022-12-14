package com.example.MyBookShoppApp.selenium;

import org.apache.catalina.connector.Connector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class MainPage {
    private ChromeDriver driver;
    private String url = "http://localhost:8085/";

    public MainPage(ChromeDriver driver) {
        this.driver = driver;
    }

    public MainPage callPage()
    {
        driver.get(url);
        return this;
    }

    public MainPage pause() throws InterruptedException {
        Thread.sleep(2000);
        return this;
    }

    public MainPage setUpSearchToken(String token) {
        WebElement element = driver.findElement(By.id("query"));
        element.sendKeys(token);
        return this;
    }

    public MainPage submit()
    {
        WebElement element = driver.findElement(By.id("search"));
        element.submit();
        return this;
    }
}
