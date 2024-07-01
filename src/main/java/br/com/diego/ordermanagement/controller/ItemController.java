package br.com.diego.ordermanagement.controller;

import br.com.diego.ordermanagement.dto.EntityIdDTO;
import br.com.diego.ordermanagement.dto.ItemCreateDTO;
import br.com.diego.ordermanagement.dto.ItemDTO;
import br.com.diego.ordermanagement.entity.Item;
import br.com.diego.ordermanagement.service.ItemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/item")
@Slf4j
@AllArgsConstructor
public class ItemController {

    private final ItemService service;
    private final ModelMapper mapper;

    @PostMapping
    public ResponseEntity<EntityIdDTO> create(@Valid @RequestBody ItemCreateDTO dto) {
        log.info("Creating item: {}", dto);
        Item item = service.insert(dto);
        return new ResponseEntity<>(mapper.map(item, EntityIdDTO.class), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<EntityIdDTO> update(@Valid @RequestBody ItemDTO dto) {
        log.info("Updating item: {}", dto);
        Item item = service.update(dto);
        return new ResponseEntity<>(mapper.map(item, EntityIdDTO.class), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> find(@PathVariable UUID id) {
        Item item = service.findById(id);
        return new ResponseEntity<>(mapper.map(item, ItemDTO.class), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        log.info("Deleting item with id: {}", id);
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
