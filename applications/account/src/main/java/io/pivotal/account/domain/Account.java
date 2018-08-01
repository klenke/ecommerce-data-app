package io.pivotal.account.domain;

import javax.persistence.*;
import java.util.Set;

@Table(name = "accounts")
@Entity
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column
  private String firstName;

  @Column
  private String lastName;

  @Column
  private String email;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "account_id")
  private Set<Address> addresses;


  public Account(){}

  public Account(String firstName, String lastName, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  public Account(String firstName, String lastName, String email, Set<Address> addresses) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.addresses = addresses;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Set<Address> getAddresses() {
    return addresses;
  }

  public void setAddresses(Set<Address> addresses) {
    this.addresses = addresses;
  }

}
