package br.com.diego.ordermanagement.service.validation;

import br.com.diego.ordermanagement.entity.Item;
import br.com.diego.ordermanagement.service.OrderService;
import lombok.experimental.UtilityClass;
import org.springframework.util.CollectionUtils;

import java.util.UUID;

@UtilityClass
public class ItemValidationService {

    public void validateExistsOrderWithItem(OrderService orderService, UUID id) {
        if (orderService.existsByItemId(id)) {
            throw new IllegalArgumentException(String.format("Item with id %s exists in some order", id));
        }
    }

    public void validateDeactivatedItemWithOrder(Item item) {
        if (Boolean.FALSE.equals(item.isActive()) && !CollectionUtils.isEmpty(item.getOrders())) {
            throw new IllegalArgumentException(String.format("Adding deactivated item with id %s in order is not allowed", item.getId()));
        }
    }
}
