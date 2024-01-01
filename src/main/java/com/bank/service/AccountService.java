package com.bank.service;

import com.bank.enums.AccountType;
import com.bank.dto.AccountDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AccountService {
    void createNewAccount(AccountDTO accountDTO);

    List<AccountDTO> listAllAccounts();

    void deleteAccount(Long id);

    void activateAccount(Long id);

    AccountDTO retrieveById(Long id);

    List<AccountDTO> listAllActiveAccount();
}
