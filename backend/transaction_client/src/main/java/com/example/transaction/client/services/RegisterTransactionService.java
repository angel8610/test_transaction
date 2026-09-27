package com.example.transaction.client.services;

import com.example.transaction.client.dtos.RegisterTransactionRequestDTO;
import com.example.transaction.client.dtos.RegisterTransactionResponseDTO;

public interface RegisterTransactionService {

  RegisterTransactionResponseDTO register(RegisterTransactionRequestDTO registerTransactionRequestDTO);


}
