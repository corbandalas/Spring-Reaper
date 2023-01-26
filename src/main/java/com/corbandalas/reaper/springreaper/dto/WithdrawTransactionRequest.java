package com.corbandalas.reaper.springreaper.dto;

public record WithdrawTransactionRequest(Long amount, String currency, Long accountFrom) {
}
