package io.pivotal.shipment.domain;


import io.pivotal.shipment.domain.Shipment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ShipmentClassTests {

  private Shipment shipment;

  @Before
  public void setup(){
    this.shipment = new Shipment(1L, 2L, new Date(100000), new Date(100001));
  }

  @Test
  public void testShipmentCreation(){
    assertNotNull(shipment);
  }

  @Test
  public void testGetAccountId(){
    assertEquals(1L, shipment.getAccountId().longValue());
  }

  @Test
  public void testGetAddressId(){
    assertEquals(2L, shipment.getAddressId().longValue());
  }

  @Test
  public void testGetShippingDate(){
    assertEquals(new Date(100000), shipment.getShipped());
  }

  @Test
  public void testGetDeliveryDate(){
    assertEquals(new Date(100001), shipment.getDelivered());
  }
}
