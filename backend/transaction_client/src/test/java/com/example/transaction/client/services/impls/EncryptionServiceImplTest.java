package com.example.transaction.client.services.impls;

import com.example.transaction.client.configurations.EncryptionProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class EncryptionServiceImplTest {

  private EncryptionServiceImpl encryptionService;

  @BeforeEach
  void setUp() {
    EncryptionProperties encryptionProperties = new EncryptionProperties("AES/GCM/NoPadding", "AES",
      "k7Jf9a8pQ2mZ8vR3tY0eXn5wLp1sD6cH4bA2gF9jK3M=");
    MockitoAnnotations.openMocks(this);
    encryptionService = new EncryptionServiceImpl(encryptionProperties);
  }

  @Test
  void generarSecretoCifrado() {
    String cifrado = encryptionService.encrypt("jejdjw134&3#$$");
    System.out.println("SECRETO CIFRADO: " + cifrado);
  }
  
}