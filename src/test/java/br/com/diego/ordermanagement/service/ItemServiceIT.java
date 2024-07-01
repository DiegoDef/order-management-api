package br.com.diego.ordermanagement.service;

import br.com.diego.ordermanagement.config.TestOrderConfiguration;
import br.com.diego.ordermanagement.dto.ItemCreateDTO;
import br.com.diego.ordermanagement.dto.ItemDTO;
import br.com.diego.ordermanagement.dto.ItemViewDTO;
import br.com.diego.ordermanagement.dto.OrderDTO;
import br.com.diego.ordermanagement.entity.Item;
import br.com.diego.ordermanagement.entity.Order;
import br.com.diego.ordermanagement.exceptions.BadRequestException;
import br.com.diego.ordermanagement.helper.ItemTestHelper;
import br.com.diego.ordermanagement.helper.OrderTestHelper;
import br.com.diego.ordermanagement.respository.ItemRepository;
import br.com.diego.ordermanagement.respository.OrderRepository;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = TestOrderConfiguration.class)
class ItemServiceIT {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper mapper;

    @BeforeEach
    public void setup() {

    }

    @AfterEach
    public void tearDown() {
        itemRepository.deleteAll();
        orderRepository.deleteAll();
    }

    @Test
    @DisplayName("Should create Item with max price")
    void shouldCreateWithMaxPrice() {
        BigDecimal maxPrice = BigDecimal.valueOf(999999999.99);
        ItemCreateDTO toCreate = ItemTestHelper.createItemDTO();
        toCreate.setPrice(maxPrice);

        Item created = itemService.insert(toCreate);

        Assertions.assertNotNull(created.getId());
        assertEquals(maxPrice, created.getPrice());
    }

    @Test
    @DisplayName("Should not create Item with more than max price")
    void shouldNotCreateWithMoreThanMaxPrice() {
        BigDecimal moreThanMaxValue = BigDecimal.valueOf(9999999999999999.99);
        ItemCreateDTO toCreate = ItemTestHelper.createItemDTO();
        toCreate.setPrice(moreThanMaxValue);

        assertThrows(TransactionSystemException.class, () -> itemService.insert(toCreate));
    }

    @Test
    @DisplayName("Should create Item with minimum price")
    void shouldCreateWithMinPrice() {
        BigDecimal minPrice = BigDecimal.valueOf(0.0);
        ItemCreateDTO toCreate = ItemTestHelper.createItemDTO();
        toCreate.setPrice(minPrice);

        Item created = itemService.insert(toCreate);

        Assertions.assertNotNull(created.getId());
        assertEquals(minPrice, created.getPrice());
    }

    @Test
    @DisplayName("Should not create Item with minor than minimum price")
    void shouldNotCreateWithMinorThanMinimumPrice() {
        BigDecimal minorThanMinimumPrice = BigDecimal.valueOf(-1);
        ItemCreateDTO toCreate = ItemTestHelper.createItemDTO();
        toCreate.setPrice(minorThanMinimumPrice);

        assertThrows(TransactionSystemException.class, () -> itemService.insert(toCreate));
    }

    @Test
    @DisplayName("Should create Item")
    void shouldCreateItem() {
        ItemCreateDTO toCreate = ItemTestHelper.createItemDTO();

        Item created = itemService.insert(toCreate);

        Assertions.assertNotNull(created.getId());
        assertEquals(toCreate.getName(), created.getName());
        assertEquals(toCreate.getPrice(), created.getPrice());
        assertEquals(toCreate.isService(), created.isService());
        assertEquals(toCreate.isActive(), created.isActive());
    }

    @Test
    @DisplayName("Should not create deactivated Item with Order")
    void createItemShouldValidateDeactivatedWithOrder() {
        Order order = orderRepository.save(mapper.map(OrderTestHelper.createOrderDTO(), Order.class));
        ItemCreateDTO toCreate = ItemTestHelper.createItemDTO();
        toCreate.setOrders(List.of(mapper.map(order, OrderDTO.class)));
        toCreate.setActive(Boolean.FALSE);

        assertThrows(BadRequestException.class, () -> itemService.insert(toCreate));
    }

    @Test
    @DisplayName("Should not throws when create activated Item with Order")
    void shouldNotThrowsWhenCreateActivatedItemWithOrder() {
        Order order = orderRepository.save(mapper.map(OrderTestHelper.createOrderDTO(), Order.class));
        ItemCreateDTO toCreate = ItemTestHelper.createItemDTO();
        toCreate.setOrders(List.of(mapper.map(order, OrderDTO.class)));
        toCreate.setActive(Boolean.TRUE);

        assertDoesNotThrow(() -> itemService.insert(toCreate));
    }

