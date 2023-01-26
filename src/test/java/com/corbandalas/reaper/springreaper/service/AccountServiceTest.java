package com.corbandalas.reaper.springreaper.service;

import com.corbandalas.reaper.springreaper.entities.Account;
import com.corbandalas.reaper.springreaper.repository.PostgreSQLExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@ExtendWith(PostgreSQLExtension.class)
@DirtiesContext
public class AccountServiceTest extends BaseServiceTest{

    private Long accountFromID;
    private Long accountToID;


    @Test
    @Transactional
    void testGetAccountSuccess() {
        Optional<Account> account = accountService.getAccount(accountFromID);

        assertThat(account.isPresent()).isTrue();
        assertThat(account.get().getId()).isEqualTo(accountFromID);
    }

    @Test
    @Transactional
    void testGetAccountFail() {
        Optional<Account> account = accountService.getAccount(1000L);

        assertThat(account.isPresent()).isFalse();
    }


    @Test
    @Transactional
    @Sql("/scripts/insert_transactions.sql")
    void testGetBalance() {

        Long balance = accountService.getBalance(100L);

        assertThat(balance).isEqualTo(299L);
    }



    @BeforeEach
    void setup() {

        var client = createClient("Artyom");

        clientID = client.getId();

        accountFromID =  createAccount("From Account", client.getId()).getId();

         accountToID = createAccount("To Account", clientID).getId();
    }

    @AfterEach
    void after() {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
        clientRepository.deleteAll();
    }

}
