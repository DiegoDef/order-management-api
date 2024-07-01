package br.com.diego.ordermanagement.validation;

import br.com.diego.ordermanagement.entity.Order;
import br.com.diego.ordermanagement.helper.OrderTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderValidationServiceUnitTest {

    @Test
    @DisplayName("Should throws when Order is not open")
    void shouldThrowsWhenNotOpen() {
        Order order = OrderTestHelper.createOrder();
        order.setOpen(Boolean.FALSE);

        Assertions.assertThrows(IllegalStateException.class, () -> OrderValidationService.validateOrderForDiscount(order));
    }

    @Test
    @DisplayName("Should not throws when Order is open")
    void shouldNotThrowsWhenOpen() {
        Order order = OrderTestHelper.createOrder();
        order.setOpen(Boolean.TRUE);

        Assertions.assertDoesNotThrow(() -> OrderValidationService.validateOrderForDiscount(order));
    }
}
