package io.pivotal.product;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIntegrationTests {

  @Autowired
  private ProductController productController;

  private Product product;

  @Before
  public void setup(){
    product = productController.create("Bonsai Tree", "A beautiful shrub", "imgur.com", 13.50).getBody();
  }

  @Test
  public void testProductCreation(){
    assertNotNull(product);
    assertEquals("Bonsai Tree", product.getName());
    assertEquals("A beautiful shrub", product.getDescription());
    assertEquals("imgur.com", product.getImageUrl());
    assertEquals(13.50, product.getPrice(), 0.001);
  }

  @Test
  public void testUpdateProduct(){
    Product resp = productController.update(product.getId(), "Bonsaid Tree", "Twas a beautiful shrub", "imgured.com", 1.49).getBody();
    assertEquals(resp.getName(), "Bonsaid Tree");
    assertEquals(resp.getDescription(), "Twas a beautiful shrub");
    assertEquals(resp.getImageUrl(), "imgured.com");
    assertEquals(resp.getPrice(), 1.49, 0.001);
    assertEquals(resp.getId(), product.getId());
  }

  @Test
  public void testGetProductById(){
    Product resp = productController.getById(product.getId()).getBody();
    assertEquals(resp.getId(), product.getId());
    assertEquals(resp.getPrice(), product.getPrice(), 0.001);
    assertEquals(resp.getImageUrl(), product.getImageUrl());
    assertEquals(resp.getDescription(), product.getDescription());
    assertEquals(resp.getName(), product.getName());
  }

  @Test
  public void testDelete(){
    productController.delete(product.getId());
    Product resp = productController.getById(product.getId()).getBody();
    assertNull(resp);
  }

  @Test
  public void testGetPriceByProductId(){
    double resp = productController.getPriceByProductId(product.getId());
    assertEquals(resp, product.getPrice(), 0.001);
  }

  @Test
  public void testGetNameByProductId(){
    String resp = productController.getNameByProductId(product.getId());
    assertEquals(resp, product.getName());
  }
}
