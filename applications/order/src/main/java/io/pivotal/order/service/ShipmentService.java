package io.pivotal.order.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
public class ShipmentService {

  private RestTemplate restTemplate;

  public ShipmentService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @HystrixCommand(fallbackMethod = "getDateFallback")
  public Date getShippedDate(Long shipmentId){
    return restTemplate.getForObject("//shipment-service/shipments/" + shipmentId + "/shipped", Date.class);
  }

  @HystrixCommand(fallbackMethod = "getDateFallback")
  public Date getDeliveredDate(Long shipmentId){
    return restTemplate.getForObject("//shipment-service/shipments/" + shipmentId + "/delivered", Date.class);
  }

  public Date getDateFallback(Long shipmentId){
    return null;
  }
}
