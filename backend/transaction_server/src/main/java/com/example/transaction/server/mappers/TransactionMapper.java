package com.example.transaction.server.mappers;

import com.example.transaction.server.dtos.SaveTransactionRequestDTO;
import com.example.transaction.server.entities.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TransactionMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "reference", ignore = true)
  @Mapping(target = "status", ignore = true)
  TransactionEntity saveTransactionRequestToEntity(SaveTransactionRequestDTO saveTransactionRequestDTO);


}
