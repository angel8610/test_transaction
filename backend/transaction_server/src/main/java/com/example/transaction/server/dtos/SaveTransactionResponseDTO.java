package com.example.transaction.server.dtos;

import lombok.Builder;

@Builder
public record SaveTransactionResponseDTO(

  Long id,
  String status,
  String reference,
  String operation

) {}
