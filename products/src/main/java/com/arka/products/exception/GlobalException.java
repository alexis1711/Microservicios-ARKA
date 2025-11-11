package com.arka.products.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleValidationExceptions(WebExchangeBindException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        ex.getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Validación fallida");
        response.put("details", errors);

        return Mono.just(ResponseEntity.badRequest().body(response));
    }
}
