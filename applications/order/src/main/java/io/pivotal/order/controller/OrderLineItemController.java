package io.pivotal.order.controller;

import io.pivotal.order.domain.Order;
import io.pivotal.order.domain.OrderLineItem;
import io.pivotal.order.domain.OrderLineItemList;
import io.pivotal.order.repository.OrderLineItemRepository;
import io.pivotal.order.repository.OrderRepository;
import io.pivotal.order.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/lineItems")
public class OrderLineItemController {

  private OrderLineItemRepository orderLineItemRepository;
  private OrderRepository orderRepository;
  private ProductService productService;

  public OrderLineItemController(
      OrderLineItemRepository orderLineItemRepository,
      OrderRepository orderRepository,
      ProductService productService) {
    this.orderLineItemRepository = orderLineItemRepository;
    this.orderRepository = orderRepository;

    this.productService = productService;
  }

  @PostMapping
  public ResponseEntity<OrderLineItem> create(
      @RequestParam("productId") Long productId,
      @RequestParam("quantity") int quantity,
      @RequestParam("shipmentId") Long shipmentId,
      @RequestParam("orderNumber") Long orderNumber){

    //find price by product id
    double price = productService.getPriceForProductId(productId);
    //double price = restTemplate.getForObject("//product-service/products/" + productId + "/price",  Double.class);
    System.out.println(price);
    if(price == -1d){
      //error getting price, product probably doesnt exist
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //find order by order number
    Order o = orderRepository.findOne(orderNumber);
    if(o == null){
      //error getting order, probably doesn't exist
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //update order total price
    o.setPrice(o.getPrice() + (quantity * price));

    OrderLineItem orderLineItem =  orderLineItemRepository.save(new OrderLineItem(productId, quantity, price, shipmentId, o));

    return new ResponseEntity<>(orderLineItem, HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<OrderLineItem> update(
      @RequestParam("id") Long id,
      @RequestParam(value = "productId", required = false) Long productId,
      @RequestParam(value = "quantity", required = false) int quantity,
      @RequestParam(value = "price", required = false) double price,
      @RequestParam(value = "shipmentId", required = false) Long shipmentId,
      @RequestParam(value = "orderNumber",required = false) Long orderNumber
  ){
    OrderLineItem orderLineItem = orderLineItemRepository.findOne(id);

    if(productId != null){
      orderLineItem.setProductId(productId);
    }
    if(quantity > 0){
      orderLineItem.setQuantity(quantity);
    }
    if(price > 0){
      orderLineItem.setPrice(price);
    }
    if(shipmentId != null){
      orderLineItem.setShipmentId(shipmentId);
    }
    if(orderNumber != null){
      orderLineItem.setOrder(orderRepository.findOne(orderNumber));
    }
    return new ResponseEntity<>(orderLineItem, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public OrderLineItem getOrderLineItemById(@PathVariable("id") Long id) {
    return orderLineItemRepository.findOne(id);
  }

  @GetMapping("/shipments/{shipmentId}")
  public OrderLineItemList getOrderLineItemsByShipmentId(@PathVariable("shipmentId")Long shipmentId){
    return new OrderLineItemList(orderLineItemRepository.findByShipmentId(shipmentId));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable("id") Long id) {
    try {
      orderLineItemRepository.delete(id);
    } catch (Exception e){
      return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }
    return new ResponseEntity(HttpStatus.OK);
  }
}
