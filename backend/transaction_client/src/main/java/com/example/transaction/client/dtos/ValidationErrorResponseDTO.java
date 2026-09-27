package com.example.transaction.client.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record ValidationErrorResponseDTO(

  LocalDateTime timestamp,
  int status,
  String message,
  List<FieldErrorDTO> errors

) {
}
