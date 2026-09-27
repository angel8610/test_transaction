package com.example.transaction.client.dtos;

import lombok.Builder;

@Builder
public record TransactionResponseDTO(

  Long id,
  String status,
  String reference,
  String operation

) {}
