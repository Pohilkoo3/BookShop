package com.example.MyBookShoppApp.custom_annotation;

import org.apache.logging.log4j.LogManager;
import org.aspectj.lang.JoinPoint;

import org.aspectj.lang.annotation.*;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class ObjectAnnotation {

    Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    private org.apache.logging.log4j.Logger loggerErrors = LogManager.getLogger("Errors");
    private String classEx = "";
    @Pointcut(value = "@annotation(com.example.MyBookShoppApp.custom_annotation.ObjectException)")
    public void exceptionPointcut(){}

    @Before(value = "exceptionPointcut()")
    public void getClassMy(JoinPoint point){
        classEx = ((MethodInvocationProceedingJoinPoint) point).getSignature().toString();
    }
    @AfterThrowing(pointcut = "exceptionPointcut()", throwing = "ex")
    public void getExceptionCustom(Exception ex) throws CustomAnnotationException {
        logger.info("Checked the exception" + classEx);
        CustomAnnotationException customAnnotationException = new CustomAnnotationException("Our custom mistake appeared");
        customAnnotationException.setException(ex.getMessage());
        customAnnotationException.setNameMethodInvoked(classEx);
        loggerErrors.info(customAnnotationException.getTimeException() + " => " + customAnnotationException.getNameMethodInvoked());
        throw customAnnotationException;
    }
}
