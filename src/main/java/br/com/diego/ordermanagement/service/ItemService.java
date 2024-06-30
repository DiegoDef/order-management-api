package br.com.diego.ordermanagement.service;

import br.com.diego.ordermanagement.dto.ItemDTO;
import br.com.diego.ordermanagement.entity.Item;
import br.com.diego.ordermanagement.respository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.UUID;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ItemService {

    private final ItemRepository repository;

    private final ModelMapper mapper;

    public Item insert(ItemDTO dto) {
        return repository.save(mapper.map(dto, Item.class));
    }

    public List<ItemDTO> findAll() {
        return mapper.map(repository.findAll(), getTypeFindAll());
    }

    public Item update(ItemDTO dto) {
        return repository.save(mapper.map(dto, Item.class));
    }

    public Item findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found with id: " + id));
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    protected Type getTypeFindAll() {
        return new TypeToken<List<ItemDTO>>(){}.getType();
    }
}
