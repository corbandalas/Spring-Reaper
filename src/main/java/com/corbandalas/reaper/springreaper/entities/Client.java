package com.corbandalas.reaper.springreaper.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "client")
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "client_name")
    private String clientName;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Account> accounts = new java.util.ArrayList<>();

}
