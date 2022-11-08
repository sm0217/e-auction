package com.auction.products.exception;

import static java.util.Collections.singletonList;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

  @ExceptionHandler(value = {ResponseStatusException.class})
  protected ResponseEntity<Object> handle(ResponseStatusException ex, WebRequest request) {
    return new ResponseEntity<>(
        new ErrorResponse(LocalDateTime.now(), singletonList(ex.getReason())), ex.getStatus());
  }

  //  @ExceptionHandler(value = {Exception.class})
  //  protected ResponseEntity<ErrorResponse> handle(Exception ex, WebRequest request) {
  //    return new ResponseEntity<>(
  //        new ErrorResponse(LocalDateTime.now(), singletonList(ex.getMessage())),
  //        HttpStatus.INTERNAL_SERVER_ERROR);
  //  }

  @ExceptionHandler({ConstraintViolationException.class})
  public ResponseEntity<Object> handleConstraintViolation(
      ConstraintViolationException ex, WebRequest request) {
    return new ResponseEntity<>(
        new ErrorResponse(
            LocalDateTime.now(),
            ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList())),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity<Object> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex, WebRequest request) {
    return new ResponseEntity<>(
        new ErrorResponse(
            LocalDateTime.now(),
            ex.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList())),
        HttpStatus.BAD_REQUEST);
  }
}