    @Test
    @Transactional
    @DisplayName("Should create Item with Order")
    void shouldCreateItemWithOrder() {
        Order order = orderRepository.save(mapper.map(OrderTestHelper.createOrderDTO(), Order.class));
        ItemCreateDTO toCreate = ItemTestHelper.createItemDTO();
        toCreate.setOrders(List.of(mapper.map(order, OrderDTO.class)));

        Item created = itemService.insert(toCreate);

        Assertions.assertNotNull(created.getId());
        assertTrue(toCreate.getOrders().stream().findFirst().isPresent());
        assertEquals(toCreate.getOrders().stream().findFirst().get().getId(), created.getOrders().stream().findFirst().get().getId());
        assertEquals(toCreate.getName(), created.getName());
        assertEquals(toCreate.getPrice(), created.getPrice());
        assertEquals(toCreate.isService(), created.isService());
        assertEquals(toCreate.isActive(), created.isActive());
    }

    @Test
    @DisplayName("Should update Item")
    void shouldUpdateItem() {
        ItemCreateDTO toCreate = ItemTestHelper.createItemDTO();
        Item created = itemService.insert(toCreate);
        ItemDTO toUpdate = ItemTestHelper.createItemToUpdate();
        created.setId(toUpdate.getId());

        Item updated = itemService.update(toUpdate);

        Assertions.assertNotNull(updated.getId());
        assertEquals(toUpdate.getName(), updated.getName());
        assertEquals(toUpdate.getPrice(), updated.getPrice());
        assertEquals(toUpdate.isService(), updated.isService());
        assertEquals(toUpdate.isActive(), updated.isActive());
    }

    @Test
    @DisplayName("Should find Item by id")
    void shouldFindById() {
        ItemCreateDTO toCreate = ItemTestHelper.createItemDTO();
        Item created = itemService.insert(toCreate);

        Item founded = itemService.findById(created.getId());

        Assertions.assertNotNull(founded.getId());
        assertEquals(toCreate.getName(), founded.getName());
        assertEquals(toCreate.getPrice().setScale(2, RoundingMode.DOWN), founded.getPrice().setScale(2, RoundingMode.DOWN));
        assertEquals(toCreate.isService(), founded.isService());
        assertEquals(toCreate.isActive(), founded.isActive());
    }

    @Test
    @Transactional
    @DisplayName("Should find Item by id with Order")
    void shouldFindByIdWithOrder() {
        Order order = orderRepository.save(mapper.map(OrderTestHelper.createOrderDTO(), Order.class));
        ItemCreateDTO toCreate = ItemTestHelper.createItemDTO();
        toCreate.setOrders(List.of(mapper.map(order, OrderDTO.class)));

        Item created = itemService.insert(toCreate);
        Item founded = itemService.findById(created.getId());

        Assertions.assertNotNull(founded.getId());
        assertEquals(toCreate.getName(), founded.getName());
        assertEquals(toCreate.getPrice().setScale(2, RoundingMode.DOWN), founded.getPrice().setScale(2, RoundingMode.DOWN));
        assertEquals(toCreate.isService(), founded.isService());
        assertTrue(toCreate.getOrders().stream().findFirst().isPresent());
        assertEquals(toCreate.getOrders().stream().findFirst().get().getId(), founded.getOrders().stream().findFirst().get().getId());
        assertEquals(toCreate.isActive(), founded.isActive());
    }

    @Test
    @Transactional
    @DisplayName("Should find all created Items")
    void shouldFindAll() {
        ItemCreateDTO toCreate = ItemTestHelper.createItemDTO();

        for (int i = 0; i < 12; i++) {
            itemService.insert(toCreate);
        }

        List<ItemViewDTO> allItems = itemService.findAll();

        assertEquals(12, allItems.size(), "The number of items found should be 12");
    }

    @Test
    @DisplayName("Should delete Item")
    void shouldDeleteItem() {
        ItemCreateDTO toCreate = ItemTestHelper.createItemDTO();

        Item created = itemService.insert(toCreate);
        Item itemToDelete = itemService.findById(created.getId());

        assertNotNull(itemToDelete);

        itemService.delete(created.getId());

        boolean contains = false;
        try {
            itemService.findById(created.getId());
        } catch (BadRequestException e) {
            contains = true;
        }
        assertTrue(contains);
    }

    @Test
    @DisplayName("Should throws when delete Item with Order")
    void shouldThrowsDeleteItemWithOrder() {
        Order order = orderRepository.save(mapper.map(OrderTestHelper.createOrderDTO(), Order.class));
        ItemCreateDTO toCreate = ItemTestHelper.createItemDTO();
        toCreate.setOrders(List.of(mapper.map(order, OrderDTO.class)));

        Item created = itemService.insert(toCreate);

        UUID idToDelete = itemService.findById(created.getId()).getId();

        assertNotNull(idToDelete);
        assertThrows(BadRequestException.class, () -> itemService.delete(idToDelete));
    }

    @Test
    @DisplayName("Should not throws when delete Item without Order")
    void shouldNotThrowsDeleteItemWithoutOrder() {
        ItemCreateDTO toCreate = ItemTestHelper.createItemDTO();

        Item created = itemService.insert(toCreate);
        Item itemToDelete = itemService.findById(created.getId());

        assertNotNull(itemToDelete);

        assertDoesNotThrow(() -> itemService.delete(created.getId()));
    }
}
