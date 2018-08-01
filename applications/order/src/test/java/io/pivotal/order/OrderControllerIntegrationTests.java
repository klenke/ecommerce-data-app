package io.pivotal.order;


import io.pivotal.order.controller.OrderController;
import io.pivotal.order.domain.Order;
import io.pivotal.order.domain.OrderInfo;
import io.pivotal.order.domain.OrderLineItem;
import io.pivotal.order.repository.OrderLineItemRepository;
import io.pivotal.order.repository.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerIntegrationTests {

  @Autowired
  private OrderController orderController;

  @Autowired
  private OrderLineItemRepository orderLineItemRepository;

  @Autowired
  private OrderRepository orderRepository;

  private Order order;

  @Before
  public void setup(){
    order = orderController.create(1L, 2L);
  }

  @Test
  public void testOrderCreation(){
    assertNotNull(order);
    assertEquals(1L, order.getAccountId().longValue());
    assertEquals(2L, order.getAddressId().longValue());
  }

  @Test
  public void testUpdateOrder(){
    Order resp = orderController.update(order.getOrderNumber(), 4L, 5L);
    assertEquals(4L, resp.getAccountId().longValue());
    assertEquals(5L, resp.getAddressId().longValue());
    assertEquals(resp.getOrderNumber(), order.getOrderNumber());
  }

  @Test
  public void testGetOrderByOrderNumber(){
    Order resp = orderController.getByOrderNumber(order.getOrderNumber());
    assertEquals(resp.getOrderNumber(), order.getOrderNumber());
    assertEquals(resp.getAccountId(), order.getAccountId());
    assertEquals(resp.getAddressId(), order.getAddressId());

  }

  @Test
  public void testDelete(){
    orderController.delete(order.getOrderNumber());
    Order resp = orderController.getByOrderNumber(order.getOrderNumber());
    assertNull(resp);
  }

  @Test
  public void testGetOrderLineItemsForOrder(){
    //each order line item here will be tied to this.order
    List<OrderLineItem> testList = Arrays.asList(
        new OrderLineItem(1L, 20, 12.45, 1L, this.order),
        new OrderLineItem(2L, 45, 13.2, 1L, this.order)
    );
    orderLineItemRepository.save(testList);

    List<OrderLineItem> resp = orderController.getOrderLineItemsForOrder(this.order.getOrderNumber()).getBody();

    assertNotNull(resp);
    assertEquals(2, resp.size());
    for(OrderLineItem o:resp){
      assertEquals(o.getOrder().getOrderNumber(), order.getOrderNumber());
    }
  }

  @Test
  public void testGetOrdersForAccount(){
    Long dummyAccountNumber = -1L;
    List<Order> testList = Arrays.asList(
        new Order(dummyAccountNumber, new Date(), 1L),
        new Order(dummyAccountNumber, new Date(), 2L),
        new Order(dummyAccountNumber, new Date(), 5L)
    );
    orderRepository.save(testList);

    List<Order> resp = orderController.getOrdersByAccountId(dummyAccountNumber).getBody();
    assertNotNull(resp);
    assertEquals(3, resp.size());

  }
}
