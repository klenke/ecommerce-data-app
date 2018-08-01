package io.pivotal.order.domain;

import java.util.Set;

public class OrderInfo {
  private Long orderNumber;
  private Address shippingAddress;
  private double totalPrice;
  private Set<InfoLineItems> items;
  private Set<Shipment> shipments;

  public OrderInfo(){}

  public void setOrderNumber(Long orderNumber) {
    this.orderNumber = orderNumber;
  }

  public void setShippingAddress(Address shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

  public void setTotalPrice(double totalPrice) {
    this.totalPrice = totalPrice;
  }

  public void setItems(Set<InfoLineItems> items) {
    this.items = items;
  }

  public void setShipments(Set<Shipment> shipments) {
    this.shipments = shipments;
  }

  public Long getOrderNumber() {
    return orderNumber;
  }

  public Address getShippingAddress() {
    return shippingAddress;
  }

  public double getTotalPrice() {
    return totalPrice;
  }

  public Set<InfoLineItems> getItems() {
    return items;
  }

  public Set<Shipment> getShipments() {
    return shipments;
  }
}

