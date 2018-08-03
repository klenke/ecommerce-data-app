package io.pivotal.order.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class InfoLineItemsClassTests {

  private InfoLineItems infoLineItems;

  @Before
  public void setup(){
    infoLineItems = new InfoLineItems("Bop it", 100);
  }

  @Test
  public void testCreation(){
    assertNotNull(infoLineItems);
  }

  @Test
  public void testGetName(){
    assertEquals("Bop it", infoLineItems.getName());
  }

  @Test
  public void testGetQuantity(){
    assertEquals(100, infoLineItems.getQuantity());
  }

  @Test
  public void testSetName(){
    String name = "Mug";
    infoLineItems.setName(name);
    assertEquals(name, infoLineItems.getName());
  }

  @Test
  public void testSetQuantity(){
    int quantity = 40;
    infoLineItems.setQuantity(quantity);
    assertEquals(quantity, infoLineItems.getQuantity());
  }
}
