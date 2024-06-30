package br.com.diego.ordermanagement.controller;

import br.com.diego.ordermanagement.dto.EntityIdDTO;
import br.com.diego.ordermanagement.dto.ProductDTO;
import br.com.diego.ordermanagement.entity.Product;
import br.com.diego.ordermanagement.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
@Slf4j
@AllArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<EntityIdDTO> create(@RequestBody ProductDTO productDTO) {
        log.info("Creating product: {}", productDTO);
        Product product = service.insert(productDTO);
        return new ResponseEntity<>(EntityIdDTO.of(product.getId()), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<EntityIdDTO> update(@RequestBody ProductDTO productDTO) {
        log.info("Creating product: {}", productDTO);
        Product product = service.update(productDTO);
        return new ResponseEntity<>(EntityIdDTO.of(product.getId()), HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<EntityIdDTO> find(@PathVariable UUID id) {
        Product product = service.findById(id);
        return new ResponseEntity<>(EntityIdDTO.of(product.getId()), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        log.info("Deleting product with id: {}", id);
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
