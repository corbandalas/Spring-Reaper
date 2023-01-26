package com.corbandalas.reaper.springreaper.repository;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgreSQLExtension implements BeforeAllCallback, AfterAllCallback {

    private PostgreSQLContainer<?> postgres;
    private static final String DB_NAME = "springboot";
    private static final String DB_USER = "springboot";
    private static final String DB_PASS = "springboot";

    @Override
    public void beforeAll(ExtensionContext context) {
        postgres = new PostgreSQLContainer<>("postgres:12")
                .withDatabaseName(DB_NAME)
                .withPassword(DB_PASS)
                .withUsername(DB_USER);

        postgres.start();
        String jdbcUrl = String.format("jdbc:postgresql://localhost:%d/" + DB_NAME, postgres.getFirstMappedPort());
        System.setProperty("spring.datasource.url", jdbcUrl);
        System.setProperty("spring.datasource.username", DB_USER);
        System.setProperty("spring.datasource.password", DB_PASS);
    }

    @Override
    public void afterAll(ExtensionContext context) {
        // do nothing, Testcontainers handles container shutdown
    }
}