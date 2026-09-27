package com.example.transaction.client.services;

import com.example.transaction.client.dtos.LoginRequestDTO;
import com.example.transaction.client.dtos.LoginResponseDTO;

public interface AuthenticationService {

  LoginResponseDTO authenticate(LoginRequestDTO request);


}
