package com.example.transaction.client.controllers;

import com.example.transaction.client.dtos.LoginRequestDTO;
import com.example.transaction.client.dtos.LoginResponseDTO;
import com.example.transaction.client.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationService authenticationService;

  public AuthController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
    var loginResponseDTO = authenticationService.authenticate(loginRequestDTO);
    return ResponseEntity.ok(loginResponseDTO);
  }


}
