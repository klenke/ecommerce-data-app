package io.pivotal.order.controller;


import io.pivotal.order.domain.*;
import io.pivotal.order.repository.OrderLineItemRepository;
import io.pivotal.order.repository.OrderRepository;
import io.pivotal.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/orders")
public class OrderController {

  private OrderRepository orderRepository;

  private OrderLineItemRepository orderLineItemRepository;

  private OrderService orderService;

  public OrderController(
      OrderRepository orderRepository,
      OrderLineItemRepository orderLineItemRepository,
      OrderService orderService) {
    this.orderRepository = orderRepository;
    this.orderLineItemRepository = orderLineItemRepository;
    this.orderService = orderService;
  }

  @PostMapping()
  public Order create(
      @RequestParam("accountId") Long accountId,
      @RequestParam("addressId") Long addressId){

    return orderRepository.save(new Order(accountId, new Date(), addressId));

  }

  @PutMapping()
  public Order update(@RequestParam("orderNumber") Long orderNumber,
      @RequestParam("accountId") Long accountId,
      @RequestParam("addressId") Long addressId) {

    Order order = orderRepository.findOne(orderNumber);
    order.setAccountId(accountId);
    order.setAddressId(addressId);

    return orderRepository.save(order);
  }

  @GetMapping("/{orderNumber}")
  public OrderInfo getOrderInfoByOrderNumber(@PathVariable("orderNumber") Long orderNumber){
   return orderService.getOrderInfoByOrderNumber(orderNumber);
  }

  public Order getByOrderNumber(Long orderNumber){
    return orderRepository.findOne(orderNumber);
  }

  @GetMapping()
  public ResponseEntity<List<Order>> getOrdersByAccountId(@RequestParam("accountId") Long accountId){
    List<Order> orders = orderRepository.findByAccountIdOrderByOrderDateAsc(accountId);
    if(orders != null){
      return new ResponseEntity<>(orders, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{orderNumber}")
  public ResponseEntity delete(@PathVariable("orderNumber") Long orderNumber) {
    try {
      orderRepository.delete(orderNumber);
    } catch (Exception e){
      return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }
    return new ResponseEntity(HttpStatus.OK);
  }

  @GetMapping("/{orderNumber}/lines")
  public ResponseEntity<List<OrderLineItem>> getOrderLineItemsForOrder(@PathVariable("orderNumber") Long orderNumber) {
    List<OrderLineItem> resp = orderLineItemRepository.findByOrderOrderNumber(orderNumber);
    if(resp != null){
      return new ResponseEntity<>(resp, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
