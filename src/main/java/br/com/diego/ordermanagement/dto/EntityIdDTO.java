package br.com.diego.ordermanagement.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class EntityIdDTO {

    private UUID id;

    public static EntityIdDTO of(UUID id) {
        return EntityIdDTO.builder().id(id).build();
    }
}
