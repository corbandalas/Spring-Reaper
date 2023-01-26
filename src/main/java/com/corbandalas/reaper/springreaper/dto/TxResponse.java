package com.corbandalas.reaper.springreaper.dto;

public record TxResponse(TransactionDTO transactionDTO, String code, String message) {
}
