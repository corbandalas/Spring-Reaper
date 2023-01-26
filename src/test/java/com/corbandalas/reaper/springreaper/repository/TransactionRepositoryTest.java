package com.corbandalas.reaper.springreaper.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TransactionRepositoryTest extends BaseRepositoryTest{


    private Long clientID;
    private Long accountFromID;
    private Long accountToID;

    @BeforeEach
    void init() {
        var  client = createClient("Artyom");

        clientID = client.getId();

        accountFromID = createAccount("From Account", clientID).getId();
        accountToID = createAccount("To Account", clientID).getId();

    }


    @Test
    void testCreateTransactionSuccess() {

        var transaction = createTransaction(accountFromID, accountToID, 100L);

        assertThat(transaction).isNotNull();
    }

    @Test
    void testGetTransactionSumByFromAccount() {
        createTransaction(accountFromID, accountToID, 100L);
        createTransaction(accountFromID, accountToID, 170L);

        long transactionSumByAccount = transactionRepository.getTransactionSumByFromAccount(accountRepository.getReferenceById(accountFromID)).orElse(0L);

        System.out.println(transactionSumByAccount);

        assertThat(transactionSumByAccount).isEqualTo(-270);
    }

    @Test
    void testGetTransactionSumByToAccount() {
        createTransaction(accountFromID, accountToID, 100L);
        createTransaction(accountFromID, accountToID, 170L);
        createTransaction(accountFromID, accountToID, 30L);

        long transactionSumByAccount = transactionRepository.getTransactionSumByToAccount(accountRepository.getReferenceById(accountToID)).orElse(0L);

        System.out.println(transactionSumByAccount);

        assertThat(transactionSumByAccount).isEqualTo(300);
    }


}


