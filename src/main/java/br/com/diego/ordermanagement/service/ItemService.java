package br.com.diego.ordermanagement.service;

import br.com.diego.ordermanagement.dto.ItemCreateDTO;
import br.com.diego.ordermanagement.dto.ItemUpdateDTO;
import br.com.diego.ordermanagement.dto.ItemViewDTO;
import br.com.diego.ordermanagement.entity.Item;
import br.com.diego.ordermanagement.exceptions.BadRequestException;
import br.com.diego.ordermanagement.respository.ItemRepository;
import br.com.diego.ordermanagement.service.validation.ItemValidationService;
import com.querydsl.core.types.dsl.BooleanExpression;
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
public class ItemService {

    private final ItemRepository repository;

    private final ModelMapper mapper;

    private final OrderService orderService;

    public Item insert(ItemCreateDTO dto) {
        Item item = mapper.map(dto, Item.class);
        ItemValidationService.validateDeactivatedItemWithOrder(item);
        return repository.save(item);
    }

    public List<ItemViewDTO> findAll(BooleanExpression ex) {
        return mapper.map(repository.findAll(ex), getTypeFindAll());
    }

    public Item update(ItemUpdateDTO dto) {
        Item item = mapper.map(dto, Item.class);
        ItemValidationService.validateDeactivatedItemWithOrder(item);
        return repository.save(item);
    }

    public Item findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new BadRequestException("Item not found with id: " + id));
    }

    public void delete(UUID id) {
        ItemValidationService.validateExistsOrderWithItem(orderService, id);
        repository.deleteById(id);
    }

    protected Type getTypeFindAll() {
        return new TypeToken<List<ItemViewDTO>>() {
        }.getType();
    }
}
