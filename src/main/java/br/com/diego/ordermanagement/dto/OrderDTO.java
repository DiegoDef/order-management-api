package br.com.diego.ordermanagement.dto;

import br.com.diego.ordermanagement.entity.Item;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class OrderDTO {

    private UUID id;

    private boolean open;

    private Set<Item> items;
}
