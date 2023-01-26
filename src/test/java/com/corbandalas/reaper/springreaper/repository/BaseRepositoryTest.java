package com.corbandalas.reaper.springreaper.repository;

import com.corbandalas.reaper.springreaper.entities.Account;
import com.corbandalas.reaper.springreaper.entities.Client;
import com.corbandalas.reaper.springreaper.entities.Transaction;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

//@DataJpaTest
//@TestPropertySource(
//        locations = "classpath:application-test.properties")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

@DataJpaTest
//@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@ExtendWith(PostgreSQLExtension.class)
public abstract class BaseRepositoryTest {

//    @Container
//    static PostgreSQLContainer database = new PostgreSQLContainer("postgres:12")
//            .withDatabaseName("springboot")
//            .withPassword("springboot")
//            .withUsername("springboot");
//
//    @DynamicPropertySource
//    static void setDatasourceProperties(DynamicPropertyRegistry propertyRegistry) {
//        propertyRegistry.add("spring.datasource.url", database::getJdbcUrl);
//        propertyRegistry.add("spring.datasource.password", database::getPassword);
//        propertyRegistry.add("spring.datasource.username", database::getUsername);
//        propertyRegistry.add("spring.test.context.cache.maxSize", () -> "4");
//    }


    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    TransactionRepository transactionRepository;

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
