package com.example.transaction.client.services.impls;

import com.example.transaction.client.configurations.EncryptionProperties;
import com.example.transaction.client.services.EncryptionService;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class EncryptionServiceImpl implements EncryptionService {

  private static final int IV_LENGTH = 12;
  private static final int TAG_LENGTH = 128;

  private final SecretKey secretKey;
  private final String transformation;

  private final SecureRandom secureRandom = new SecureRandom();

  public EncryptionServiceImpl(EncryptionProperties properties) {
    this.transformation = properties.transformation();
    byte[] key = Base64.getDecoder().decode(properties.key());
    this.secretKey = new SecretKeySpec(key, properties.algorithm());
  }

  @Override
  public String decrypt(String textEncrypted) {
    try {
      byte[] decoded = Base64.getDecoder().decode(textEncrypted);
      ByteBuffer buffer = ByteBuffer.wrap(decoded);
      byte[] iv = new byte[IV_LENGTH];
      buffer.get(iv);

      byte[] cipherText = new byte[buffer.remaining()];
      buffer.get(cipherText);

      Cipher cipher = Cipher.getInstance(transformation);
      cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(TAG_LENGTH, iv));

      return new String(cipher.doFinal(cipherText), StandardCharsets.UTF_8);
    } catch (GeneralSecurityException e) {
      throw new IllegalStateException("Error decrypting: ", e);
    }
  }

  @Override
  public String encrypt(String textPlain) {
    try {
      byte[] iv = new byte[IV_LENGTH];
      secureRandom.nextBytes(iv);
      Cipher cipher = Cipher.getInstance(transformation);
      GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH, iv);
      cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);

      byte[] cipherText = cipher.doFinal(textPlain.getBytes(StandardCharsets.UTF_8));

      ByteBuffer buffer = ByteBuffer.allocate(iv.length + cipherText.length);
      buffer.put(iv);
      buffer.put(cipherText);

      return Base64.getEncoder().encodeToString(buffer.array());
    } catch (GeneralSecurityException e) {
      throw new IllegalStateException("Error encrypting", e);
    }
  }


}
