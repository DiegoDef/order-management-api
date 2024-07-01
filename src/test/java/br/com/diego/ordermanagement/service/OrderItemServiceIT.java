package br.com.diego.ordermanagement.service;

import br.com.diego.ordermanagement.config.TestOrderConfiguration;
import br.com.diego.ordermanagement.dto.ItemDTO;
import br.com.diego.ordermanagement.dto.OrderDTO;
import br.com.diego.ordermanagement.dto.OrderItemDTO;
import br.com.diego.ordermanagement.entity.Item;
import br.com.diego.ordermanagement.entity.Order;
import br.com.diego.ordermanagement.entity.OrderItem;
import br.com.diego.ordermanagement.helper.ItemTestHelper;
import br.com.diego.ordermanagement.helper.OrderItemTestHelper;
import br.com.diego.ordermanagement.helper.OrderTestHelper;
import br.com.diego.ordermanagement.respository.ItemRepository;
import br.com.diego.ordermanagement.respository.OrderItemRepository;
import br.com.diego.ordermanagement.respository.OrderRepository;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = TestOrderConfiguration.class)
class OrderItemServiceIT {

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ModelMapper mapper;

    private OrderItem orderItem;

    @BeforeEach
    public void setup() {
        Item item = ItemTestHelper.createItem();
        item.setService(true);
        Item createdItem = itemRepository.save(item);
        Order toCreate = OrderTestHelper.createOrder();
        toCreate.setItems(List.of(createdItem));
        Order order = orderRepository.save(toCreate);

        orderItem = orderItemRepository.save(OrderItemTestHelper.createOrderItem(item, order));
    }

    @AfterEach
    public void tearDown() {
        orderItemRepository.deleteAll();
        itemRepository.deleteAll();
        orderRepository.deleteAll();
    }

    @Test
    @DisplayName("Should create OrderItem")
    void shouldCreateOrderItem() {
        Item item = ItemTestHelper.createItem();
        item.setService(true);
        Item createdItem = itemRepository.save(item);
        Order order = OrderTestHelper.createOrder();
        order.setItems(List.of(createdItem));
        orderRepository.save(order);
        OrderItem toCreate = OrderItemTestHelper.createOrderItem(item, mapper.map(order, Order.class));

        OrderItem created = orderItemService.insert(mapper.map(toCreate, OrderItemDTO.class));

        Assertions.assertNotNull(created.getId());
        assertEquals(toCreate.getItem().getId(), created.getItem().getId());
        assertEquals(toCreate.getOrder().getId(), created.getOrder().getId());
    }


    @Test
    @DisplayName("Should update OrderItem")
    void shouldUpdateOrderItem() {
        Item item = ItemTestHelper.createItem();
        item.setService(true);
        Item createdItem = itemRepository.save(item);
        Order order = OrderTestHelper.createOrder();
        order.setItems(List.of(createdItem));
        orderRepository.save(order);
        orderItem.setItem(item);
        orderItem.setOrder(order);
        OrderItemDTO toUpdate = mapper.map(orderItem, OrderItemDTO.class);

        OrderItem updated = orderItemService.update(toUpdate);

        Assertions.assertNotNull(updated.getId());
        assertEquals(toUpdate.getItem().getId(), updated.getItem().getId());
        assertEquals(toUpdate.getOrder().getId(), updated.getOrder().getId());
    }

    @Test
    @DisplayName("Should find OrderItem by id")
    void shouldFindById() {
        OrderItem founded = orderItemService.findById(orderItem.getId());

        Assertions.assertNotNull(founded.getId());
        assertEquals(orderItem.getItem().getId(), founded.getItem().getId());
        assertEquals(orderItem.getOrder().getId(), founded.getOrder().getId());
    }

    @Test
    @Transactional
    @DisplayName("Should find all created OrderItems")
    void shouldFindAll() {
        Item item = ItemTestHelper.createItem();
        item.setService(true);
        Item createdItem = itemRepository.save(item);
        Order order = OrderTestHelper.createOrder();
        order.setItems(List.of(createdItem));
        orderRepository.save(order);
        OrderItemDTO toCreate = OrderItemTestHelper.createOrderItemDTO(mapper.map(item, ItemDTO.class), mapper.map(order, OrderDTO.class));

        for (int i = 0; i < 11; i++) {
            orderItemService.insert(toCreate);
        }

        List<OrderItemDTO> allOrdemItems = orderItemService.findAll();

        assertEquals(12, allOrdemItems.size(), "The number of OrderItems found should be 12");
    }

    @Test
    @DisplayName("Should delete OrderItem")
    void shouldDeleteOrder() {
        Optional<OrderItem> founded = orderItemRepository.findById(orderItem.getId());
        assertTrue(founded.isPresent());

        orderItemService.delete(orderItem.getId());

        Optional<Order> deleted = orderRepository.findById(orderItem.getId());
        assertFalse(deleted.isPresent());
    }
}
