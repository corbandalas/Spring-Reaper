package com.corbandalas.reaper.springreaper.dto;

public record TransferTransactionRequest(Long amount, String currency, Long accountFrom, Long accountTo) {
}
