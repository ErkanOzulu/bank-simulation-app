package com.bank.service.impl;

import com.bank.entity.Account;
import com.bank.enums.AccountStatus;
import com.bank.dto.AccountDTO;
import com.bank.mapper.AccountMapper;
import com.bank.repository.AccountRepository;
import com.bank.service.AccountService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public void createNewAccount(AccountDTO accountDTO) {

        accountDTO.setCreationDate(new Date());
        accountDTO.setAccountStatus(AccountStatus.ACTIVE);
        //We need to create Account object

        //save into the database(repository)

        //return the object created


        accountRepository.save(accountMapper.convertToEntity(accountDTO));

    }

    @Override
    public List<AccountDTO> listAllAccounts() {
        //We are getting list of account but we need to return list of AccountDTO
        List<Account> accountList = accountRepository.findAll();
    //We are converting entity to dto list nd return it
        return accountList.stream().map(accountMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        //find the account belongs the id
        Account account = accountRepository.findById(id).get();

        //set status to delete
        account.setAccountStatus(AccountStatus.DELETED);
        accountRepository.save(account);
    }

    @Override
    public void activateAccount(Long id) {
        //find the account belongs the id
        Account account = accountRepository.findById(id).get();

        //set status to active
        account.setAccountStatus(AccountStatus.ACTIVE);

        //save the updated object
        accountRepository.save(account);
    }

    @Override
    public AccountDTO retrieveById(Long id) {
        // find the account entity based on id, then convert it dto and return it
        return accountMapper.convertToDTO(accountRepository.findById(id).get());
    }

    @Override
    public List<AccountDTO> listAllActiveAccount() {

        //we need list of active account from repository
        List<Account> accountList = accountRepository.findAllByAccountStatus(AccountStatus.ACTIVE);
        //convert active accounts to accountsDto and return it
        return accountList.stream().map(accountMapper::convertToDTO).collect(Collectors.toList());

       //---second way---
        //        return accountRepository.findAll().stream()
        //                .filter(account -> account.getAccountStatus()
        //                        .equals(AccountStatus.ACTIVE))
        //                .map(accountMapper::convertToDTO).collect(Collectors.toList());
        //    }
    }

}

