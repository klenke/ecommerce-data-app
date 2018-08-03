package io.pivotal.order.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class OrderLineItemListsClassTests {

  private OrderLineItemList orderLineItemList;

  @Before
  public void setup(){
    List<OrderLineItem> testList = Arrays.asList(
        new OrderLineItem(1L, 2, 1.00, 1L, null),
        new OrderLineItem(2L, 4, 1.50, 1L, null)
    );
    orderLineItemList = new OrderLineItemList(testList);
  }

  @Test
  public void testCreation(){
    assertNotNull(orderLineItemList);
  }

  @Test
  public void testGetList(){
    List<OrderLineItem> resp = orderLineItemList.getItems();

    assertNotNull(resp);
    assertEquals(2, resp.size());
    for(OrderLineItem o: resp){
      assertEquals(1L, o.getShipmentId().longValue());
    }
  }

  @Test
  public void testSetList(){
    List<OrderLineItem> newList = Arrays.asList(
        new OrderLineItem(10L, 2, 1.00, 2L, null),
        new OrderLineItem(10L, 4, 1.50, 2L, null),
        new OrderLineItem(10L, 2, 1.00, 2L, null),
        new OrderLineItem(10L, 4, 1.50, 2L, null)
    );
    orderLineItemList.setItems(newList);

    List<OrderLineItem> resp = orderLineItemList.getItems();

    assertNotNull(resp);
    assertEquals(4, resp.size());
    for(OrderLineItem o: resp){
      assertEquals(2L, o.getShipmentId().longValue());
      assertEquals(10L, o.getProductId().longValue());
    }
  }
}
