package com.example.MyBookShoppApp.custom_annotation.aspect;

import com.example.MyBookShoppApp.security.RegistrationForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RegistrationAspect {
    Logger loggerUsers = LogManager.getLogger("Successful");


    @Pointcut(value = "execution(* handlerUserRegistration*(..))")
    public void registrationPointcut(){}

    @Before(value = "registrationPointcut()")
    public void loggingUsers(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        Object form =  args[1];
        String name = ((RegistrationForm) form).getName();
        String email = ((RegistrationForm) form).getEmail();
        String phone = ((RegistrationForm) form).getPhone();
        String pass  = ((RegistrationForm) form).getPass();
        loggerUsers.info("User " + name + " registered: email → " + email + "; pass → " + pass + "; phone → " + phone);
    }

}
