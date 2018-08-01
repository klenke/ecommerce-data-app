package io.pivotal.order.service;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.pivotal.order.domain.Address;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AddressService {

  private RestTemplate restTemplate;


  public AddressService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @HystrixCommand(fallbackMethod = "getAddressFallback")
  public Address getAddressByAddressId(Long addressId){
    return restTemplate.getForObject("//account-service/address/" + addressId, Address.class);
  }

  public Address getAddressFallback(Long addressId){
    return null;
  }
}
