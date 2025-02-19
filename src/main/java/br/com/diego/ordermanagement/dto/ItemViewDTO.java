package br.com.diego.ordermanagement.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Builder
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ItemViewDTO {

    private UUID id;

    @Size(max = 200)
    @NotNull
    private String name;

    @NotNull
    private boolean service;

    private List<OrderDTO> orders;

    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "999999999.99")
    @Digits(integer = 9, fraction = 2)
    private BigDecimal price;

    private boolean active;
}
