package com.example.transaction.client.services.impls;

import com.example.transaction.client.dtos.RegisterTransactionRequestDTO;
import com.example.transaction.client.dtos.RegisterTransactionResponseDTO;
import com.example.transaction.client.dtos.TransactionRequestDTO;
import com.example.transaction.client.dtos.TransactionResponseDTO;
import com.example.transaction.client.openfiengs.TransactionClient;
import com.example.transaction.client.services.EncryptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("RegisterTransactionServiceImpl Tests")
class RegisterTransactionServiceImplTest {

  private RegisterTransactionServiceImpl registerTransactionService;

  @Mock
  private TransactionClient transactionClient;

  @Mock
  private EncryptionService encryptionService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    registerTransactionService = new RegisterTransactionServiceImpl(transactionClient, encryptionService);
  }

  @Test
  @DisplayName("Should register transaction successfully")
  void shouldRegisterTransactionSuccessfully() {
    RegisterTransactionRequestDTO requestDTO = new RegisterTransactionRequestDTO(
      "encryptedSecret123",
      "$1000.00",
      "John",
      "Purchase"
    );

    when(encryptionService.decrypt("encryptedSecret123")).thenReturn("decryptedSecret");

    TransactionResponseDTO clientResponse = new TransactionResponseDTO(
      1L,
      "APPROVED",
      "123456",
      "Purchase"
    );
    when(transactionClient.save(any(TransactionRequestDTO.class))).thenReturn(clientResponse);

    RegisterTransactionResponseDTO response = registerTransactionService.register(requestDTO);

    assertNotNull(response);
    assertEquals(1L, response.id());
    assertEquals("Purchase", response.operation());
    assertEquals("123456", response.reference());
    assertEquals("APPROVED", response.status());
  }

  /**@Test
  @DisplayName("Should decrypt secret before sending to client")
  void shouldDecryptSecretBeforeSendingToClient() {
    RegisterTransactionRequestDTO requestDTO = new RegisterTransactionRequestDTO(
      "encryptedKey",
      "$500.50",
      "Jane",
      "Transfer"
    );

    when(encryptionService.decrypt("encryptedKey")).thenReturn("mySecret");

    TransactionResponseDTO clientResponse = new TransactionResponseDTO(
      2L,
      "Transfer",
      "654321",
      "APPROVED"
    );
    when(transactionClient.save(any(TransactionRequestDTO.class))).thenReturn(clientResponse);

    registerTransactionService.register(requestDTO);

    verify(encryptionService, times(1)).decrypt("encryptedKey");
  }*/

  @Test
  @DisplayName("Should clean amount format before sending")
  void shouldCleanAmountFormatBeforeSending() {
    RegisterTransactionRequestDTO requestDTO = new RegisterTransactionRequestDTO(
      "encryptedValue",
      "$1,234.56",
      "Bob",
      "Payment"
    );

    when(encryptionService.decrypt("encryptedValue")).thenReturn("secret");

    TransactionResponseDTO clientResponse = new TransactionResponseDTO(
      3L,
      "Payment",
      "999999",
      "APPROVED"
    );
    when(transactionClient.save(any(TransactionRequestDTO.class))).thenReturn(clientResponse);

    registerTransactionService.register(requestDTO);

    verify(transactionClient, times(1)).save(argThat(dto ->
      dto.amount().compareTo(new BigDecimal("1234.56")) == 0
    ));
  }

  @Test
  @DisplayName("Should handle amount with spaces")
  void shouldHandleAmountWithSpaces() {
    RegisterTransactionRequestDTO requestDTO = new RegisterTransactionRequestDTO(
      "encrypted",
      "$ 2000 . 00",
      "Alice",
      "Withdrawal"
    );

    when(encryptionService.decrypt("encrypted")).thenReturn("secret");

    TransactionResponseDTO clientResponse = new TransactionResponseDTO(
      4L,
      "Withdrawal",
      "111111",
      "APPROVED"
    );
    when(transactionClient.save(any(TransactionRequestDTO.class))).thenReturn(clientResponse);

    registerTransactionService.register(requestDTO);

    verify(transactionClient, times(1)).save(argThat(dto ->
      dto.amount().compareTo(new BigDecimal("2000.00")) == 0
    ));
  }

  @Test
  @DisplayName("Should pass client name correctly")
  void shouldPassClientNameCorrectly() {
    RegisterTransactionRequestDTO requestDTO = new RegisterTransactionRequestDTO(
      "encrypted",
      "$100.00",
      "ClientName",
      "Operation"
    );

    when(encryptionService.decrypt("encrypted")).thenReturn("secret");

    TransactionResponseDTO clientResponse = new TransactionResponseDTO(
      5L,
      "Operation",
      "222222",
      "APPROVED"
    );
    when(transactionClient.save(any(TransactionRequestDTO.class))).thenReturn(clientResponse);

    registerTransactionService.register(requestDTO);

    verify(transactionClient, times(1)).save(argThat(dto ->
      "ClientName".equals(dto.client())
    ));
  }

  @Test
  @DisplayName("Should return response with all fields from client response")
  void shouldReturnResponseWithAllFieldsFromClientResponse() {
    RegisterTransactionRequestDTO requestDTO = new RegisterTransactionRequestDTO(
      "encrypted",
      "$750.25",
      "Charlie",
      "Deposit"
    );

    when(encryptionService.decrypt("encrypted")).thenReturn("secret");

    TransactionResponseDTO clientResponse = new TransactionResponseDTO(
      6L,
      "Deposit",
      "333333",
      "APPROVED"
    );
    when(transactionClient.save(any(TransactionRequestDTO.class))).thenReturn(clientResponse);

    RegisterTransactionResponseDTO response = registerTransactionService.register(requestDTO);

    assertNotNull(response.id());
    assertNotNull(response.operation());
    assertNotNull(response.reference());
    assertNotNull(response.status());
  }

  @Test
  @DisplayName("Should map response correctly")
  void shouldMapResponseCorrectly() {
    RegisterTransactionRequestDTO requestDTO = new RegisterTransactionRequestDTO(
      "encrypted",
      "$999.99",
      "David",
      "Refund"
    );

    when(encryptionService.decrypt("encrypted")).thenReturn("secret");

    TransactionResponseDTO clientResponse = new TransactionResponseDTO(
      7L,
      "Refund",
      "444444",
      "APPROVED"
    );
    when(transactionClient.save(any(TransactionRequestDTO.class))).thenReturn(clientResponse);

    RegisterTransactionResponseDTO response = registerTransactionService.register(requestDTO);

    assertEquals(clientResponse.id(), response.id());
    assertEquals(clientResponse.operation(), response.operation());
    assertEquals(clientResponse.reference(), response.reference());
    assertEquals(clientResponse.status(), response.status());
  }

  @Test
  @DisplayName("Should handle negative amount")
  void shouldHandleNegativeAmount() {
    RegisterTransactionRequestDTO requestDTO = new RegisterTransactionRequestDTO(
      "encrypted",
      "-$100.00",
      "Eve",
      "Reversal"
    );

    when(encryptionService.decrypt("encrypted")).thenReturn("secret");

    TransactionResponseDTO clientResponse = new TransactionResponseDTO(
      8L,
      "Reversal",
      "555555",
      "APPROVED"
    );
    when(transactionClient.save(any(TransactionRequestDTO.class))).thenReturn(clientResponse);

    RegisterTransactionResponseDTO response = registerTransactionService.register(requestDTO);

    assertNotNull(response);
  }

  @Test
  @DisplayName("Should handle large amount")
  void shouldHandleLargeAmount() {
    RegisterTransactionRequestDTO requestDTO = new RegisterTransactionRequestDTO(
      "encrypted",
      "$999,999.99",
      "Frank",
      "LargeTransfer"
    );

    when(encryptionService.decrypt("encrypted")).thenReturn("secret");

    TransactionResponseDTO clientResponse = new TransactionResponseDTO(
      9L,
      "LargeTransfer",
      "666666",
      "APPROVED"
    );
    when(transactionClient.save(any(TransactionRequestDTO.class))).thenReturn(clientResponse);

    registerTransactionService.register(requestDTO);

    verify(transactionClient, times(1)).save(argThat(dto ->
      dto.amount().compareTo(new BigDecimal("999999.99")) == 0
    ));
  }
}
