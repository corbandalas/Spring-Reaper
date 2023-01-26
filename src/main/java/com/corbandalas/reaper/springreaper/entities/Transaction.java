package com.corbandalas.reaper.springreaper.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "transaction")
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private Account from;

    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private Account to;

    @Column(name = "amount", nullable = false, updatable = false)
    private Long amount;

    @Column(name = "currency", nullable = false, updatable = false)
    private String currency;

}
