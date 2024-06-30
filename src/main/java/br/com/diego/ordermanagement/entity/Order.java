package br.com.diego.ordermanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "is_open")
    private boolean open;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private Set<Item> items;

    @Transient
    private BigDecimal totalPrice;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "999999999.99")
    @Digits(integer = 9, fraction = 2)
    private BigDecimal discount = BigDecimal.ZERO;

    @PostLoad
    @PostPersist
    @PostUpdate
    private void calculateTotalPrice() {
        BigDecimal total = items.stream()
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        total = total.subtract(discount);

        this.totalPrice = total;
    }
}
