package io.pivotal.order.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductService {

  private RestTemplate restTemplate;

  public ProductService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @HystrixCommand(fallbackMethod = "getPriceForProductIdFallback")
  public double getPriceForProductId(Long productId){
    return restTemplate.getForObject("//product-service/products/" + productId + "/price",  Double.class);
  }

  public double getPriceForProductIdFallback(Long productId){
    return -1d;
  }

  @HystrixCommand(fallbackMethod = "defaultName")
  public String getProductName(Long productId){
    return restTemplate.getForObject("//product-service/products/" + productId + "/name", String.class);
  }

  public String defaultName(Long productId){
    return "Name not found for product id " + productId.toString();
  }
}
