package br.com.diego.ordermanagement.helper;

import br.com.diego.ordermanagement.dto.OrderDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderTestHelper {

    public static OrderDTO createOrderDTO() {
        return OrderDTO.builder()
                .open(true)
                .build();
    }
}
