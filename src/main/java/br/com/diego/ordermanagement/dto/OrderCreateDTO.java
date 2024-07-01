package br.com.diego.ordermanagement.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderCreateDTO {

    private UUID id;

    private boolean open;

    private List<ItemDTO> items;
}
