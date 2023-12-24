package com.bank.repository;

import com.bank.entity.Account;
import com.bank.exception.RecordNotFoundException;
import com.bank.dto.AccountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

//    public static List<AccountDTO> accountDTOList =new ArrayList<>();
//
//    public AccountDTO save(AccountDTO accountDTO){
//        accountDTOList.add(accountDTO);
//        return accountDTO;
//    }
//
//    public List<AccountDTO> findAll() {
//        return accountDTOList;
//    }
//
//    public AccountDTO findById(Long accountId) {
//        //TASK
//        //complete the  method, that find  the account inside the list, if not throw RecordNotFoundException
//
//           return accountDTOList.stream().filter(account->account.getAccountId().equals(accountId))
//                   .findAny().orElseThrow(()->new RecordNotFoundException("account doesn't exist in database"));
//        }


    }

