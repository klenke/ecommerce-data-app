package io.pivotal.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Shipment {

  @JsonIgnore
  private Long shipmentId;

  private Set<OrderLineItem> orderLineItems = new HashSet<>();
  private Date shipped;
  private Date delivered;

  public Shipment(Date shipped, Date delivered, OrderLineItem orderLineItem){
    this.shipmentId = orderLineItem.getShipmentId();
    this.shipped = shipped;
    this.delivered = delivered;
    this.orderLineItems.add(orderLineItem);
  }

  public void addToOrderLineItems(OrderLineItem o){
    orderLineItems.add(o);
  }

  public Long getShipmentId() {
    return shipmentId;
  }

  public void setShipmentId(Long shipmentId) {
    this.shipmentId = shipmentId;
  }

  public Set<OrderLineItem> getOrderLineItems() {
    return orderLineItems;
  }

  public void setOrderLineItems(Set<OrderLineItem> orderLineItems) {
    this.orderLineItems = orderLineItems;
  }

  public Date getShipped() {
    return shipped;
  }

  public void setShipped(Date shipped) {
    this.shipped = shipped;
  }

  public Date getDelivered() {
    return delivered;
  }

  public void setDelivered(Date delivered) {
    this.delivered = delivered;
  }
}
