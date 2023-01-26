package com.corbandalas.reaper.springreaper.service;

import com.corbandalas.reaper.springreaper.SelfAutowired;
import com.corbandalas.reaper.springreaper.dto.DepositTransactionRequest;
import com.corbandalas.reaper.springreaper.dto.TransactionDTO;
import com.corbandalas.reaper.springreaper.dto.TransferTransactionRequest;
import com.corbandalas.reaper.springreaper.dto.WithdrawTransactionRequest;
import com.corbandalas.reaper.springreaper.entities.Transaction;
import com.corbandalas.reaper.springreaper.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;


@Service
public class TransactionService {

    @Autowired
    private  TransactionRepository transactionRepository;
    @Autowired
    private  AccountService accountService;

    @SelfAutowired
    private TransactionService selfService;


    @Transactional
    public TransactionDTO createDepositTransaction(DepositTransactionRequest transactionRequest) {
        checkTransactionRequest(transactionRequest.amount(), transactionRequest.currency());

        var accountTo = accountService.getAccountNoneLocked(transactionRequest.accountTo()).orElseThrow();


        Transaction transaction = new Transaction();

        transaction.setAmount(transactionRequest.amount());
        transaction.setCurrency(transactionRequest.currency());
        transaction.setFrom(null);
        transaction.setTo(accountTo);

        var savedTransaction = transactionRepository.save(transaction);

        return new TransactionDTO(savedTransaction.getId(), savedTransaction.getAmount(), savedTransaction.getCurrency(),
                LocalDateTime.now(), null, accountTo.getId(), null, accountService.getBalance(transactionRequest.accountTo()));

    }


    @Transactional
    public TransactionDTO createWithdrawTransaction(WithdrawTransactionRequest transactionRequest) {
        checkTransactionRequest(transactionRequest.amount(), transactionRequest.currency());

        var accountFrom = accountService.getAccount(transactionRequest.accountFrom()).orElseThrow();

        var accountBalance = accountService.getBalance(transactionRequest.accountFrom());

        if (accountBalance - transactionRequest.amount() < 0) {
            throw new IllegalArgumentException("Not enough money");
        }

        Transaction transaction = new Transaction();

        transaction.setAmount(transactionRequest.amount());
        transaction.setCurrency(transactionRequest.currency());
        transaction.setFrom(accountFrom);
        transaction.setTo(null);

        var savedTransaction = transactionRepository.save(transaction);

        return new TransactionDTO(savedTransaction.getId(), savedTransaction.getAmount(), savedTransaction.getCurrency(),
                LocalDateTime.now(), accountFrom.getId(), null, accountService.getBalance(transactionRequest.accountFrom()), null);

    }

    @Transactional
    public TransactionDTO createTransferTransaction(TransferTransactionRequest transactionRequest) {

        var savedWithdraw = selfService.createWithdrawTransaction(new WithdrawTransactionRequest(transactionRequest.amount(), transactionRequest.currency(), transactionRequest.accountFrom()));
        var savedDeposit = selfService.createDepositTransaction(new DepositTransactionRequest(transactionRequest.amount(), transactionRequest.currency(), transactionRequest.accountTo()));

        return new TransactionDTO(savedDeposit.transactionID(), transactionRequest.amount(), transactionRequest.currency(),
                LocalDateTime.now(), transactionRequest.accountFrom(), transactionRequest.accountTo(), accountService.getBalance(transactionRequest.accountFrom()), accountService.getBalance(transactionRequest.accountTo()));


    }

    private void checkTransactionRequest(Long transactionRequest, String transactionRequest1) {
        if (transactionRequest <= 0) {
            throw new IllegalArgumentException("Transaction amount is negative");
        }

        if (!StringUtils.hasText(transactionRequest1)) {
            throw new IllegalArgumentException("Transaction amount is negative");
        }
    }

}
