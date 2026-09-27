package com.example.transaction.client.dtos;

import java.util.Set;

public record AuthenticatedUserDTO(
  String userId,
  String username,
  Set<String> roles
) {

  public AuthenticatedUserDTO {
    roles = Set.copyOf(roles);
  }


}
