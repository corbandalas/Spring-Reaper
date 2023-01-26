package com.corbandalas.reaper.springreaper.repository;

import com.corbandalas.reaper.springreaper.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;


public interface AccountRepository extends JpaRepository<Account, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Account findAccountById(Long id);

    @Query(value = "select a from Account a where a.id = :id")
    Account findAccountByIdNoneLocked(Long id);

    @Query(value = "select get_balance(?1)", nativeQuery = true)
    Long getAccountBalance(Long accountId);
}
