package com.telerikacademy.web.forumsystem.controllers.mvc;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleTypeMismatchException(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "NotFound";
    }

}
