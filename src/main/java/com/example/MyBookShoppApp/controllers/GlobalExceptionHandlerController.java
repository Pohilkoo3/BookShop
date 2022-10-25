package com.example.MyBookShoppApp.controllers;

import com.example.MyBookShoppApp.custom_annotation.CustomAnnotationException;
import com.example.MyBookShoppApp.errs.EmptySearchException;
import com.example.MyBookShoppApp.errs.UserNoRegistrated;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.plexus.logging.LoggerManager;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandlerController {


    @ExceptionHandler(EmptySearchException.class)
    public String handlerEmptySearchException(EmptySearchException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("searchError", e);
        return "redirect:/";
    }

    @ExceptionHandler(IllegalStateException.class)
    public String getEmptySearchExceptionIllegal(IllegalStateException ex, RedirectAttributes redirectAttributes) {
        EmptySearchException emptySearchException = new EmptySearchException(ex.getMessage());
        emptySearchException.setCustomMessage("Нулевой поиск невозможен!");

        redirectAttributes.addFlashAttribute("searchError", emptySearchException);

        return "redirect:/";
    }

    @ExceptionHandler(UserNoRegistrated.class)
    public String getUserNoRegistrated(UserNoRegistrated ex, RedirectAttributes redirectAttributes) {
        UserNoRegistrated userNoRegistrated= new UserNoRegistrated();
        userNoRegistrated.setCustomMessage("User not register. Please signup.!");
        redirectAttributes.addFlashAttribute("isUserNotRegister", true);
        redirectAttributes.addFlashAttribute("userNotRegister", userNoRegistrated);
        return "redirect:/books/" + ex.getSlugBook();
    }



    @ExceptionHandler({RequestRejectedException.class})
    public String getRequestRejectedException() {
        return "404";
    }

    @ExceptionHandler(CustomAnnotationException.class)
    public String getExceptionCustomAnnotationException(Model model, CustomAnnotationException exception){
        model.addAttribute("isCustomAnnotationException", true);
        model.addAttribute("customAnnotationException", exception);
        return "404";

    }





}
