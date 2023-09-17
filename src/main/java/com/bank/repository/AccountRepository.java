package com.bank.repository;

import com.bank.exception.BadRequestException;
import com.bank.exception.RecordNotFoundException;
import com.bank.model.Account;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class AccountRepository {

    public static List<Account>accountList=new ArrayList<>();

    public Account save(Account account){
        accountList.add(account);
        return account;
    }

    public List<Account> findAll() {
        return accountList;
    }

    public Account findById(UUID accountId) {
        //TASK
        //complete the  method, that find  the account inside the list, if not throw RecordNotFoundException

           return accountList.stream().filter(account->account.getAccountId().equals(accountId))
                   .findAny().orElseThrow(()->new RecordNotFoundException("account doesn't exist in database"));
        }


    }

