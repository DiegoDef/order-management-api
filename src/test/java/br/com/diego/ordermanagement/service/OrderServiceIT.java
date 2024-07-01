package br.com.diego.ordermanagement.service;

import br.com.diego.ordermanagement.config.TestOrderConfiguration;
import br.com.diego.ordermanagement.dto.ItemDTO;
import br.com.diego.ordermanagement.dto.OrderCreateDTO;
import br.com.diego.ordermanagement.dto.OrderDTO;
import br.com.diego.ordermanagement.entity.Item;
import br.com.diego.ordermanagement.entity.Order;
import br.com.diego.ordermanagement.helper.ItemTestHelper;
import br.com.diego.ordermanagement.helper.OrderTestHelper;
import br.com.diego.ordermanagement.respository.ItemRepository;
import br.com.diego.ordermanagement.respository.OrderRepository;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = TestOrderConfiguration.class)
class OrderServiceIT {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper mapper;

    private Order order;

    @BeforeEach
    public void setup() {
        Item item = ItemTestHelper.createItem();
        item.setService(true);
        Item createdItem = itemRepository.save(item);
        OrderCreateDTO toCreate = OrderTestHelper.orderCreateDTO();
        toCreate.setItems(List.of(mapper.map(createdItem, ItemDTO.class)));
        order = orderService.insert(toCreate);
    }

    @AfterEach
    public void tearDown() {
        itemRepository.deleteAll();
        orderRepository.deleteAll();
    }

    @Test
    @Transactional
    @DisplayName("Should apply discount for Order only on items are not service")
    void shouldApplyDiscount() {
        order.setItems(new ArrayList<>());
        for (int i = 0; i < 12; i++) {
            Item toCreate = ItemTestHelper.createItem();
            toCreate.setService(false);
            toCreate.setOrders(List.of(order));
            order.getItems().add(toCreate);
            itemRepository.save(toCreate);

            Item service = ItemTestHelper.createItem();
            service.setService(true);
            service.setOrders(List.of(order));
            order.getItems().add(service);
            itemRepository.save(service);
        }

        orderRepository.save(order);

        orderService.applyDiscount(order.getId());
        Order discountApplied = orderRepository.findById(order.getId()).get();

        Assertions.assertNotNull(discountApplied.getId());
        assertEquals(BigDecimal.valueOf(24.00).setScale(2, RoundingMode.DOWN), discountApplied.getDiscount().setScale(2, RoundingMode.DOWN));
    }

    @Test
    @Transactional
    @DisplayName("Should not apply discount for Order when items are service")
    void shouldNotApplyDiscountPriceWhenItemIsService() {
        order.setItems(new ArrayList<>());
        for (int i = 0; i < 12; i++) {
            Item toCreate = ItemTestHelper.createItem();
            toCreate.setService(true);
            toCreate.setOrders(List.of(order));
            order.getItems().add(toCreate);
            itemRepository.save(toCreate);
        }

        orderRepository.save(order);

        orderService.applyDiscount(order.getId());
        Order discountApplied = orderRepository.findById(order.getId()).get();

        Assertions.assertNotNull(discountApplied.getId());
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.DOWN), discountApplied.getDiscount().setScale(2, RoundingMode.DOWN));
    }

    @Test
    @DisplayName("Should create Order")
    void shouldCreateOrder() {
        Item item = ItemTestHelper.createItem();
        item.setService(true);
        Item createdItem = itemRepository.save(item);
        OrderCreateDTO toCreate = OrderTestHelper.orderCreateDTO();
        toCreate.setItems(List.of(mapper.map(createdItem, ItemDTO.class)));

        Order created = orderService.insert(toCreate);

        Assertions.assertNotNull(created.getId());
        assertEquals(toCreate.isOpen(), created.isOpen());
        assertEquals(toCreate.getItems().stream().findFirst().get().getId(), created.getItems().stream().findFirst().get().getId());
    }


    @Test
    @DisplayName("Should update Order")
    void shouldUpdateOrder() {
        order.setOpen(false);
        orderRepository.save(order);

        OrderDTO toUpdate = mapper.map(order, OrderDTO.class);

        Order updated = orderService.update(toUpdate);

        Assertions.assertNotNull(updated.getId());
        assertEquals(toUpdate.isOpen(), updated.isOpen());
    }

    @Test
    @DisplayName("Should find Order by id")
    void shouldFindById() {
        Order founded = orderService.findById(order.getId());

        Assertions.assertNotNull(founded.getId());
        assertEquals(founded.getItems().stream().findFirst().get().getId(), founded.getItems().stream().findFirst().get().getId());

    }


    @Test
    @Transactional
    @DisplayName("Should find all created Orders")
    void shouldFindAll() {
        OrderCreateDTO toCreate = OrderTestHelper.orderCreateDTO();

        for (int i = 0; i < 11; i++) {
            orderService.insert(toCreate);
        }

        List<OrderDTO> allItems = orderService.findAll();

        assertEquals(12, allItems.size(), "The number of Orders found should be 12");
    }

    @Test
    @DisplayName("Should delete Order")
    void shouldDeleteOrder() {
        Optional<Order> founded = orderRepository.findById(order.getId());
        assertTrue(founded.isPresent());

        orderService.delete(order.getId());

        Optional<Order> deleted = orderRepository.findById(order.getId());
        assertFalse(deleted.isPresent());
    }
}
