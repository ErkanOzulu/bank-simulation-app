package com.bank.service;

import com.bank.enums.AccountType;
import com.bank.dto.AccountDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface AccountService {
AccountDTO createNewAccount(BigDecimal balance, Date createDate, AccountType accountType, Long userId);

List<AccountDTO>listAllAccounts();

    void deleteAccount(UUID id);

    void activateAccount(UUID id);

    AccountDTO retrieveById(UUID id);
}
