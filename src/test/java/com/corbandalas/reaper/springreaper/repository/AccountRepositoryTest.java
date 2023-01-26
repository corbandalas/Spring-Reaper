package com.corbandalas.reaper.springreaper.repository;

import com.corbandalas.reaper.springreaper.entities.Account;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



class AccountRepositoryTest extends BaseRepositoryTest{



    @BeforeEach
    void init() {

        var client = createClient("Artyom");

        clientID = client.getId();

    }


    @Test
    void testAccountSuccessCreation() {

        var account = createAccount("Test account", clientID);

        Account accountFromDB = accountRepository.getReferenceById(account.getId());

        assertThat(accountFromDB).isEqualTo(account);
    }


    @Test
    @SneakyThrows
    void testGetAccountBalance() {
        var account = createAccount("Test account", clientID);

        var accountBalance = accountRepository.getAccountBalance(account.getId());

        assertThat(accountBalance).isEqualTo(0);

        createTransaction(null, account.getId(), 100L);

        var accountBalanceNew = accountRepository.getAccountBalance(account.getId());

        assertThat(accountBalanceNew).isEqualTo(100L);
    }



}