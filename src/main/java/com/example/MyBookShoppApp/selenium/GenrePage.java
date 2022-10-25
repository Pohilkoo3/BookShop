package com.example.MyBookShoppApp.selenium;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;

public class GenrePage {
    private String url = "http://localhost:8085/genres";
    private ChromeDriver driver;

    @Autowired
    public GenrePage(ChromeDriver driver) {
        this.driver = driver;
    }


    public GenrePage callPage() {
        driver.get(url);
        return this;
    }

    public GenrePage pause() throws InterruptedException {
        Thread.sleep(2000);
        return this;
    }

public GenrePage refOnGenreFantastic(){
        WebElement element = driver.findElement(By.xpath("/html/body/div[3]/div/main/div/div/div/div[1]/div/div[2]/div[1]/div[1]/a"));
        element.click();
        return this;
    }


}
