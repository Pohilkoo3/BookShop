package com.example.MyBookShoppApp;

import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootApplication
@PropertySource("classpath:spring-frontend/lang/messages_ru.properties")

public class MyBookShoppAppApplication {

	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		SpringApplication.run(MyBookShoppAppApplication.class, args);
	}


}
