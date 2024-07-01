package br.com.diego.ordermanagement.validation;

import br.com.diego.ordermanagement.entity.Item;
import br.com.diego.ordermanagement.entity.Order;
import br.com.diego.ordermanagement.helper.ItemTestHelper;
import br.com.diego.ordermanagement.service.OrderService;
import br.com.diego.ordermanagement.service.validation.ItemValidationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ItemValidationServiceUnitTest {

    @Mock
    private OrderService orderService;

    @Test
    @DisplayName("Should throws when exists order with Item")
    void shouldValidateExistsOrderWithItemThrows() {
        UUID itemId = UUID.randomUUID();
        when(orderService.existsByItemId(itemId)).thenReturn(Boolean.TRUE);

        Assertions.assertThrows(IllegalArgumentException.class, () -> ItemValidationService.validateExistsOrderWithItem(orderService, itemId));
    }

    @Test
    @DisplayName("Should does not throws when not exists order with Item")
    void shouldNotThrowsWhenNotExistsOrderWithItem() {
        UUID itemId = UUID.randomUUID();
        when(orderService.existsByItemId(itemId)).thenReturn(Boolean.FALSE);

        Assertions.assertDoesNotThrow(() -> ItemValidationService.validateExistsOrderWithItem(orderService, itemId));
    }

    @Test
    @DisplayName("Should throws when Item is deactivated and exists order")
    void shouldThrowsValidateDeactivatedItemWithOrder() {
        Item item = ItemTestHelper.createItem();
        item.setActive(Boolean.FALSE);
        item.setOrders(List.of(new Order()));

        Assertions.assertThrows(IllegalArgumentException.class, (() -> ItemValidationService.validateDeactivatedItemWithOrder(item)));
    }

    @Test
    @DisplayName("Should does not throws when Item is active and exists order")
    void shouldNotThrowsValidateActivatedItemWithOrder() {
        Item item = ItemTestHelper.createItem();
        item.setActive(Boolean.TRUE);
        item.setOrders(List.of(new Order()));

        Assertions.assertDoesNotThrow(() -> ItemValidationService.validateDeactivatedItemWithOrder(item));
    }

    @Test
    @DisplayName("Should does not throws when Item is not active and not exists order")
    void shouldThrowsValidateDeactivatedItemWithoutOrder() {
        Item item = ItemTestHelper.createItem();
        item.setActive(Boolean.FALSE);

        Assertions.assertDoesNotThrow(() -> ItemValidationService.validateDeactivatedItemWithOrder(item));
    }
}
