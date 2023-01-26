package com.corbandalas.reaper.springreaper.dto;

import java.time.LocalDateTime;

public record TxDTO(Long transactionID, Long amount, String currency, LocalDateTime localDateTime, Long from, Long to) {
}
