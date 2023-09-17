package com.bank.service.impl;

import com.bank.enums.AccountType;
import com.bank.exception.AccountOwnershipException;
import com.bank.exception.BadRequestException;
import com.bank.exception.BalanceNotSufficientException;
import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import com.bank.service.TransactionService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionServiceImp implements TransactionService {
   private final AccountRepository accountRepository;
   private final TransactionRepository transactionRepository;

    public TransactionServiceImp(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date creationDate, String message) {
        /*
               -if sender or receiver is null ?
               -if sender and receiver is the same account ?
               -if sender has enough balance to make transfer ?
               -if both accounts are checking, if not, one of them saving, it needs to be same userId
         */
        validateAccount(sender, receiver);
        checkAccountOwnership(sender,receiver);
        executeBalanceAndUpdateIfRequired(amount,sender,receiver);
        //makeTransfer
        /*
            after all validations are completed, and money is transferred, we need to create Transaction object and save/return it.
         */
        Transaction transaction=Transaction.builder().amount(amount).receiver(receiver.getAccountId()).createDate(creationDate).message(message).build();
        //save into the db and return it
        return transactionRepository.save(transaction);



    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, Account sender, Account receiver) {

        if (checkSenderBalance(sender, amount)) {
            //update sender and receiver balance
            //100-80
            sender.setBalance(sender.getBalance().subtract(amount));
            //50+80
            receiver.setBalance(receiver.getBalance().add(amount));
        }else{
            throw new BalanceNotSufficientException("Balance not enough for transfer");
        }

    }

    private boolean checkSenderBalance(Account sender, BigDecimal amount) {

        //verify sender has uneugh balance to send
        return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO)>=0;
    }

    private void checkAccountOwnership(Account sender, Account receiver) {
             if (sender.getAccountType().equals(AccountType.SAVING)||receiver.getAccountType().equals(AccountType.SAVING)&& !sender.getUserId().equals(receiver.getUserId())){
                 throw new AccountOwnershipException("If one of the account is saving, user must be the same for sender and receiver");
             }

            /*
            write an if statement that checks if one of the account is saving,
            and user of sender or receiver is not the same, throw AccountOwnershipException
         */
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

        //if account are the same throw BadRequestException with saying accounts needs to be different
        if(sender.getAccountId().equals(receiver.getAccountId())){
            throw new BadRequestException("Sender account needs to be different than receiver account");
        }

        findAccountById(sender.getAccountId());
        findAccountById(receiver.getAccountId());

    }

    private void findAccountById(UUID accountId) {
       accountRepository.findById(accountId);

    }

    @Override
    public List<Transaction> findAllTransaction() {
        return null;
    }
}
