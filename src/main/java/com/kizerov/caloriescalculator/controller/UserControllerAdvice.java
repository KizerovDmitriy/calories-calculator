package com.kizerov.caloriescalculator.controller;

import com.kizerov.caloriescalculator.exception.RegistrationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(RegistrationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleUserNotFoundException(RegistrationException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}