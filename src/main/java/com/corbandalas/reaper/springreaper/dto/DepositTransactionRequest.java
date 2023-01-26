package com.corbandalas.reaper.springreaper.dto;

public record DepositTransactionRequest(Long amount, String currency, Long accountTo) {
}
