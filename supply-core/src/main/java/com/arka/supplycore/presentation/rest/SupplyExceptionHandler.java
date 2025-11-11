package com.arka.supplycore.presentation.rest;

import com.arka.supplycore.application.exception.BusinessException;
import com.arka.supplycore.application.exception.DataNotFoundException;
import com.arka.supplycore.presentation.dto.ErrorResponse;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class SupplyExceptionHandler {
  @ExceptionHandler(DataNotFoundException.class)
  public Mono<ResponseEntity<ErrorResponse>> handleDataNotFoundException(DataNotFoundException ex, ServerWebExchange exchange) {
    return Mono
      .just(
        new ErrorResponse("NOT_FOUND", "Requested resource was not found", LocalDateTime.now(), exchange.getRequest().getPath().value()))
      .map(err -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(err));
  }

  @ExceptionHandler(BusinessException.class)
  public Mono<ResponseEntity<ErrorResponse>> handleBusinessException(BusinessException ex, ServerWebExchange exchange) {
    return Mono
      .just(
        new ErrorResponse("UNPROCESSABLE_ENTITY", ex.getMessage(), LocalDateTime.now(), exchange.getRequest().getPath().value()))
      .map(err -> ResponseEntity.unprocessableEntity().body(err));
  }

  @ExceptionHandler(IllegalStateException.class)
  public Mono<ResponseEntity<ErrorResponse>> handleIllegalStateException(IllegalStateException ex, ServerWebExchange exchange) {
    return Mono
      .just(
        new ErrorResponse("UNPROCESSABLE_ENTITY", ex.getMessage(), LocalDateTime.now(), exchange.getRequest().getPath().value()))
      .map(err -> ResponseEntity.unprocessableEntity().body(err));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public Mono<ResponseEntity<ErrorResponse>> handleIllegalArgumentException(IllegalArgumentException ex, ServerWebExchange exchange) {
    return Mono.just(new ErrorResponse("BAD_REQUEST", ex.getMessage(), LocalDateTime.now(), exchange.getRequest().getPath().value()))
      .map(err -> ResponseEntity.badRequest().body(err));
  }
}
