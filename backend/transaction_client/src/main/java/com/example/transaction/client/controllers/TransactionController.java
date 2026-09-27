package com.example.transaction.client.controllers;

import com.example.transaction.client.dtos.RegisterTransactionRequestDTO;
import com.example.transaction.client.dtos.RegisterTransactionResponseDTO;
import com.example.transaction.client.services.RegisterTransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {

  private final RegisterTransactionService registerTransactionService;

  public TransactionController(RegisterTransactionService registerTransactionService) {
    this.registerTransactionService = registerTransactionService;
  }

  @PostMapping
  public ResponseEntity<RegisterTransactionResponseDTO> register(
    @Valid @RequestBody RegisterTransactionRequestDTO registerTransactionRequestDTO
    ) {
    return ResponseEntity.ok(this.registerTransactionService.register(
      registerTransactionRequestDTO));
  }


}
