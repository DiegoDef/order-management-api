package br.com.diego.ordermanagement.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class OrderViewDTO {

    private UUID id;

    private boolean open;

    private List<ItemDTO> items;

    private BigDecimal totalPrice;

    private BigDecimal discount = BigDecimal.ZERO;
}
