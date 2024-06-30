package br.com.diego.ordermanagement.dto;

import lombok.*;

import java.util.UUID;


@Builder
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

    private UUID id;

    private boolean service;

    private OrderDTO order;
}
