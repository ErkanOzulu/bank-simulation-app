package com.bank.mapper;

import com.bank.dto.AccountDTO;
import com.bank.entity.Account;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    private final ModelMapper modelMapper;

    public AccountMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public AccountDTO convertToDTO(Account entity){
        return modelMapper.map(entity,AccountDTO.class);
    }
    public Account convertToEntity(AccountDTO dto){
        return modelMapper.map(dto,Account.class);
    }
}
