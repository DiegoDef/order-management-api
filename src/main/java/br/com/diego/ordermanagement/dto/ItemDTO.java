package br.com.diego.ordermanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ItemDTO {

    private UUID id;

    @Size(max = 200)
    @NotEmpty
    private String name;

    @NotNull
    private boolean service;

    @JsonIgnore
    private List<OrderDTO> orders;

    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "999999999.99")
    @Digits(integer = 9, fraction = 2)
    private BigDecimal price = BigDecimal.ZERO;

    private boolean active;
}
