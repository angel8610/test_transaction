package com.example.transaction.client.dtos;

public record LoginResponseDTO(

  String accessToken,
  String tokenType,
  long expiresIn
) {
}
