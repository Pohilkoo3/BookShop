package com.example.MyBookShoppApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
public class SpringfoxConfig
{

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.MyBookShoppApp.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    public ApiInfo apiInfo(){
        return  new ApiInfo(
                "API title",
                "API description",
                "API version",
                "hhtp://www.termsOffService.com",
                new Contact("Oleg", "http://www.oleg.com", "Pohilkoo@rambler.ru"),
                "API license",
                "lecense URL",
                new ArrayList<>()
        );
    }
}
