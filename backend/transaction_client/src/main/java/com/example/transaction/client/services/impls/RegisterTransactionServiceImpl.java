package com.example.transaction.client.services.impls;

import com.example.transaction.client.dtos.RegisterTransactionRequestDTO;
import com.example.transaction.client.dtos.RegisterTransactionResponseDTO;
import com.example.transaction.client.dtos.TransactionRequestDTO;
import com.example.transaction.client.dtos.TransactionResponseDTO;
import com.example.transaction.client.openfiengs.TransactionClient;
import com.example.transaction.client.services.EncryptionService;
import com.example.transaction.client.services.RegisterTransactionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RegisterTransactionServiceImpl implements RegisterTransactionService {

  private final TransactionClient transactionClient;

  private final EncryptionService encryptionService;

  public RegisterTransactionServiceImpl(TransactionClient transactionClient,
                                        EncryptionService encryptionService) {
    this.transactionClient = transactionClient;
    this.encryptionService = encryptionService;
  }

  @Override
  public RegisterTransactionResponseDTO register(
    RegisterTransactionRequestDTO registerTransactionRequestDTO) {
    String secretDecrypted = this.encryptionService.decrypt(
      registerTransactionRequestDTO.secret());

    // Por convencion se utiliza diferentes objectos para la peticion a la API externa
    // puede ser que en el cliente se agregue campos adicionales

    // Se puede usar mapper por tiempos se opta por el patron Builder con Lombok
    var transactionRequestDTO = TransactionRequestDTO.builder()
      .amount(new BigDecimal(registerTransactionRequestDTO.amount()
        .replace("$", "").replace(" ", "")
        .replace(",", "")))// Por tiempos, se limpia de esta forma,
      // se puede hacer un metodo en una utilería
      .client(registerTransactionRequestDTO.client())
      .secret(secretDecrypted)
      .operation(registerTransactionRequestDTO.operation())
      .build();

    TransactionResponseDTO transactionResponseDTO = this.transactionClient.save(
      transactionRequestDTO);

    // Se puede usar mapper por tiempos se opta por el patron Builder con Lombok
    return RegisterTransactionResponseDTO.builder()
      .id(transactionResponseDTO.id())
      .operation(transactionResponseDTO.operation())
      .reference(transactionResponseDTO.reference())
      .status(transactionResponseDTO.status())
      .build();
  }


}
