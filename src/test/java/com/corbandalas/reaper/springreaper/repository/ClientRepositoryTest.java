package com.corbandalas.reaper.springreaper.repository;

import com.corbandalas.reaper.springreaper.entities.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClientRepositoryTest extends BaseRepositoryTest{

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void testClientCreationWrongConstraints() {

        assertThrows(DataIntegrityViolationException.class, () -> {
            Client client = new Client();
            clientRepository.save(client);
        });

    }

    @Test
    void testClientCreationSuccess() {

        var saveClient = createClient("Artyom");

        assertThat(saveClient).isNotNull();

    }

}