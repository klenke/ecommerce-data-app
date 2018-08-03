package io.pivotal.shipment.domain;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class OrderLineItemClassTests {

  private OrderLineItem orderLineItem;

  @Before
  public void setup(){
    this.orderLineItem = new OrderLineItem(1L, 1L, 1, 1d, 1d, 1L, null);
  }

  @Test
  public void testCreation(){
    assertNotNull(orderLineItem);
  }

  @Test
  public void testGetId(){
    assertEquals(1L, orderLineItem.getId().longValue());
  }

  @Test
  public void testSetId(){
    Long id = 10L;
    orderLineItem.setId(id);
    assertEquals(10L, orderLineItem.getId().longValue());
  }

  @Test
  public void testGetProductId(){
    assertEquals(1L, orderLineItem.getProductId().longValue());
  }

  @Test
  public void testSetProductId(){
    Long id = 12L;
    orderLineItem.setProductId(id);
    assertEquals(id, orderLineItem.getProductId());
  }

  @Test
  public void testGetQuantity(){
    assertEquals(1, orderLineItem.getQuantity());
  }

  @Test
  public void testSetQuantity(){
    int i =4;
    orderLineItem.setQuantity(i);
    assertEquals(i, orderLineItem.getQuantity());
  }

  @Test
  public void testGetPrice(){
    assertEquals(1d, orderLineItem.getPrice(), 0.0001);
  }

  @Test
  public void testSetPrice(){
    double i = 1.50;
    orderLineItem.setPrice(i);
    assertEquals(i, orderLineItem.getPrice(), 0.0001);
  }

  @Test
  public void testGetTotalPrice(){
    assertEquals(1d, orderLineItem.getTotalPrice(), 0.0001);
  }

  @Test
  public void testSetTotalPrice(){
    double i = 10.00;
    orderLineItem.setTotalPrice(i);
    assertEquals(i, orderLineItem.getTotalPrice(), 0.0001);
  }

  @Test
  public void testGetShipmentId(){
    assertEquals(1L, orderLineItem.getShipmentId().longValue());
  }

  @Test
  public void testSetShipmentId(){
    Long id = 11L;
    orderLineItem.setShipmentId(id);
    assertEquals(id, orderLineItem.getShipmentId());
  }

  @Test
  public void testGetOrder(){
    assertNull(orderLineItem.getOrder());
  }
}
