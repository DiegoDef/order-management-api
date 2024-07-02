package br.com.diego.ordermanagement.helper;

import br.com.diego.ordermanagement.dto.ItemCreateDTO;
import br.com.diego.ordermanagement.dto.ItemUpdateDTO;
import br.com.diego.ordermanagement.entity.Item;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class ItemTestHelper {

    public static ItemCreateDTO createItemDTO() {
        return ItemCreateDTO.builder()
                .name("Item 1")
                .price(BigDecimal.TEN)
                .service(true)
                .active(true)
                .build();
    }

    public static Item createItem() {
        Item item = new Item();
        item.setName("Item 1");
        item.setPrice(BigDecimal.TEN);
        item.setService(true);
        item.setActive(true);
        return item;
    }

    public static ItemUpdateDTO createItemToUpdate() {
        return ItemUpdateDTO.builder()
                .name("Item 2")
                .price(BigDecimal.ZERO)
                .service(false)
                .active(false)
                .build();
    }
}
