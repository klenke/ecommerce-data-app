package io.pivotal.product;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerUnitTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProductRepository productRepository;

  @MockBean
  private org.springframework.cloud.netflix.eureka.CloudEurekaInstanceConfig instanceConfig;

  @MockBean
  private org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration eurekaDiscoveryClientConfiguration;

  @InjectMocks
  private ProductController productController;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testCreate(){
    Product product = new Product("Buzz Lightyear", "To infinity and beyond", "www.toystory.com", 5.00);
    when(productRepository.save(any(Product.class))).thenReturn(product);

    Product resp = productController.create("B", "K", "d", 10.00).getBody();
    assertNotNull(resp);
    assertEquals("Buzz Lightyear", resp.getName());
    assertEquals("To infinity and beyond", resp.getDescription());
  }

  @Test
  public void testCreateWithFailure(){
    when(productRepository.save(any(Product.class))).thenReturn(null);
    HttpStatus resp = productController.create("B","K", "bk.com", 10.0).getStatusCode();
    assertEquals(HttpStatus.NOT_FOUND, resp);
  }

  @Test
  public void testGetNameByProductId() throws Exception {
    Product product = new Product("Buzz Lightyear", "To infinity and beyond", "www.toystory.com", 5.00);
    when(productRepository.findOne(anyLong())).thenReturn(product);

    mockMvc
        .perform(get("/products/{id}/name", 1L))
        .andExpect(status().isOk())
        .andExpect(content().string("Buzz Lightyear"))
        .andReturn();
  }

  @Test
  public void testGetNameByProductIdFailure() throws Exception {
    when(productRepository.findOne(anyLong())).thenReturn(null);

    mockMvc
        .perform(get("/products/{id}/name", 1L))
        .andExpect(status().isOk())
        .andReturn();
  }

}
