package io.pivotal.order.service;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.pivotal.order.domain.*;
import io.pivotal.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class OrderService {

  private RestTemplate restTemplate;

  private OrderRepository orderRepository;

  private AddressService addressService;

  private ProductService productService;

  private ShipmentService shipmentService;

  public OrderService(RestTemplate restTemplate, OrderRepository orderRepository, AddressService addressService, ProductService productService, ShipmentService shipmentService) {
    this.restTemplate = restTemplate;
    this.orderRepository = orderRepository;
    this.addressService = addressService;
    this.productService = productService;
    this.shipmentService = shipmentService;
  }

  public OrderInfo getOrderInfoByOrderNumber(Long orderNumber){
    OrderInfo orderInfo = new OrderInfo();

    Order order = orderRepository.findOne(orderNumber);

    Address address = addressService.getAddressByAddressId(order.getAddressId());
    //Address address = restTemplate.getForObject("//account-service/address/" + order.getAddressId(), Address.class);

    orderInfo.setOrderNumber(orderNumber);
    orderInfo.setShippingAddress(address);
    orderInfo.setTotalPrice(order.getPrice());

    Set<InfoLineItems> lineItems = new HashSet<>();
    String name;

    Set<Shipment> shipments = new HashSet<>();
    boolean shipmentFound = false;

    for(OrderLineItem o: order.getOrderLineItems()){

      //get order item's name and add it with the price to line items
      name = productService.getProductName(o.getProductId());
      //name = restTemplate.getForObject("//product-service/products/" + o.getProductId() + "/name", String.class);
      if(name != null) {
        lineItems.add(new InfoLineItems(name, o.getQuantity()));
      }

      shipmentFound = false;
      //get the order's shipment number and if it is unique, make new shipment to add to OrderInfo's set
      for(Shipment s: shipments){
        if(o.getShipmentId() == s.getShipmentId()){
          shipmentFound = true;
          s.addToOrderLineItems(o);
          break;
        }
      }
      if(!shipmentFound){
        //need to make a new shipment locally to add order line items to
        Date shipped = shipmentService.getShippedDate(o.getShipmentId());
        //Date delivered = restTemplate.getForObject("//shipment-service/shipments/" + o.getShipmentId() + "/delivered", Date.class);
        Date delivered = shipmentService.getDeliveredDate(o.getShipmentId());
        shipments.add(new Shipment(shipped, delivered, o));
      }
    }

    orderInfo.setItems(lineItems);
    orderInfo.setShipments(shipments);

    return orderInfo;
  }

}
