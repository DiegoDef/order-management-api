package br.com.diego.ordermanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id")
    private UUID id;

    @Column(name = "is_open")
    private boolean open;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "mm_order_item",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items;

    @Transient
    private BigDecimal totalPrice;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "999999999.99")
    @Digits(integer = 9, fraction = 2)
    @Column(name = "discount")
    private BigDecimal discount = BigDecimal.ZERO;

    @PostLoad
    @PostPersist
    @PostUpdate
    private void calculateTotalPrice() {
        if (CollectionUtils.isEmpty(items)) {
            return;
        }
        BigDecimal total = items.stream()
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        total = total.subtract(discount);

        this.totalPrice = total;
    }
}
