package com.bank.repository;

import com.bank.dto.TransactionDTO;
import com.bank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    @Query(value = "Select * from transactions ORDER BY create_date desc Limit 10", nativeQuery = true)
    List<Transaction> findLast10Transactions();

    @Query("select t from Transaction t WHERE  t.sender.accountId=?1 Or t.receiver.accountId=?1")
    List<Transaction> findTransactionListByAccountId(Long id);
}
