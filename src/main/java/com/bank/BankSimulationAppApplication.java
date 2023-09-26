package com.bank;

import com.bank.enums.AccountType;
import com.bank.model.Account;
import com.bank.service.AccountService;
import com.bank.service.TransactionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootApplication
public class BankSimulationAppApplication {

    public static void main(String[] args) {

        ApplicationContext container = SpringApplication.run(BankSimulationAppApplication.class, args);

//        //get account and transactionService beans
//        AccountService accountService = container.getBean(AccountService.class);
//        TransactionService transactionService = container.getBean(TransactionService.class);
//
//        //create 2 accounts sender and receiver
//        Account sender = accountService.createNewAccount(BigDecimal.valueOf(70), new Date(), AccountType.CHECKING, 1L);
//        Account receiver = accountService.createNewAccount(BigDecimal.valueOf(50), new Date(), AccountType.SAVING, 1L);
//        Account receiver2 = null;
//
//        accountService.listAllAccounts().forEach(System.out::println);
//        transactionService.makeTransfer(sender,receiver,new BigDecimal(40),new Date(),"Transaction1");
//        System.out.println(transactionService.findAllTransaction().get(0));
//        accountService.listAllAccounts().forEach(System.out::println);



    }

}
