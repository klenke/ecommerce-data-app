package io.pivotal.order;


import io.pivotal.order.controller.OrderLineItemController;
import io.pivotal.order.domain.OrderLineItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderLineItemControllerIntegrationTests {

  @Autowired
  private OrderLineItemController orderLineItemController;

  private OrderLineItem orderLineItem;

  @Before
  public void setup(){
    orderLineItem = orderLineItemController.create(1L, 20, 1L, 1L).getBody();
  }

  @Test
  public void testOrderLineItemCreation(){
    assertNotNull(orderLineItem);
    assertEquals(1L, orderLineItem.getProductId().longValue());
    assertEquals(20, orderLineItem.getQuantity());
    assertEquals(1L, orderLineItem.getShipmentId().longValue());
  }

  @Test
  public void testUpdateOrderLineItem(){
    OrderLineItem resp = orderLineItemController.update(orderLineItem.getId(), 2L, 10, 12.50, 2L, 2L).getBody();
    assertEquals(2L, resp.getProductId().longValue());
    assertEquals(10, resp.getQuantity());
    assertEquals(2L, resp.getShipmentId().longValue());
    assertEquals(orderLineItem.getId(), resp.getId());
  }

  @Test
  public void testGetOrderLineItemById(){
    OrderLineItem resp = orderLineItemController.getOrderLineItemById(orderLineItem.getId());
    assertNotNull(resp);
    assertEquals(resp.getId(), orderLineItem.getId());
    assertEquals(resp.getShipmentId(), orderLineItem.getShipmentId());
    assertEquals(resp.getQuantity(), orderLineItem.getQuantity());
    assertEquals(resp.getTotalPrice(), orderLineItem.getTotalPrice(), 0.001);
  }

  @Test
  public void testDelete(){
    orderLineItemController.delete(orderLineItem.getId());
    OrderLineItem resp = orderLineItemController.getOrderLineItemById(orderLineItem.getId());
    assertNull(resp);
  }

}
