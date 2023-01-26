package com.corbandalas.reaper.springreaper.repository;

import com.corbandalas.reaper.springreaper.entities.Account;
import com.corbandalas.reaper.springreaper.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    @Query("SELECT -sum(t.amount) from Transaction t where t.from = :fromAccount")
    Optional<Long> getTransactionSumByFromAccount(Account fromAccount);

    @Query("SELECT sum(t.amount) from Transaction t where t.to = :toAccount")
    Optional<Long> getTransactionSumByToAccount(Account toAccount);

}
