package io.pivotal.shipment.domain;

public class OrderLineItem {
  private Long id;
  private Long productId;
  private int quantity;
  private double price;
  private double totalPrice;
  private Long shipmentId;
  private Order order;

  public OrderLineItem(){}

  public OrderLineItem(Long id, Long productId, int quantity, double price, double totalPrice, Long shipmentId, Order order) {
    this.id = id;
    this.productId = productId;
    this.quantity = quantity;
    this.price = price;
    this.totalPrice = totalPrice;
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
    return totalPrice;
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
