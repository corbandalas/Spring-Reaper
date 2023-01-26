package com.corbandalas.reaper.springreaper.controller;

import com.corbandalas.reaper.springreaper.dto.*;
import com.corbandalas.reaper.springreaper.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<TxResponse> createDeposit(@RequestBody DepositTransactionRequest transactionRequest) {
        return ResponseEntity.ok(new TxResponse(transactionService.createDepositTransaction(transactionRequest), "0", "Success"));
    }


    @PostMapping("/withdraw")
    public ResponseEntity<TxResponse> createWithdraw(@RequestBody WithdrawTransactionRequest transactionRequest) {
        return ResponseEntity.ok(new TxResponse(transactionService.createWithdrawTransaction(transactionRequest), "0", "Success"));
    }

    @PostMapping("/transfer")
    public ResponseEntity<TxResponse> createTransfer(@RequestBody TransferTransactionRequest transactionRequest) {
        return ResponseEntity.ok(new TxResponse(transactionService.createTransferTransaction(transactionRequest), "0", "Success"));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TxDTO>> getTxListByAccount(@PathVariable Long accountId) {
        return ResponseEntity.ok(transactionService.getTransactionListByAccount(accountId));
    }
}
