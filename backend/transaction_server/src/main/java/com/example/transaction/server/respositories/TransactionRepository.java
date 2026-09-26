package com.example.transaction.server.respositories;

import com.example.transaction.server.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {


}
