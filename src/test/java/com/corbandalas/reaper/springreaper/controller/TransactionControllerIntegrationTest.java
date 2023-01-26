package com.corbandalas.reaper.springreaper.controller;

import com.corbandalas.reaper.springreaper.dto.DepositTransactionRequest;
import com.corbandalas.reaper.springreaper.dto.WithdrawTransactionRequest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class TransactionControllerIntegrationTest extends BaseControllerTest{


    @Test
    @SneakyThrows
    @Sql("/scripts/insert_transactions.sql")
    @Tag("integration")
    void testDepositTransactionSuccess() {

        DepositTransactionRequest depositTransactionRequest
                = new DepositTransactionRequest(
                        300L, "USD", 100L);

        mockMvc.perform(
                        post("/transaction/deposit")
                                .content(objectMapper.writeValueAsString(depositTransactionRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("transactionDTO.transactionID").isNumber())
                .andExpect(jsonPath("transactionDTO.accountBalanceTo").value("599"));

    }

    @Test
    @SneakyThrows
    @Sql("/scripts/insert_transactions.sql")
    @Tag("integration")
    void testWithdrawSuccess() {

        WithdrawTransactionRequest depositTransactionRequest
                = new WithdrawTransactionRequest(
                50L, "USD", 100L);

        mockMvc.perform(
                        post("/transaction/withdraw")
                                .content(objectMapper.writeValueAsString(depositTransactionRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("transactionDTO.transactionID").isNumber())
                .andExpect(jsonPath("transactionDTO.accountBalanceFrom").value("249"));
    }

    @Test
    @SneakyThrows
    @Sql("/scripts/insert_transactions.sql")
    @Tag("integration")
    void testWithdrawFail() {

        WithdrawTransactionRequest depositTransactionRequest
                = new WithdrawTransactionRequest(
                5000L, "USD", 100L);

        mockMvc.perform(
                        post("/transaction/withdraw")
                                .content(objectMapper.writeValueAsString(depositTransactionRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").isString())
                .andExpect(jsonPath("$.code").value("500"))
                .andExpect(jsonPath("$.message").value("Not enough money"));;
    }


}