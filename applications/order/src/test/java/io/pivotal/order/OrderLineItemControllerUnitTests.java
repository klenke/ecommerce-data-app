package io.pivotal.order;


import io.pivotal.order.controller.OrderLineItemController;
import io.pivotal.order.domain.Order;
import io.pivotal.order.domain.OrderLineItem;
import io.pivotal.order.repository.OrderLineItemRepository;
import io.pivotal.order.repository.OrderRepository;
import io.pivotal.order.service.ProductService;
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
import org.springframework.web.client.RestTemplate;

import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderLineItemController.class)
public class OrderLineItemControllerUnitTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OrderLineItemRepository orderLineItemRepository;

  @MockBean
  private OrderRepository orderRepository;

  @MockBean
  private ProductService productService;

  @MockBean
  private RestTemplate restTemplate;

  @MockBean
  private org.springframework.cloud.netflix.eureka.CloudEurekaInstanceConfig instanceConfig;

  @MockBean
  private org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration eurekaDiscoveryClientConfiguration;

  @MockBean
  private com.netflix.discovery.EurekaClient eurekaClient;

  @MockBean
  private com.netflix.discovery.EurekaClientConfig eurekaClientConfig;

  @InjectMocks
  private OrderLineItemController orderLineItemController;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testCreateOrderLineItem() throws Exception {
    double price = 100.00;
    when(restTemplate.getForObject(anyString(), eq(Double.class))).thenReturn(price);

    Order order = new Order(1L, new Date(1234), 1L);
    when(orderRepository.findOne(anyLong())).thenReturn(order);

    OrderLineItem orderLineItem = new OrderLineItem(1L, 20, 100.00, 4L, order);
    when(orderLineItemRepository.save(any(OrderLineItem.class))).thenReturn(orderLineItem);

    mockMvc
        .perform(post("/lineItems")
          .param("productId", "1")
          .param("quantity", "20")
          .param("shipmentId", "4")
          .param("orderNumber", "1"))
        .andExpect(status().isCreated())
        .andReturn();
  }

  @Test
  public void testCreateOrderLineItemWhereProductDoesNotExist() throws Exception {
    when(restTemplate.getForObject(anyString(), eq(Double.class))).thenReturn(-1d);

    mockMvc
        .perform(post("/lineItems")
            .param("productId", "1")
            .param("quantity", "20")
            .param("shipmentId", "4")
            .param("orderNumber", "1"))
        .andExpect(status().isNotFound())
        .andReturn();
  }

  @Test
  public void testCreateOrderLineItemWhereOrderDoesNotExist() throws Exception {
    double price = 100.00;
    when(restTemplate.getForObject(anyString(), eq(Double.class))).thenReturn(price);

    when(orderRepository.findOne(anyLong())).thenReturn(null);

    mockMvc
        .perform(post("/lineItems")
            .param("productId", "1")
            .param("quantity", "20")
            .param("shipmentId", "4")
            .param("orderNumber", "1"))
        .andExpect(status().isNotFound())
        .andReturn();
  }






}
