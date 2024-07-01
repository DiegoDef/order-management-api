package br.com.diego.ordermanagement.service;

import br.com.diego.ordermanagement.dto.OrderDTO;
import br.com.diego.ordermanagement.dto.OrderViewDTO;
import br.com.diego.ordermanagement.entity.Item;
import br.com.diego.ordermanagement.entity.Order;
import br.com.diego.ordermanagement.respository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderService {

    private final OrderRepository repository;

    private final ModelMapper mapper;

    private final BigDecimal fixedDiscount = BigDecimal.valueOf(0.2);

    public Order insert(OrderDTO dto) {
        return repository.save(mapper.map(dto, Order.class));
    }

    public List<OrderDTO> findAll() {
        return mapper.map(repository.findAll(), getTypeFindAll());
    }

    public Order update(OrderDTO dto) {
        return repository.save(mapper.map(dto, Order.class));
    }

    public Order findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public Order applyDiscount(UUID id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));

        order.setDiscount(calculateTotalDiscount(order));

        return repository.save(order);
    }

    private BigDecimal calculateTotalDiscount(Order order) {
        if (CollectionUtils.isEmpty(order.getItems())) {
            return BigDecimal.ZERO;
        }
        BigDecimal totalPrice = order.getItems().stream()
                .filter(item -> !item.isService())
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalPrice.multiply(fixedDiscount).setScale(2, RoundingMode.DOWN);
    }

    protected Type getTypeFindAll() {
        return new TypeToken<List<OrderViewDTO>>() {
        }.getType();
    }
}
