package io.pivotal.shipment.domain;

import java.util.Date;
import java.util.Set;

public class Order {
  private Long orderNumber;
  private Long accountId;
  private Date orderDate;
  private Long addressId;
  private Set<OrderLineItem> orderLineItems;
  private double price;

  public Order(){}

  public Order(Long orderNumber, Long accountId, Date orderDate, Long addressId, Set<OrderLineItem> orderLineItems, double price) {
    this.orderNumber = orderNumber;
    this.accountId = accountId;
    this.orderDate = orderDate;
    this.addressId = addressId;
    this.orderLineItems = orderLineItems;
    this.price = price;
  }

  public Long getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(Long orderNumber) {
    this.orderNumber = orderNumber;
  }

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public Date getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(Date orderDate) {
    this.orderDate = orderDate;
  }

  public Long getAddressId() {
    return addressId;
  }

  public void setAddressId(Long addressId) {
    this.addressId = addressId;
  }

  public Set<OrderLineItem> getOrderLineItems() {
    return orderLineItems;
  }

  public void setOrderLineItems(Set<OrderLineItem> orderLineItems) {
    this.orderLineItems = orderLineItems;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }
}
