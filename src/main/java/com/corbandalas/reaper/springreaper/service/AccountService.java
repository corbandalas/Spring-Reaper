package com.corbandalas.reaper.springreaper.service;


import com.corbandalas.reaper.springreaper.entities.Account;
import com.corbandalas.reaper.springreaper.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public Optional<Account> getAccount(Long accountID) {
        Objects.requireNonNull(accountID);
        return Optional.ofNullable(accountRepository.findAccountById(accountID));
    }

    @Transactional(readOnly = true)
    public Optional<Account> getAccountNoneLocked(Long accountID) {
        Objects.requireNonNull(accountID);
        return Optional.ofNullable(accountRepository.findAccountByIdNoneLocked(accountID));
    }

    @Transactional(readOnly = true)
    public Long getBalance(Long accountID) {
        return accountRepository.getAccountBalance(accountID);
    }

}
