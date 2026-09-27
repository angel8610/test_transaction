package com.example.transaction.client.dtos;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TransactionRequestDTO(

  String operation,

  BigDecimal amount,

  String client,

  String secret
) {
}
