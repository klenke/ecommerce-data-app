package io.pivotal.order;


import io.pivotal.order.domain.Order;
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
public class OrderClassTests {

  private Order order;

  @Before
  public void setup() {
    this.order = new Order(1L, new Date(), 2L);
  }

  @Test
  public void testOrderCreation() { assertNotNull(order); }

  @Test
  public void testGetAccountId(){
    assertEquals(1L, order.getAccountId().longValue());
  }

  @Test
  public void testGetAddressId(){
    assertEquals(2L, order.getAddressId().longValue());
  }
}
