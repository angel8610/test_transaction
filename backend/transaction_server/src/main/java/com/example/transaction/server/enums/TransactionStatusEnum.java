package com.example.transaction.server.enums;

import lombok.Getter;

@Getter
public enum TransactionStatusEnum {

  REGISTERED("Registrada"),
  APPROVED("Aprobada"),
  REJECTED("Rechazada"),
  FAILED("Fallida");

  private final String value;

  TransactionStatusEnum(String value) {
    this.value = value;
  }


}
