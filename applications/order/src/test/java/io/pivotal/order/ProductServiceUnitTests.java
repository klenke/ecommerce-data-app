package io.pivotal.order;


import io.pivotal.order.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ProductServiceUnitTests {

  @Mock
  private RestTemplate restTemplate;

  @InjectMocks
  private ProductService productService;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testGetPriceForProductIdFallback(){
    double price = productService.getPriceForProductIdFallback(1L);
    assertEquals(-1d, price, 0.001);
  }

  @Test
  public void testGetProductName(){
    String name = "Dovahkiin";
    when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(name);
    String resp = productService.getProductName(1L);
    assertEquals(name, resp);
  }

  @Test
  public void testDefaultName(){
    String resp = productService.defaultName(1L);
    assertEquals("1", resp);
  }
}
