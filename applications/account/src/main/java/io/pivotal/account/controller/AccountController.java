package io.pivotal.account.controller;

import io.pivotal.account.domain.Account;
import io.pivotal.account.domain.Address;
import io.pivotal.account.repository.AccountRepository;
import io.pivotal.account.repository.AddressRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

  private AccountRepository accountRepository;

  private AddressRepository addressRepository;

  public AccountController(AccountRepository accountRepository, AddressRepository addressRepository) {
    this.accountRepository = accountRepository;
    this.addressRepository = addressRepository;
  }

  @PostMapping()
  public ResponseEntity<Account> create(
      @RequestParam("firstName") String firstName,
      @RequestParam("lastName") String lastName,
      @RequestParam("email") String email){


    Account account = accountRepository.save(new Account(firstName, lastName, email));
    if(account != null){
      return new ResponseEntity<>(account, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping()
  public ResponseEntity<Account> update(
      @RequestParam("id") Long id,
      @RequestParam(value = "firstName", required = false) String firstName,
      @RequestParam(value = "lastName", required = false) String lastName,
      @RequestParam(value = "email", required = false) String email){

    Account account = accountRepository.findOne(id);
    if(account == null){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    if(!firstName.isEmpty()){
      account.setFirstName(firstName);
    }
    if(!lastName.isEmpty()){
      account.setLastName(lastName);
    }
    if(!email.isEmpty()){
      account.setEmail(email);
    }
    return new ResponseEntity<>(accountRepository.save(account), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public Account account(@PathVariable("id") Long id){
    return accountRepository.findOne(id);
  }

  @GetMapping("/{id}/address")
  public ResponseEntity<List<Address>> getAddresses(@PathVariable("id")Long id){
    List<Address> resp = addressRepository.findByAccountId(id);
    if(resp != null){
      return new ResponseEntity<>(resp, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public String deleteAccount(@PathVariable("id") String id){
    try{
      accountRepository.delete(Long.parseLong(id));
    } catch (Exception e){
      return "Error";
    }
    return "Done";
  }

  @GetMapping()
  public List<Account> accounts(){
    List<Account> accounts = new ArrayList<>();
    accountRepository.findAll().forEach(accounts::add);
    return accounts;
  }
}