package io.pivotal.product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ProductClassTests {

  private Product product;

  @Before
  public void setup(){
    this.product = new Product("Sham-wow", "Mop up your hopes and dreams!", "imgur.com", 19.99);
  }

  @Test
  public void testProductCreation(){
    assertNotNull(product);
  }

  @Test
  public void testGetProductName(){
    assertEquals("Sham-wow", product.getName());
  }

  @Test
  public void testGetProductDescription(){
    assertEquals("Mop up your hopes and dreams!", product.getDescription());
  }

  @Test
  public void testGetImageUrl(){
    assertEquals("imgur.com", product.getImageUrl());
  }

  @Test
  public void testGetPrice(){
    assertEquals(19.99, product.getPrice(), 0.001);
  }
}
