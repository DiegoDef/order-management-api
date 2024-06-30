package br.com.diego.ordermanagement.service;

import br.com.diego.ordermanagement.dto.OrderDTO;
import br.com.diego.ordermanagement.entity.Order;
import br.com.diego.ordermanagement.respository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderService {

    private final OrderRepository repository;

    private final ModelMapper mapper;

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

    protected Type getTypeFindAll() {
        return new TypeToken<List<OrderDTO>>() {
        }.getType();
    }
}
