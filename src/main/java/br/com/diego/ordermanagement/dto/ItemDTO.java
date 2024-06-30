package br.com.diego.ordermanagement.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;


@Builder
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

    private UUID id;

    @NotNull
    private boolean service;

    @NotNull
    private OrderDTO order;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "999999999.99")
    @Digits(integer = 9, fraction = 2)
    private BigDecimal price;
}
