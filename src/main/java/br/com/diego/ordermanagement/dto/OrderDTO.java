package br.com.diego.ordermanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderDTO {

    private UUID id;

    private boolean open;

    @JsonIgnore
    private List<ItemDTO> items;
}
