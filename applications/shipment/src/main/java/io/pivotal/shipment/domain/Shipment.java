package io.pivotal.shipment.domain;

import javax.persistence.*;
import java.util.Date;

@Table(name = "shipment")
@Entity
public class Shipment {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column
  private Long accountId;

  @Column
  private Long addressId;

  @Column
  private Date shipped;

  @Column
  private Date delivered;

  public Shipment(){}

  public Shipment(Long accountId, Long addressId, Date shipped, Date delivered) {
    this.accountId = accountId;
    this.addressId = addressId;
    this.shipped = shipped;
    this.delivered = delivered;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public Long getAddressId() {
    return addressId;
  }

  public void setAddressId(Long addressId) {
    this.addressId = addressId;
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
