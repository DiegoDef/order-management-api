package br.com.diego.ordermanagement.dto;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class OrderDTO {

    private UUID id;

    private boolean open;

    private Set<ItemDTO> items;
}
