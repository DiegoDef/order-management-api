package br.com.diego.ordermanagement.helper;

import br.com.diego.ordermanagement.dto.ItemDTO;
import br.com.diego.ordermanagement.dto.OrderDTO;
import br.com.diego.ordermanagement.dto.OrderItemDTO;
import br.com.diego.ordermanagement.entity.Item;
import br.com.diego.ordermanagement.entity.Order;
import br.com.diego.ordermanagement.entity.OrderItem;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderItemTestHelper {

    public static OrderItemDTO createOrderItemDTO(ItemDTO item, OrderDTO order) {
        return OrderItemDTO.builder()
                .item(item)
                .order(order)
                .build();
    }

    public static OrderItem createOrderItem(Item item, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setItem(item);
        return orderItem;
    }
}
