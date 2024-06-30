package br.com.diego.ordermanagement.service;

import br.com.diego.ordermanagement.dto.OrderItemsDTO;
import br.com.diego.ordermanagement.entity.OrderItem;
import br.com.diego.ordermanagement.respository.OrderItemsRepository;
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
public class OrderItemsService {

    private final OrderItemsRepository repository;

    private final ModelMapper mapper;

    public OrderItem insert(OrderItemsDTO dto) {
        return repository.save(mapper.map(dto, OrderItem.class));
    }

    public List<OrderItemsDTO> findAll() {
        return mapper.map(repository.findAll(), getTypeFindAll());
    }

    public OrderItem update(OrderItemsDTO dto) {
        return repository.save(mapper.map(dto, OrderItem.class));
    }

    public OrderItem findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("OrderItems not found with id: " + id));
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    protected Type getTypeFindAll() {
        return new TypeToken<List<OrderItemsDTO>>() {
        }.getType();
    }
}
