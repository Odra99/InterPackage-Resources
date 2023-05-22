package com.interpackage.resources.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.interpackage.resources.model.Response;

@ControllerAdvice
public class ValidationErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleValidationErrors(MethodArgumentNotValidException ex) {
        return ResponseEntity
                .badRequest()
                .body(new Response("Todos los campos son requeridos."));
    }

}