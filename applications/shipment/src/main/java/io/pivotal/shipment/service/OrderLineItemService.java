package io.pivotal.shipment.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.pivotal.shipment.domain.OrderLineItemList;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class OrderLineItemService {

  private RestTemplate restTemplate;

  public OrderLineItemService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @HystrixCommand(fallbackMethod = "getOrderLineItemsByShipmentIdFallback")
  public OrderLineItemList getOrderLineItemsByShipmentId(Long shipmentId){
    return restTemplate
        .getForObject("//order-service/lineItems/shipments/" + shipmentId, OrderLineItemList.class);
  }

  public OrderLineItemList getOrderLineItemsByShipmentIdFallback(Long shipmentId){
    //make an empty array list instead of returning nothing
    return new OrderLineItemList(new ArrayList<>());
  }
}
