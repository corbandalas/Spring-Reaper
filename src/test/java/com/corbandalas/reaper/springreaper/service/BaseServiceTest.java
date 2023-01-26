package com.corbandalas.reaper.springreaper.service;

import com.corbandalas.reaper.springreaper.entities.Account;
import com.corbandalas.reaper.springreaper.entities.Client;
import com.corbandalas.reaper.springreaper.entities.Transaction;
import com.corbandalas.reaper.springreaper.repository.AccountRepository;
import com.corbandalas.reaper.springreaper.repository.ClientRepository;
import com.corbandalas.reaper.springreaper.repository.PostgreSQLExtension;
import com.corbandalas.reaper.springreaper.repository.TransactionRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@ExtendWith(PostgreSQLExtension.class)
public abstract class BaseServiceTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    TransactionService transactionService;

    @Autowired
    AccountService accountService;

    Long clientID;

    Account createAccount(String name, Long clientID) {
        Account account = new Account();

        var client = clientRepository.getReferenceById(clientID);

        account.setAccountName(name);
        account.setClient(client);

        accountRepository.save(account);

        return account;
    }


    Client createClient(String name) {
        Client client = new Client();
        client.setClientName(name);

        clientRepository.save(client);

        return client;
    }

    Transaction createTransaction(Long accountFromID, Long accountToID, Long amount) {
        Transaction transaction = new Transaction();

        transaction.setAmount(amount);
        transaction.setCurrency("USD");
        if (accountFromID != null)
            transaction.setFrom(accountRepository.getReferenceById(accountFromID));
        if (accountToID != null)
            transaction.setTo(accountRepository.getReferenceById(accountToID));

        return transactionRepository.save(transaction);

    }
}
