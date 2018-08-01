package io.pivotal.order;

import io.pivotal.order.domain.Address;
import io.pivotal.order.domain.Order;
import io.pivotal.order.domain.OrderInfo;
import io.pivotal.order.domain.OrderLineItem;
import io.pivotal.order.repository.OrderRepository;
import io.pivotal.order.service.AddressService;
import io.pivotal.order.service.OrderService;
import io.pivotal.order.service.ProductService;
import io.pivotal.order.service.ShipmentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class OrderServiceUnitTests {

  @Mock
  private RestTemplate restTemplate;

  @Mock
  private AddressService addressService;

  @Mock
  private ProductService productService;

  @Mock
  private ShipmentService shipmentService;

  @Mock
  private OrderRepository orderRepository;

  @MockBean
  private com.netflix.discovery.EurekaClient eurekaClient;

  @MockBean
  private com.netflix.discovery.EurekaClientConfig eurekaClientConfig;

  @InjectMocks
  private OrderService orderService;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testGetOrderInfoByOrderNumber(){
    Order order = new Order(1L, new Date(123456789), 2L);
    order.setOrderNumber(1L);
    when(orderRepository.findOne(any(Long.class))).thenReturn(order);

    Address address = new Address("1232 W Eddy", "Unit G", "Chicago", "IL", "60657", "USA");
    //when(restTemplate.getForObject(anyString(), eq(Address.class))).thenReturn(address);
    when(addressService.getAddressByAddressId(anyLong())).thenReturn(address);

    Set<OrderLineItem> testList = new HashSet<>(Arrays.asList(
        new OrderLineItem(1L, 20, 12.45, 1L, order),
        new OrderLineItem(2L, 45, 13.2, 1L, order)
    ));

    order.setOrderLineItems(testList);

    String name = "Dovahkiin";
    when(productService.getProductName(anyLong())).thenReturn(name);
    //when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(name);

    Date dummyDate = new Date(123456789);
    when(shipmentService.getShippedDate(anyLong())).thenReturn(dummyDate);
    when(shipmentService.getDeliveredDate(anyLong())).thenReturn(dummyDate);
    //when(restTemplate.getForObject(anyString(), eq(Date.class))).thenReturn(dummyDate);

    OrderInfo response = orderService.getOrderInfoByOrderNumber(1L);
    assertNotNull(response);
    assertEquals(response.getOrderNumber(), order.getOrderNumber());
    assertEquals(response.getTotalPrice(), order.getPrice(), 0.0001);
    assertEquals(response.getShippingAddress(), address);
    assertEquals(response.getItems().size(), testList.size());
  }
}
