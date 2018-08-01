package io.pivotal.order;


import io.pivotal.order.domain.Address;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AddressClassTests {

  private Address address;

  @Before
  public void setup() {
    this.address = new Address("111 N Canal", "Floor 7", "Chicago", "IL", "60657", "USA");
  }

  @Test
  public void testAddressCreation() { assertNotNull(address); }

  @Test
  public void testGetStreet(){
    assertEquals("111 N Canal", address.getStreet());
  }

  @Test
  public void testGetApt(){
    assertEquals("Floor 7", address.getApt());
  }

  @Test
  public void testGetCity(){
    assertEquals("Chicago", address.getCity());
  }

  @Test
  public void testGetState(){
    assertEquals("IL", address.getState());
  }

  @Test
  public void testGetCountry(){
    assertEquals("USA", address.getCountry());
  }

  @Test
  public void testGetZip(){
    assertEquals("60657", address.getZip());
  }

}
