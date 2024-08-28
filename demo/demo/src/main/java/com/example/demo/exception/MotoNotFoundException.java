package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Moto not found in database")
public class MotoNotFoundException extends Exception {

    public MotoNotFoundException(Long id) {
        super("Moto with id " + id + " not found");
    }
}