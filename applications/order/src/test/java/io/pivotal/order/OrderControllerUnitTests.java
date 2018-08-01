package io.pivotal.order;

import io.pivotal.order.controller.OrderController;
import io.pivotal.order.domain.Order;
import io.pivotal.order.domain.OrderInfo;
import io.pivotal.order.domain.OrderLineItem;
import io.pivotal.order.repository.OrderLineItemRepository;
import io.pivotal.order.repository.OrderRepository;
import io.pivotal.order.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerUnitTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OrderRepository orderRepository;

  @MockBean
  private OrderLineItemRepository orderLineItemRepository;

  @MockBean
  private OrderService orderService;

  @MockBean
  private org.springframework.cloud.netflix.eureka.CloudEurekaInstanceConfig instanceConfig;

  @MockBean
  private org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration eurekaDiscoveryClientConfiguration;

  @MockBean
  private com.netflix.discovery.EurekaClient eurekaClient;

  @MockBean
  private com.netflix.discovery.EurekaClientConfig eurekaClientConfig;

  @InjectMocks
  private OrderController orderController;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }


  @Test
  public void testGetByOrderNumber(){
    Order order = new Order(1L, new Date(), 1L);

    when(orderRepository.findOne(anyLong())).thenReturn(order);
    Order resp = orderController.getByOrderNumber(1L);
    assertNotNull(resp);
  }

  @Test
  public void testGetOrderInfoByOrderNumber() throws Exception {
    OrderInfo orderInfo = new OrderInfo();
    orderInfo.setTotalPrice(100);
    orderInfo.setOrderNumber(12L);

    when(orderService.getOrderInfoByOrderNumber(anyLong())).thenReturn(orderInfo);

    mockMvc
        .perform(get("/orders/{orderNumber}", 12L))
        .andExpect(status().isOk())
        .andReturn();
  }

  @Test
  public void testGetOrderLineItemsForOrder() throws Exception {
    Order order = new Order();
    List<OrderLineItem> testList = Arrays.asList(
        new OrderLineItem(1L, 20, 12.45, 1L, order),
        new OrderLineItem(2L, 45, 13.2, 1L, order)
    );
    when(orderLineItemRepository.findByOrderOrderNumber(anyLong())).thenReturn(testList);

    mockMvc
        .perform(get("/orders/{orderNumber}/lines", 12L))
        .andExpect(status().isOk())
        .andReturn();
  }

  @Test
  public void testGetOrderLineItemsForWrongOrderNumber() throws Exception {
    when(orderLineItemRepository.findByOrderOrderNumber(anyLong())).thenReturn(null);

    mockMvc
        .perform(get("/orders/{orderNumber}/lines", 12L))
        .andExpect(status().isNotFound())
        .andReturn();
  }

}
