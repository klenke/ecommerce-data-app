package io.pivotal.order.domain;

import io.pivotal.order.domain.Order;
import io.pivotal.order.domain.OrderLineItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class OrderLineItemClassTests {

  private OrderLineItem orderLineItem;

  private Order newOrder = new Order(1L, new Date(), 1L);

  @Before
  public void setup() {
    this.orderLineItem = new OrderLineItem(1L, 20, 2d, 1L, newOrder);
  }

  @Test
  public void testOrderLineItemCreation(){
    assertNotNull(orderLineItem);
  }

  @Test
  public void testGetProductId(){
    assertEquals(1L, orderLineItem.getProductId().longValue());
  }

  @Test
  public void testGetQuantity(){
    assertEquals(20, orderLineItem.getQuantity());
  }

  @Test
  public void testGetPrice(){
    assertEquals(2d, orderLineItem.getPrice(), .001);
  }

  @Test
  public void testGetTotalPrice(){
    assertEquals(2d * 20, orderLineItem.getTotalPrice(), .001);
  }

  @Test
  public void testGetShipmentId(){
    assertEquals(1L, orderLineItem.getShipmentId().longValue());
  }

}
