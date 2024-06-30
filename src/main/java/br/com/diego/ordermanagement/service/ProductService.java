package br.com.diego.ordermanagement.service;

import br.com.diego.ordermanagement.dto.ProductDTO;
import br.com.diego.ordermanagement.entity.Product;
import br.com.diego.ordermanagement.respository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.UUID;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductService {

    private final ProductRepository repository;

    public Product insert(ProductDTO productDTO) {
        return repository.save(productDTO.toEntity());
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product update(ProductDTO productDTO) {
        return repository.save(productDTO.toEntity());
    }

    public Product findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
