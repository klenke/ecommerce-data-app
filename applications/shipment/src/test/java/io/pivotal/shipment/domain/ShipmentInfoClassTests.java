package io.pivotal.shipment.domain;

import io.pivotal.shipment.domain.ShipmentInfo;
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
public class ShipmentInfoClassTests {

  private ShipmentInfo shipmentInfo;

  @Before
  public void setup(){
    this.shipmentInfo = new ShipmentInfo();
  }

  @Test
  public void testCreation(){
    assertNotNull(shipmentInfo);
  }

  @Test
  public void testSetOrderNumber(){
    shipmentInfo.setOrderNumber(12L);
    assertEquals(shipmentInfo.getOrderNumber().longValue(), 12L);
  }

  @Test
  public void testSetShipped(){
    shipmentInfo.setShipped(new Date(123));
    assertEquals(new Date(123), shipmentInfo.getShipped());
  }

  @Test
  public void testSetDelievered(){
    shipmentInfo.setDelivered(new Date(123));
    assertEquals(new Date(123), shipmentInfo.getDelivered());
  }
}
