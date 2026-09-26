package com.example.transaction.server.controllers;

import com.example.transaction.server.dtos.SaveTransactionRequestDTO;
import com.example.transaction.server.dtos.SaveTransactionResponseDTO;
import com.example.transaction.server.services.SaveTransactionService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/server/transaction")
public class TransactionController {

  private final SaveTransactionService saveTransactionService;

  public TransactionController(SaveTransactionService saveTransactionService) {
    this.saveTransactionService = saveTransactionService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
           produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SaveTransactionResponseDTO> save(
    @Valid @RequestBody SaveTransactionRequestDTO saveTransactionRequestDTO) {
    return ResponseEntity.ok(this.saveTransactionService.save(saveTransactionRequestDTO));
  }


}
