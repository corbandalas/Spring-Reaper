package com.corbandalas.reaper.springreaper.controller;

import com.corbandalas.reaper.springreaper.entities.Account;
import com.corbandalas.reaper.springreaper.entities.Client;
import com.corbandalas.reaper.springreaper.entities.Transaction;
import com.corbandalas.reaper.springreaper.repository.AccountRepository;
import com.corbandalas.reaper.springreaper.repository.ClientRepository;
import com.corbandalas.reaper.springreaper.repository.PostgreSQLExtension;
import com.corbandalas.reaper.springreaper.repository.TransactionRepository;
import com.corbandalas.reaper.springreaper.service.AccountService;
import com.corbandalas.reaper.springreaper.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = "eureka.client.enabled=false",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ExtendWith(PostgreSQLExtension.class)
@AutoConfigureMockMvc
public abstract class BaseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

}
