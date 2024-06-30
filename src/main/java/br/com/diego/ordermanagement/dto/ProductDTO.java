package br.com.diego.ordermanagement.dto;

import br.com.diego.ordermanagement.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;


@Builder
@Getter
@ToString
public class ProductDTO {

    private UUID id;

    private boolean isService;

    public Product toEntity() {
        return Product.builder()
                .id(id)
                .isService(isService)
                .build();
    }
}
