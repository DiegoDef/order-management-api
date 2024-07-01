package br.com.diego.ordermanagement.helper;

import br.com.diego.ordermanagement.dto.OrderCreateDTO;
import br.com.diego.ordermanagement.dto.OrderDTO;
import br.com.diego.ordermanagement.entity.Order;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderTestHelper {

    public static OrderDTO createOrderDTO() {
        return OrderDTO.builder()
                .open(true)
                .build();
    }

    public static OrderCreateDTO orderCreateDTO() {
        return OrderCreateDTO.builder()
                .open(true)
                .build();
    }

    public static Order createOrder() {
        Order order = new Order();
        order.setOpen(true);
        return order;
    }
}
