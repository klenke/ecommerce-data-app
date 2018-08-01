package io.pivotal.shipment.domain;


import java.util.Date;
import java.util.List;

public class ShipmentInfo {

  private Long orderNumber;
  private Date shipped;
  private Date delivered;
  private List<InfoLineItems> items;

  public ShipmentInfo(){}

  public Long getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(Long orderNumber) {
    this.orderNumber = orderNumber;
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

  public List<InfoLineItems> getItems() {
    return items;
  }

  public void setItems(List<InfoLineItems> items) {
    this.items = items;
  }
}
