package com.kizerov.caloriescalculator.controller;

import com.kizerov.caloriescalculator.exception.RegistrationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler({DuplicateKeyException.class, IllegalArgumentException.class,
            RegistrationException.class, UsernameNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleDuplicateAndIllegalArgumentAndRegistrationException(Exception ex) {

        ModelAndView modelAndView = new ModelAndView("errors/error-template");
        modelAndView.addObject("errorMessage", ex.getMessage());

        return modelAndView;
    }

}