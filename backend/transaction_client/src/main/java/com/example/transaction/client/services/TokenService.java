package com.example.transaction.client.services;

import com.example.transaction.client.dtos.AuthenticatedUserDTO;

public interface TokenService {

  String generateToken(AuthenticatedUserDTO user);


}
