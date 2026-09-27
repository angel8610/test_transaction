package com.example.transaction.client.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.encryption")
public record EncryptionProperties(

  String transformation,
  String algorithm,
  String key


) {
}
