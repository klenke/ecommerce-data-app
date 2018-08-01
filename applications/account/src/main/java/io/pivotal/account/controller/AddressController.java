package io.pivotal.account.controller;

import io.pivotal.account.domain.Account;
import io.pivotal.account.domain.Address;
import io.pivotal.account.repository.AccountRepository;
import io.pivotal.account.repository.AddressRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

  private AddressRepository addressRepository;
  private AccountRepository accountRepository;

  public AddressController(AddressRepository addressRepository, AccountRepository accountRepository) {
    this.addressRepository = addressRepository;
    this.accountRepository = accountRepository;
  }

  /*@GetMapping()
  public List<Address> address(){
    List<Address> addresses = new ArrayList<>();
    addressRepository.findAll().forEach(addresses::add);
    return addresses;
  }*/

  @GetMapping("/{id}")
  public Address address(@PathVariable("id") Long id){
    return addressRepository.findOne(id);
  }

  @PostMapping()
  public Address create(
      @RequestParam("street") String street,
      @RequestParam(value = "apt", required = false)String apt,
      @RequestParam("city") String city,
      @RequestParam("state") String state,
      @RequestParam("zip") String zip,
      @RequestParam("country") String country,
      @RequestParam("account_id") Long accountId){


    Account account = accountRepository.findOne(accountId);

    return addressRepository.save(new Address(street, apt, city, state, zip, country, account));

  }

  @PutMapping()
  public Address update(
      @RequestParam("address_id") Long addressId,
      @RequestParam(value = "street", required = false) String street,
      @RequestParam(value = "apt", required = false)String apt,
      @RequestParam(value = "city",required = false) String city,
      @RequestParam(value = "state", required = false) String state,
      @RequestParam(value = "zip", required = false) String zip,
      @RequestParam(value = "country", required = false) String country,
      @RequestParam(value = "account_id", required = false) Long accountId){

    Address address = addressRepository.findOne(addressId);
    if(!street.isEmpty()){
      address.setStreet(street);
    }
    if(!apt.isEmpty()){
      address.setApt(apt);
    }
    if(!city.isEmpty()) {
      address.setCity(city);
    }
    if(!state.isEmpty()) {
      address.setState(state);
    }
    if(!zip.isEmpty()) {
      address.setZip(zip);
    }
    if(!country.isEmpty()){
      address.setCountry(country);
    }
    if(accountId != null) {
      address.setAccount(accountRepository.findOne(accountId));
    }
    return addressRepository.save(address);

  }

  @DeleteMapping("/{id}")
  public String deleteAddress(@PathVariable("id") Long id){
    try{
      addressRepository.delete(id);
    } catch (Exception e){
      return "Error deleting address";
    }

    return "Address Deleted";
  }
}

