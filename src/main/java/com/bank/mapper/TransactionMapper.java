package com.bank.mapper;

import com.bank.dto.TransactionDTO;
import com.bank.entity.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    private final ModelMapper modelMapper;

    public TransactionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public TransactionDTO convertToDTO(Transaction entity){
        return modelMapper.map(entity,TransactionDTO.class);
    }


    public Transaction convertToEntity(TransactionDTO dto){
        return modelMapper.map(dto,Transaction.class);
    }
}
