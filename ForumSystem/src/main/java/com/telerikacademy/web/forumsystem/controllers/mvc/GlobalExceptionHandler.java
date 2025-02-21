package com.telerikacademy.web.forumsystem.controllers.mvc;

import com.telerikacademy.web.forumsystem.exceptions.EntityNotFoundException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleTypeMismatchException(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "NotFound";
    }

//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    public String handleTypeMismatchException(MethodArgumentTypeMismatchException ex, Model model) {
//        model.addAttribute("errorMessage", "400 - Invalid request! Please check your input.");
//        return "NotFound";
//    }
//
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public String handleHttpMessageNotReadable(HttpMessageNotReadableException ex, Model model) {
//        model.addAttribute("errorMessage", "400 - Malformed request! Please check your request format.");
//        return "NotFound";
//    }
//
//    @ExceptionHandler(MissingServletRequestParameterException.class)
//    public String handleMissingParams(MissingServletRequestParameterException ex, Model model) {
//        model.addAttribute("errorMessage", "400 - Required parameters are missing!");
//        return "NotFound";
//    }
//
//    @ExceptionHandler(EntityNotFoundException.class)
//    public String handleEntityNotFoundException(EntityNotFoundException ex, Model model) {
//        model.addAttribute("errorMessage", "404 - The page you are looking for doesn't exist!");
//        return "NotFound";
//    }
}
