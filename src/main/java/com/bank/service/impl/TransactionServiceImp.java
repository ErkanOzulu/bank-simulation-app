package com.bank.service.impl;

import com.bank.exception.BadRequestException;
import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.service.TransactionService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component
public class TransactionServiceImp implements TransactionService {
    @Override
    public Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date creationDate, String message) {
        /*
               -if sender or receiver is null ?
               -if sender and receiver is the same account ?
               -if sender has enough balance to make transfer ?
               -if both accounts are checking, if not, one of them saving, it needs to be same userId
         */
        validateAccount(sender, receiver);

        //makeTransfer
        return null;
    }

    private void validateAccount(Account sender, Account receiver) {
            /*
            -if any of the account is null
            -if account ids are the same(same account)
            -f the account exist in the database (repository)
         */

        if (sender==null || receiver==null) {
            throw new BadRequestException("Sender or Receiver cannot be null");
        }
    }

    @Override
    public List<Transaction> findAllTransaction() {
        return null;
    }
}
