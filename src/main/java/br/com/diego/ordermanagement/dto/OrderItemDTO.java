package br.com.diego.ordermanagement.dto;

import br.com.diego.ordermanagement.entity.Item;
import br.com.diego.ordermanagement.entity.Order;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderItemDTO {

    private UUID id;

    private Order order;

    private Item item;
}
