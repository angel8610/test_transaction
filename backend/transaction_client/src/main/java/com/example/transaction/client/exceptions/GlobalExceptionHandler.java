package com.example.transaction.client.exceptions;

import com.example.transaction.client.dtos.FieldErrorDTO;
import com.example.transaction.client.dtos.ValidationErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrorResponseDTO> handleValidation(
    MethodArgumentNotValidException ex) {

    List<FieldErrorDTO> errors = ex.getBindingResult()
      .getFieldErrors()
      .stream()
      .map(error -> new FieldErrorDTO(
        error.getField(),
        error.getDefaultMessage()))
      .toList();

    ValidationErrorResponseDTO response =
      new ValidationErrorResponseDTO(
        LocalDateTime.now(),
        HttpStatus.BAD_REQUEST.value(),
        "Validation failed",
        errors
      );

    return ResponseEntity.badRequest().body(response);
  }


}
