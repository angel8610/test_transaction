package com.example.transaction.client.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(

  @NotBlank
  @Size(max = 50)
  String username,

  @NotBlank
  @Size(max = 60)
  String password

) {
}
