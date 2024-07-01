package br.com.diego.ordermanagement.service.validation;

import br.com.diego.ordermanagement.entity.Order;
import jakarta.validation.ValidationException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderValidationService {

    public void validateOrderForDiscount(Order order) {
        if (Boolean.FALSE.equals(order.isOpen())) {
            throw new ValidationException("Cannot apply discount to a closed order");
        }
    }
}
