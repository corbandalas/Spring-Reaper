package com.corbandalas.reaper.springreaper.service;

import com.corbandalas.reaper.springreaper.dto.*;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
//@TestPropertySource(
//        locations = "classpath:application-test.properties")
@ExtendWith(PostgreSQLExtension.class)
@DirtiesContext
class TransactionServiceTest extends BaseServiceTest{

    private long accountFromID;
    private long accountToID;

    @BeforeEach
    void setup() {

        var client = createClient("Artyom");

        clientID = client.getId();

        accountFromID =  createAccount("From Account", client.getId()).getId();

        accountToID = createAccount("To Account", clientID).getId();
    }

    @Test
    @Transactional
    void testGetAccountBalance() {

        Long accountBalance = accountRepository.getAccountBalance(accountFromID);

        assertThat(accountBalance).isEqualTo(0L);
    }

    @Test
    @Transactional
    void testCreateDepositTransaction() {

        var depositTransaction1 = transactionService.createDepositTransaction(new DepositTransactionRequest(100L, "USD", accountToID));

        assertThat(depositTransaction1).isNotNull();

        assertThat(depositTransaction1.transactionID()).isGreaterThan(0);

        Long accountBalance = accountRepository.getAccountBalance(accountToID);

        assertThat(accountBalance).isEqualTo(100L);

        var depositTransaction2 = transactionService.createDepositTransaction(new DepositTransactionRequest(300L, "USD", accountToID));

        assertThat(depositTransaction2).isNotNull();

        assertThat(depositTransaction2.transactionID()).isGreaterThan(0);

        accountBalance = accountRepository.getAccountBalance(accountToID);

        assertThat(accountBalance).isEqualTo(400L);

    }


    @Test
    @Transactional
    void testCreateWithdrawTransactionFailedNotEnoughMoney() {

        assertThrows(IllegalArgumentException.class, () -> {
            transactionService.createWithdrawTransaction(new WithdrawTransactionRequest(100L, "USD", accountFromID));
        });
    }


    @Test
    @Transactional
    void testCreateWithdrawTransactionSuccess() {

        var depositTransaction1 = transactionService.createDepositTransaction(new DepositTransactionRequest(100L, "USD", accountToID));

        var withdrawTransaction = transactionService.createWithdrawTransaction(new WithdrawTransactionRequest(37L, "USD", accountToID));

        assertThat(withdrawTransaction).isNotNull();

        Long accountBalance = accountRepository.getAccountBalance(accountToID);

        assertThat(accountBalance).isEqualTo(63L);
    }

    @Test
    @Transactional
    void testTransferTransaction() {
        var depositTransaction1 = transactionService.createDepositTransaction(new DepositTransactionRequest(100L, "USD", accountFromID));

        Long accountBalance = accountRepository.getAccountBalance(accountFromID);

        assertThat(accountBalance).isEqualTo(100L);

        accountBalance = accountRepository.getAccountBalance(accountToID);

        assertThat(accountBalance).isEqualTo(0L);


        transactionService.createTransferTransaction(new TransferTransactionRequest(33L, "USD", accountFromID, accountToID));

         accountBalance = accountRepository.getAccountBalance(accountFromID);

        assertThat(accountBalance).isEqualTo(67L);

        accountBalance = accountRepository.getAccountBalance(accountToID);

        assertThat(accountBalance).isEqualTo(33L);
    }

    @Test
    @Transactional
    void testTransferTransactionFailed() {
        var depositTransaction1 = transactionService.createDepositTransaction(new DepositTransactionRequest(100L, "USD", accountFromID));

        Long accountBalance = accountRepository.getAccountBalance(accountFromID);

        assertThat(accountBalance).isEqualTo(100L);

        accountBalance = accountRepository.getAccountBalance(accountToID);

        assertThat(accountBalance).isEqualTo(0L);


        assertThrows(Exception.class, () -> transactionService.createTransferTransaction(new TransferTransactionRequest(150L, "USD", accountFromID, accountToID)));







        accountBalance = accountRepository.getAccountBalance(accountFromID);

        assertThat(accountBalance).isEqualTo(100L);

        accountBalance = accountRepository.getAccountBalance(accountToID);

        assertThat(accountBalance).isEqualTo(0L);
    }


    @Test
    void testParallelTransactionsSuccess() {
        transactionService.createDepositTransaction(new DepositTransactionRequest(100L, "USD", accountToID));

        List<CompletableFuture<TransactionDTO>> futures = new ArrayList<>();


        for (int i = 0; i < 10; ++i) {

            CompletableFuture<TransactionDTO> future = CompletableFuture.supplyAsync(() ->
                    transactionService.createWithdrawTransaction(new WithdrawTransactionRequest(10L, "USD", accountToID)));

            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        Long accountBalance = accountRepository.getAccountBalance(accountToID);

        assertThat(accountBalance).isEqualTo(0L);

    }

    @Test
    @Transactional
    void testParallelTransactionsFail() {
        transactionService.createDepositTransaction(new DepositTransactionRequest(100L, "USD", accountToID));

        List<CompletableFuture<TransactionDTO>> futures = new ArrayList<>();


        assertThrows(Exception.class, () -> {
            for (int i = 0; i < 20; ++i) {
                CompletableFuture<TransactionDTO> future = CompletableFuture.supplyAsync(() ->
                        transactionService.createWithdrawTransaction(new WithdrawTransactionRequest(10L, "USD", accountToID)));

                futures.add(future);
            }

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        });

        Long accountBalance = accountRepository.getAccountBalance(accountToID);
        assertThat(accountBalance).isGreaterThanOrEqualTo(0L);
    }


    @Test
    @Transactional
    @Sql("/scripts/insert_transactions.sql")
    void testTransactionHistoryList() {
        List<TxDTO> transactionListByAccount = transactionService.getTransactionListByAccount(100L);

        assertThat(transactionListByAccount).isNotNull();
        assertThat(transactionListByAccount.size()).isEqualTo(3);
    }

    @AfterEach
    void after() {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
        clientRepository.deleteAll();
    }

}