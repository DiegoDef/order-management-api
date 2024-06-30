package br.com.diego.ordermanagement.dto;

import br.com.diego.ordermanagement.entity.Item;
import br.com.diego.ordermanagement.entity.Order;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderItemDTO {

    private UUID id;

    @NotNull
    private Order order;

    @NotNull
    private Item item;
}
