package br.com.diego.ordermanagement.controller;

import br.com.diego.ordermanagement.dto.EntityIdDTO;
import br.com.diego.ordermanagement.dto.OrderItemDTO;
import br.com.diego.ordermanagement.entity.OrderItem;
import br.com.diego.ordermanagement.service.OrderItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order-items")
@Slf4j
@AllArgsConstructor
public class OrderItemController {

    private final OrderItemService service;
    private final ModelMapper mapper;

    @PostMapping
    public ResponseEntity<EntityIdDTO> create(@RequestBody OrderItemDTO dto) {
        log.info("Creating orderItem: {}", dto);
        OrderItem orderItem = service.insert(dto);
        return new ResponseEntity<>(mapper.map(orderItem, EntityIdDTO.class), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<EntityIdDTO> update(@RequestBody OrderItemDTO dto) {
        log.info("Updating orderItem: {}", dto);
        OrderItem orderItem = service.update(dto);
        return new ResponseEntity<>(mapper.map(orderItem, EntityIdDTO.class), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<OrderItemDTO> find(@PathVariable UUID id) {
        OrderItem orderItem = service.findById(id);
        return new ResponseEntity<>(mapper.map(orderItem, OrderItemDTO.class), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OrderItemDTO>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        log.info("Deleting orderItem with id: {}", id);
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
