package io.pivotal.shipment;


import io.pivotal.shipment.domain.*;
import io.pivotal.shipment.service.OrderLineItemService;
import io.pivotal.shipment.service.ProductService;
import io.pivotal.shipment.service.ShipmentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ShipmentServiceUnitTests {

  @Mock
  private ShipmentRepository shipmentRepository;

  @Mock
  private OrderLineItemService orderLineItemService;

  @Mock
  private ProductService productService;

  @InjectMocks
  private ShipmentService shipmentService;

  @Before
  public void setup(){
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testGetShipmentsForAccount(){
    Long accountId = 12L;
    List<Shipment> shipments = Arrays.asList(
        new Shipment(accountId, 2L, new Date(), new Date()),
        new Shipment(accountId, 1L, new Date(), new Date()),
        new Shipment(accountId, 4L, new Date(), new Date()),
        new Shipment(accountId, 2L, new Date(), new Date())
    );

    when(shipmentRepository.findByAccountIdOrderByDeliveredAsc(anyLong())).thenReturn(shipments);

    List<OrderLineItem> lineItems = Arrays.asList(
        new OrderLineItem(1L, 1L, 2, .25, .50, 1L, new Order()),
        new OrderLineItem(2L, 2L, 4, .25, 1.00, 1L, new Order()),
        new OrderLineItem(3L, 3L, 6, .25, 1.50, 1L, new Order()),
        new OrderLineItem(4L, 4L, 8, .25, 2.0, 1L, new Order())
    );
    OrderLineItemList orderLineItemList = new OrderLineItemList(lineItems);

    when(orderLineItemService.getOrderLineItemsByShipmentId(anyLong())).thenReturn(orderLineItemList);
    //when(restTemplate.getForObject(anyString(), eq(OrderLineItemList.class))).thenReturn(orderLineItemList);

    String name = "Dovahkiin";
    when(productService.getProductName(anyLong())).thenReturn(name);
    //when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(name);

    List<ShipmentInfo> shipmentInfoList = shipmentService.getShipmentsForAccount(accountId);
    assertNotNull(shipmentInfoList);
    assertEquals(4, shipmentInfoList.size());
  }
}
