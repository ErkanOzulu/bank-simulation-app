package com.bank.service.impl;

import com.bank.entity.Transaction;
import com.bank.enums.AccountType;
import com.bank.exception.AccountOwnershipException;
import com.bank.exception.BadRequestException;
import com.bank.exception.BalanceNotSufficientException;
import com.bank.exception.UnderConstructionException;
import com.bank.dto.AccountDTO;
import com.bank.dto.TransactionDTO;
import com.bank.mapper.TransactionMapper;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import com.bank.service.AccountService;
import com.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionServiceImpl implements TransactionService {
    @Value("${under_construction}")
    private boolean underConstruction;
    private final AccountService accountService;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionServiceImpl(AccountRepository accountRepository, AccountService accountService, TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public TransactionDTO makeTransfer(AccountDTO sender, AccountDTO receiver, BigDecimal amount, Date creationDate, String message) {
        if (!underConstruction) {
        /*
               -if sender or receiver is null ?
               -if sender and receiver is the same account ?
               -if sender has enough balance to make transfer ?
               -if both accounts are checking, if not, one of them saving, it needs to be same userId
         */
            validateAccount(sender, receiver);
            checkAccountOwnership(sender, receiver);
            executeBalanceAndUpdateIfRequired(amount, sender, receiver);
            //makeTransfer
        /*
            after all validations are completed, and money is transferred, we need to create Transaction object and save/return it.
         */

            TransactionDTO transactionDTO = new TransactionDTO(sender, receiver, amount, message, creationDate);
            //save into the db and return it
            transactionRepository.save(transactionMapper.convertToEntity(transactionDTO));
            return transactionDTO;
        } else {
            throw new UnderConstructionException("App is under construction, please try again later ");
        }

    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, AccountDTO sender, AccountDTO receiver) {

        if (checkSenderBalance(sender, amount)) {
            //update sender and receiver balance
            //100-80
            sender.setBalance(sender.getBalance().subtract(amount));
            //50+80
            receiver.setBalance(receiver.getBalance().add(amount));
            /**
             get the dto from the database for both sender and receiver, update balance and save it.
             create accountService updateAccount method and use it for saving.
             */
            //find sender by id
            AccountDTO senderAccount = accountService.retrieveById(sender.getAccountId());
            senderAccount.setBalance(sender.getBalance());
            accountService.updateAccount(senderAccount);


            AccountDTO receiverAccount = accountService.retrieveById(receiver.getAccountId());
            receiverAccount.setBalance(receiver.getBalance());
            accountService.updateAccount(receiverAccount);

        } else {
            throw new BalanceNotSufficientException("Balance not enough for transfer");
        }

    }

    private boolean checkSenderBalance(AccountDTO sender, BigDecimal amount) {

        //verify sender has uneugh balance to send
        return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0;  //-1 - 0 - 1
    }

    private void checkAccountOwnership(AccountDTO sender, AccountDTO receiver) {

            /*
            write an if statement that checks if one of the account is saving,
            and user of sender or receiver is not the same, throw AccountOwnershipException
         */

        if (sender.getAccountType().equals(AccountType.SAVING) || receiver.getAccountType().equals(AccountType.SAVING) && !sender.getUserId().equals(receiver.getUserId())) {
            throw new AccountOwnershipException("If one of the account is saving, user must be the same for sender and receiver");
        }


    }

    private void validateAccount(AccountDTO sender, AccountDTO receiver) {
            /*
            -if any of the account is null
            -if account ids are the same(same account)
            -f the account exist in the database (repository)
         */

        if (sender == null || receiver == null) {
            throw new BadRequestException("Sender or Receiver cannot be null");
        }

        //if account are the same throw BadRequestException with saying accounts needs to be different
        if (sender.getAccountId().equals(receiver.getAccountId())) {
            throw new BadRequestException("Sender account needs to be different than receiver account");
        }

        findAccountById(sender.getAccountId());
        findAccountById(receiver.getAccountId());

    }

    private void findAccountById(Long accountId) {
        accountService.retrieveById(accountId);

    }

    @Override
    public List<TransactionDTO> findAllTransaction() {
        return transactionRepository.findAll().stream().map(transactionMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> last10Transactions() {
        //We want last 10 latest transaction
        //write a query to get result of last transaction

        List<Transaction> last10Transactions = transactionRepository.findLast10Transactions();

        //convert to dto and return it
        return last10Transactions.stream().map(transactionMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findTransactionListById(Long id) {

        //get the list of transactions if account id is involved as a sender or receiver

        List<Transaction> transactionList = transactionRepository.findTransactionListByAccountId(id);
        return transactionList.stream().map(transactionMapper::convertToDTO).collect(Collectors.toList());
    }
}
