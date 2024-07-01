package br.com.diego.ordermanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "item")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "item_id")
    private UUID id;

    @Size(max = 200)
    @NotEmpty
    @Column(name = "name")
    private String name;

    @Column(name = "is_service")
    private boolean service;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "mm_order_item",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<Order> orders;

    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "999999999.99")
    @Digits(integer = 9, fraction = 2)
    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "active")
    private boolean active;
}
