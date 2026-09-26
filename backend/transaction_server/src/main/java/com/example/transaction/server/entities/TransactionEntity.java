package com.example.transaction.server.entities;

import com.example.transaction.server.enums.TransactionStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "transaction")
public class TransactionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 50)
  private String operation;

  @Column(nullable = false, precision = 15, scale = 2)
  private BigDecimal amount;

  @Column(nullable = false, length = 50)
  private String client;

  @Column(length = 6)
  private String reference;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private TransactionStatusEnum status;

  @Column(nullable = false, length = 250)
  private String secret;

  @PrePersist
  protected void onCreate() {
    if(this.status == null) {
      this.status = TransactionStatusEnum.REGISTERED;
    }
  }


}
