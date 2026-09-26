package com.example.transaction.server.services.impls;

import com.example.transaction.server.dtos.SaveTransactionRequestDTO;
import com.example.transaction.server.dtos.SaveTransactionResponseDTO;
import com.example.transaction.server.entities.TransactionEntity;
import com.example.transaction.server.enums.TransactionStatusEnum;
import com.example.transaction.server.mappers.TransactionMapper;
import com.example.transaction.server.respositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("SaveTransactionServiceImpl Tests")
class SaveTransactionServiceImplTest {

  private SaveTransactionServiceImpl saveTransactionService;

  @Mock
  private TransactionRepository transactionRepository;

  @Mock
  private TransactionMapper transactionMapper;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    saveTransactionService = new SaveTransactionServiceImpl(transactionRepository, transactionMapper);
  }

  @Test
  @DisplayName("Should save transaction and return response with approved status")
  void shouldSaveTransactionAndReturnResponseWithApprovedStatus() {
    SaveTransactionRequestDTO requestDTO = new SaveTransactionRequestDTO(
      "Purchase",
      new BigDecimal("100.00"),
      "John",
      "secretKey123"
    );

    TransactionEntity entity = new TransactionEntity();
    entity.setId(1L);
    entity.setOperation("Purchase");
    entity.setAmount(new BigDecimal("100.00"));
    entity.setClient("John");
    entity.setSecret("secretKey123");

    TransactionEntity savedEntity = new TransactionEntity();
    savedEntity.setId(1L);
    savedEntity.setOperation("Purchase");
    savedEntity.setAmount(new BigDecimal("100.00"));
    savedEntity.setClient("John");
    savedEntity.setSecret("secretKey123");
    savedEntity.setReference("123456");
    savedEntity.setStatus(TransactionStatusEnum.APPROVED);

    when(transactionMapper.saveTransactionRequestToEntity(requestDTO)).thenReturn(entity);
    when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(savedEntity);

    SaveTransactionResponseDTO response = saveTransactionService.save(requestDTO);

    assertNotNull(response);
    assertEquals(1L, response.id());
    assertNotNull(response.reference());
    assertEquals(6, response.reference().length());
    assertEquals(TransactionStatusEnum.APPROVED.getValue(), response.status());
    assertEquals("Purchase", response.operation());
  }

  @Test
  @DisplayName("Should save transaction twice")
  void shouldSaveTransactionTwice() {
    SaveTransactionRequestDTO requestDTO = new SaveTransactionRequestDTO(
      "Transfer",
      new BigDecimal("250.50"),
      "Jane",
      "secretKey456"
    );

    TransactionEntity entity = new TransactionEntity();
    entity.setId(2L);
    entity.setOperation("Transfer");
    entity.setAmount(new BigDecimal("250.50"));
    entity.setClient("Jane");
    entity.setSecret("secretKey456");

    TransactionEntity savedEntity = new TransactionEntity();
    savedEntity.setId(2L);
    savedEntity.setOperation("Transfer");
    savedEntity.setAmount(new BigDecimal("250.50"));
    savedEntity.setClient("Jane");
    savedEntity.setSecret("secretKey456");
    savedEntity.setReference("654321");
    savedEntity.setStatus(TransactionStatusEnum.APPROVED);

    when(transactionMapper.saveTransactionRequestToEntity(requestDTO)).thenReturn(entity);
    when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(savedEntity);

    SaveTransactionResponseDTO response = saveTransactionService.save(requestDTO);

    assertNotNull(response);
    verify(transactionRepository, times(2)).save(any(TransactionEntity.class));
  }

  @Test
  @DisplayName("Should set reference on saved transaction")
  void shouldSetReferenceOnSavedTransaction() {
    SaveTransactionRequestDTO requestDTO = new SaveTransactionRequestDTO(
      "Withdrawal",
      new BigDecimal("500.00"),
      "Bob",
      "secretKey789"
    );

    TransactionEntity entity = new TransactionEntity();
    entity.setId(3L);
    entity.setOperation("Withdrawal");

    TransactionEntity savedEntity = new TransactionEntity();
    savedEntity.setId(3L);
    savedEntity.setOperation("Withdrawal");
    savedEntity.setReference("999999");
    savedEntity.setStatus(TransactionStatusEnum.APPROVED);

    when(transactionMapper.saveTransactionRequestToEntity(requestDTO)).thenReturn(entity);
    when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(savedEntity);

    SaveTransactionResponseDTO response = saveTransactionService.save(requestDTO);

    assertNotNull(response.reference());
    assertEquals(6, response.reference().length());
    assertTrue(response.reference().matches("\\d+"));
  }

  @Test
  @DisplayName("Should return transaction with all required fields")
  void shouldReturnTransactionWithAllRequiredFields() {
    SaveTransactionRequestDTO requestDTO = new SaveTransactionRequestDTO(
      "Deposit",
      new BigDecimal("1000.00"),
      "Alice",
      "secretKeyABC"
    );

    TransactionEntity entity = new TransactionEntity();
    entity.setId(4L);
    entity.setOperation("Deposit");
    entity.setAmount(new BigDecimal("1000.00"));
    entity.setClient("Alice");
    entity.setSecret("secretKeyABC");

    TransactionEntity savedEntity = new TransactionEntity();
    savedEntity.setId(4L);
    savedEntity.setOperation("Deposit");
    savedEntity.setAmount(new BigDecimal("1000.00"));
    savedEntity.setClient("Alice");
    savedEntity.setSecret("secretKeyABC");
    savedEntity.setReference("111111");
    savedEntity.setStatus(TransactionStatusEnum.APPROVED);

    when(transactionMapper.saveTransactionRequestToEntity(requestDTO)).thenReturn(entity);
    when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(savedEntity);

    SaveTransactionResponseDTO response = saveTransactionService.save(requestDTO);

    assertNotNull(response);
    assertNotNull(response.id());
    assertNotNull(response.operation());
    assertNotNull(response.reference());
    assertNotNull(response.status());
  }

  @Test
  @DisplayName("Should generate 6 digit reference")
  void shouldGenerate6DigitReference() {
    SaveTransactionRequestDTO requestDTO = new SaveTransactionRequestDTO(
      "Payment",
      new BigDecimal("75.25"),
      "Charlie",
      "secretKeyXYZ"
    );

    TransactionEntity entity = new TransactionEntity();
    entity.setId(5L);
    entity.setOperation("Payment");

    TransactionEntity savedEntity = new TransactionEntity();
    savedEntity.setId(5L);
    savedEntity.setOperation("Payment");
    savedEntity.setReference("555555");
    savedEntity.setStatus(TransactionStatusEnum.APPROVED);

    when(transactionMapper.saveTransactionRequestToEntity(requestDTO)).thenReturn(entity);
    when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(savedEntity);

    SaveTransactionResponseDTO response = saveTransactionService.save(requestDTO);

    assertEquals(6, response.reference().length());
  }

  @Test
  @DisplayName("Should always return approved status")
  void shouldAlwaysReturnApprovedStatus() {
    SaveTransactionRequestDTO requestDTO = new SaveTransactionRequestDTO(
      "Operation",
      new BigDecimal("50.00"),
      "David",
      "secretKeyDEF"
    );

    TransactionEntity entity = new TransactionEntity();
    entity.setId(6L);

    TransactionEntity savedEntity = new TransactionEntity();
    savedEntity.setId(6L);
    savedEntity.setReference("777777");
    savedEntity.setStatus(TransactionStatusEnum.APPROVED);

    when(transactionMapper.saveTransactionRequestToEntity(requestDTO)).thenReturn(entity);
    when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(savedEntity);

    SaveTransactionResponseDTO response = saveTransactionService.save(requestDTO);

    assertEquals(TransactionStatusEnum.APPROVED.getValue(), response.status());
  }
}

