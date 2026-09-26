package com.example.transaction.server.services.impls;

import com.example.transaction.server.dtos.SaveTransactionRequestDTO;
import com.example.transaction.server.dtos.SaveTransactionResponseDTO;
import com.example.transaction.server.entities.TransactionEntity;
import com.example.transaction.server.enums.TransactionStatusEnum;
import com.example.transaction.server.mappers.TransactionMapper;
import com.example.transaction.server.respositories.TransactionRepository;
import com.example.transaction.server.services.SaveTransactionService;
import com.example.transaction.server.utils.ReferenceGeneratorUtil;
import org.springframework.stereotype.Service;

@Service
public class SaveTransactionServiceImpl implements SaveTransactionService {

  private final TransactionRepository transactionRepository;

  private final TransactionMapper transactionMapper;

  public SaveTransactionServiceImpl(TransactionRepository transactionRepository,
                                    TransactionMapper transactionMapper) {
    this.transactionRepository = transactionRepository;
    this.transactionMapper = transactionMapper;
  }

  @Override
  public SaveTransactionResponseDTO save(SaveTransactionRequestDTO saveTransactionRequestDTO) {
    TransactionEntity transaction = this.transactionMapper
      .saveTransactionRequestToEntity(saveTransactionRequestDTO);
    TransactionEntity transactionSaved = transactionRepository.save(transaction);
    /**
     * En español: Dice que una vez que se guarda, la referencia, lo que se comprende que va a un
     * servicio de pago o algo similar, si sale bien se guarda la referencia con el status en aprobada,
     * no está tomando en cuenta cuando ocurre un error.
     *
     */

    String reference = ReferenceGeneratorUtil.generateReferenceString(6);
    transactionSaved.setReference(reference);
    transactionSaved.setStatus(TransactionStatusEnum.APPROVED);
    this.transactionRepository.save(transactionSaved);

    return SaveTransactionResponseDTO.builder()
      .id(transactionSaved.getId())
      .status(transactionSaved.getStatus().getValue())
      .reference(transactionSaved.getReference())
      .operation(transactionSaved.getOperation())
      .build();
  }


}
