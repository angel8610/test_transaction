package com.example.transaction.client.services;

public interface EncryptionService {

  String decrypt(String textEncrypted);

  String encrypt(String textPlain);


}
