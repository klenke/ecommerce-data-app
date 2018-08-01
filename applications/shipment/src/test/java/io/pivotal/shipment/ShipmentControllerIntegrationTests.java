package io.pivotal.shipment;


import io.pivotal.shipment.domain.Shipment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShipmentControllerIntegrationTests {

  @Autowired
  private ShipmentController shipmentController;

  private Shipment shipment;

  @Before
  public void setup(){
    this.shipment = shipmentController.create(1L, 2L).getBody();
  }

  @Test
  public void testShipmentCreation(){
    assertNotNull(shipment);
    assertEquals(1L, shipment.getAccountId().longValue());
    assertEquals(2L, shipment.getAddressId().longValue());
  }

  @Test
  public void testShipmentUpdate(){
    Shipment resp = shipmentController.update(shipment.getId(), 2L, 3L, new Date(123456789), new Date(123456799)).getBody();
    assertEquals(2L, resp.getAccountId().longValue());
    assertEquals(3L, resp.getAddressId().longValue());
    assertEquals(new Date(123456789), resp.getShipped());
    assertEquals(new Date(123456799), resp.getDelivered());
    assertEquals(resp.getId(), shipment.getId());
  }

  @Test
  public void testGetShipmentById(){
    Shipment resp = shipmentController.getById(shipment.getId()).getBody();
    assertEquals(resp.getAccountId(), shipment.getAccountId());
    assertEquals(resp.getAddressId(), shipment.getAddressId());
    assertEquals(resp.getId(), shipment.getId());
  }

  @Test
  public void testGetShippedDateByShipmentId() {
    Date shipped = shipmentController.getShippedById(shipment.getId());
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
    //sdf.parse(shipped.toString());
    //assertEquals(sdf.parse(shipped.toString()), sdf.parse(shipment.getShipped().toString()));
    assertEquals(sdf.format(shipped), sdf.format(shipment.getShipped()));

  }

  @Test
  public void testGetDeliveredDateByShipmentId() {
    Date delivered = shipmentController.getDeliveredById(shipment.getId());
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
    assertEquals(sdf.format(delivered), sdf.format(shipment.getDelivered()));
  }

  @Test
  public void testDelete(){
    assertEquals(shipmentController.delete(shipment.getId()), new ResponseEntity(HttpStatus.OK));
    Shipment resp = shipmentController.getById(shipment.getId()).getBody();
    assertNull(resp);
  }
}
