package io.pivotal.account.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "address")
@Entity
public class Address implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column
  private String street;

  @Column
  private String apt;

  @Column
  private String city;

  @Column
  private String state;

  @Column
  private String zip;

  @Column
  private String country;

  @ManyToOne
  @JoinColumn(name = "account_id")
  @JsonIgnoreProperties({"lastName", "email", "addresses"})
  private Account account;


  public Address(){}

  public Address(String street, String apt, String city, String state, String zip, String country) {
    this.street = street;
    this.apt = apt;
    this.city = city;
    this.state = state;
    this.zip = zip;
    this.country = country;
  }

  public Address(String street, String apt, String city, String state, String zip, String country, Account account) {
    this.street = street;
    this.apt = apt;
    this.city = city;
    this.state = state;
    this.zip = zip;
    this.country = country;
    this.account = account;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getApt() {
    return apt;
  }

  public void setApt(String apt) {
    this.apt = apt;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }
}
