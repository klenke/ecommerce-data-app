package io.pivotal.shipment.domain;


import io.pivotal.shipment.domain.Order;
import io.pivotal.shipment.domain.OrderLineItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class OrderClassTests {

  private Order order;

  private Set<OrderLineItem> orderLineItems = new HashSet<>(Arrays.asList(
      new OrderLineItem(10L, 11L, 10, 13.50, 135d, 13L, order),
      new OrderLineItem(11L, 12L, 10, .50, 5.00, 13L, order)
  ));

  @Before
  public void setup(){ this.order = new Order(1L, 2L, new Date(12345), 3L, orderLineItems, 140.00); }


  @Test
  public void testCreation(){
    assertNotNull(order);
  }

  @Test
  public void testGetOrderNumber(){
    assertEquals(1L, order.getOrderNumber().longValue());
  }

  @Test
  public void testSetOrderNumber(){
    Long orderNumber = 12L;
    order.setOrderNumber(orderNumber);
    assertEquals(orderNumber, order.getOrderNumber());
  }

  @Test
  public void testGetAccountId(){
    assertEquals(2L, order.getAccountId().longValue());
  }

  @Test
  public void testSetAccountId(){
    Long accountId = 15L;
    order.setAccountId(accountId);
    assertEquals(accountId, order.getAccountId());
  }

  @Test
  public void testGetOrderDate(){
    assertEquals(new Date(12345), order.getOrderDate());
  }

  @Test
  public void testSetOrderDate() {
    Date date = new Date(11111);
    order.setOrderDate(date);
    assertEquals(date, order.getOrderDate());
  }

  @Test
  public void testGetAddressId(){
    assertEquals(3L, order.getAddressId().longValue());
  }

  @Test
  public void testGetPrice(){
    assertEquals(140.00, order.getPrice(), 0.001);
  }
}
