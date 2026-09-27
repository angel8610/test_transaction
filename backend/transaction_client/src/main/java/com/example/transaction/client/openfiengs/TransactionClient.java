package com.example.transaction.client.openfiengs;

import com.example.transaction.client.dtos.TransactionRequestDTO;
import com.example.transaction.client.dtos.TransactionResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "transactionClient", url = "${external.api.transaction.url}")
public interface TransactionClient {

  @PostMapping
  TransactionResponseDTO save(TransactionRequestDTO transactionRequestDTO);


}
