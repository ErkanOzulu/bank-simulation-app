package com.bank.controller;

import com.bank.enums.AccountType;
import com.bank.model.Account;
import com.bank.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;


@Controller
public class AccountController {
    /*
           write a method to return index.html including account list information
           endpoint:index
        */
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping("/index")
    public String getIndexPage(Model model){

        model.addAttribute("accountList",accountService.listAllAccounts());
        return "account/index";
    }

    @GetMapping("/create-form")
    public String getCreateForm(Model model){

        //we need to provide empty account object
        model.addAttribute("account", Account.builder().build()); //Account.builder().build()-> it provides account object with no args constr

         //we need to provide accountType enum info for filling the dropdown  options

        model.addAttribute("accountTypes", AccountType.values());
        return "account/create-account";
    }

    //create a method to capture information from ui
    //print them on the console.
    //trigger createNewAccount method, create the account based on the user input.
    //once user created return back to the index page.

    @PostMapping("/create")
    public String createAccount(@ModelAttribute("account") Account account){

        System.out.println(account);
        accountService.createNewAccount(account.getBalance(),new Date(),account.getAccountType(),account.getUserId());

        return "redirect:/index";
    }
}
