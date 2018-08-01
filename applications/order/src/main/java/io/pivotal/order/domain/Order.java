package io.pivotal.order.domain;


import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Table(name = "orders")
@Entity
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long orderNumber;

  @Column
  private Long accountId;

  @Column
  //@Temporal(TemporalType.DATE)
  private Date orderDate;

  @Column
  private Long addressId;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "order_number")
  private Set<OrderLineItem> orderLineItems;

  @Column
  private double price;

  public Order(){}

  public Order(Long accountId, Date orderDate, Long addressId){
    this.accountId = accountId;
    this.orderDate = orderDate;
    this.addressId = addressId;
    this.price = 0d;
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
