package com.bank.service.impl;

import com.bank.enums.AccountStatus;
import com.bank.enums.AccountType;
import com.bank.model.Account;
import com.bank.repository.AccountRepository;
import com.bank.service.AccountService;
import org.springframework.stereotype.Component;

import java.lang.reflect.AccessibleObject;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createNewAccount(BigDecimal balance, Date createDate, AccountType accountType, Long userId) {
        //we need to create Account object
        Account account = Account.builder().accountId(UUID.randomUUID()).userId(userId)
                .balance(balance).accountType(accountType).creationDate(createDate).accountStatus(AccountStatus.ACTIVE).build();
        //save into the database(repository)

        //return the object created


        return accountRepository.save(account);

    }

    @Override
    public List<Account> listAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public void deleteAccount(UUID id) {
        //find the account belongs the id
        Account account = accountRepository.findById(id);

        //set status to delete

        account.setAccountStatus(AccountStatus.DELETED);
    }

    @Override
    public void activateAccount(UUID id) {
        //find the account belongs the id
        Account account = accountRepository.findById(id);

        //set status to active

        account.setAccountStatus(AccountStatus.ACTIVE);
    }

    @Override
    public Account retrieveById(UUID id) {
        return accountRepository.findById(id);
    }

}

