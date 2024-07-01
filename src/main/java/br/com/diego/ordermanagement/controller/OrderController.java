package br.com.diego.ordermanagement.controller;

import br.com.diego.ordermanagement.dto.EntityIdDTO;
import br.com.diego.ordermanagement.dto.OrderDTO;
import br.com.diego.ordermanagement.dto.OrderViewDTO;
import br.com.diego.ordermanagement.entity.Order;
import br.com.diego.ordermanagement.service.OrderService;
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
@RequestMapping("/order")
@Slf4j
@AllArgsConstructor
public class OrderController {

    private final OrderService service;
    private final ModelMapper mapper;

    @PostMapping
    public ResponseEntity<EntityIdDTO> create(@Valid @RequestBody OrderDTO dto) {
        log.info("Creating order: {}", dto);
        Order order = service.insert(dto);
        return new ResponseEntity<>(mapper.map(order, EntityIdDTO.class), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<EntityIdDTO> update(@Valid @RequestBody OrderDTO dto) {
        log.info("Updating Order: {}", dto);
        Order order = service.update(dto);
        return new ResponseEntity<>(mapper.map(order, EntityIdDTO.class), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderViewDTO> find(@PathVariable UUID id) {
        Order order = service.findById(id);
        return new ResponseEntity<>(mapper.map(order, OrderViewDTO.class), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        log.info("Deleting order with id: {}", id);
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/apply-discount")
    public ResponseEntity<EntityIdDTO> applyDiscount(@PathVariable UUID id) {
        Order order = service.applyDiscount(id);
        return ResponseEntity.ok(mapper.map(order, EntityIdDTO.class));
    }
}
