package io.pivotal.order.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.pivotal.order.domain.Order;

import javax.persistence.*;

@Table(name = "order_line_item")
@Entity
public class OrderLineItem {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column
  private Long productId;

  @Column
  private int quantity;

  @Column
  private double price;

  @Column
  private double totalPrice;

  @Column
  private Long shipmentId;

  @ManyToOne
  @JoinColumn(name = "order_number")
  @JsonIgnoreProperties({"account", "orderDate", "address", "orderLineItems", "price"})
  private Order order;

  public OrderLineItem(){}

  public OrderLineItem(Long productId, int quantity, double price, Long shipmentId, Order order) {
    this.productId = productId;
    this.quantity = quantity;
    this.price = price;
    this.totalPrice = quantity * price;
    this.shipmentId = shipmentId;
    this.order = order;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public double getTotalPrice() {
    return this.price * this.quantity;
  }

  public void setTotalPrice(double totalPrice) {
    this.totalPrice = totalPrice;
  }

  public Long getShipmentId() {
    return shipmentId;
  }

  public void setShipmentId(Long shipmentId) {
    this.shipmentId = shipmentId;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }
}
