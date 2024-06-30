package br.com.diego.ordermanagement.entity;

import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "is_open")
    private boolean open;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private Set<Item> items;
}
