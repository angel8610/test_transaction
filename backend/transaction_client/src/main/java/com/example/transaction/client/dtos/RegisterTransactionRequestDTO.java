package com.example.transaction.client.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterTransactionRequestDTO(

  @NotBlank(message = "The operation field is required")
  @Pattern(regexp = "^[a-zA-Z]+$", message = "The operation field operation is invalid")
  @Size(max = 50, message = "The operation field must be less than 50 characters")
  String operation,

  @NotBlank(message = "The amount field is required")
  @Pattern(regexp = "^\\d+(\\.\\d{1,2})?$", message = "The amount field is invalid")
  @Size(max = 16, message = "The amount field must be less than 16 characters")
  String amount,

  @NotBlank(message = "The client field is required")
  @Pattern(regexp = "^[a-zA-Z]+$", message = "The client field is invalid")
  @Size(max = 250, message = "The client field must be less than 250 characters")
  String client,

  @NotBlank(message = "The secret field is required")
  @Size(max = 500, message = "The field secret must be less than 500 characters")
  String secret
) {
}
