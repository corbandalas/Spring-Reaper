package com.corbandalas.reaper.springreaper.repository;

import com.corbandalas.reaper.springreaper.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
