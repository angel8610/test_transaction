package com.example.transaction.server.services;

import com.example.transaction.server.dtos.SaveTransactionRequestDTO;
import com.example.transaction.server.dtos.SaveTransactionResponseDTO;

public interface SaveTransactionService {

  SaveTransactionResponseDTO save(SaveTransactionRequestDTO saveTransactionRequestDTO);


}
