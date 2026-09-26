package com.example.transaction.server.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record SaveTransactionRequestDTO(

  @NotBlank
  String operation,

  @NotNull
  BigDecimal amount,

  @NotBlank
  String client,

  @NotBlank
  String secret
) {
}
