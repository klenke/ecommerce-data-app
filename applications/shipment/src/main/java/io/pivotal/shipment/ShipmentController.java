package io.pivotal.shipment;


import io.pivotal.shipment.domain.Shipment;
import io.pivotal.shipment.domain.ShipmentInfo;
import io.pivotal.shipment.service.ShipmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/shipments")
public class ShipmentController {

  private ShipmentRepository shipmentRepository;

  private ShipmentService shipmentService;

  public ShipmentController(ShipmentRepository shipmentRepository, ShipmentService shipmentService) {
    this.shipmentRepository = shipmentRepository;
    this.shipmentService = shipmentService;
  }

  @PostMapping
  public ResponseEntity<Shipment> create(
      @RequestParam("accountId") Long accountId,
      @RequestParam("addressId") Long addressId
  ){
    Shipment resp = shipmentRepository.save(new Shipment(accountId, addressId, new Date(), new Date()));

    if(resp!= null){
      return new ResponseEntity<>(resp, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping
  ResponseEntity<Shipment> update(
      @RequestParam("id") Long id,
      @RequestParam(value = "accountId", required = false) Long accountId,
      @RequestParam(value = "addressId", required = false) Long addressId,
      @RequestParam(value = "shipped", required = false) Date shipped,
      @RequestParam(value = "delivered", required = false) Date delivered
  ) {
    Shipment resp = shipmentRepository.findOne(id);
    if(accountId != null){
      resp.setAccountId(accountId);
    }
    if(addressId != null){
      resp.setAddressId(addressId);
    }
    if(shipped != null){
      resp.setShipped(shipped);
    }
    if(delivered != null){
      resp.setDelivered(delivered);
    }
    return new ResponseEntity<>(shipmentRepository.save(resp), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  ResponseEntity<Shipment> getById(@PathVariable("id") Long id){
    Shipment resp = shipmentRepository.findOne(id);
    if(resp != null){
      return new ResponseEntity<>(resp, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/{id}/shipped")
  Date getShippedById(@PathVariable("id") Long id){
    Shipment resp = shipmentRepository.findOne(id);
    if(resp != null){
      return resp.getShipped();
    } else {
      return null;
    }
  }

  @GetMapping("/{id}/delivered")
  Date getDeliveredById(@PathVariable("id")Long id){
    Shipment resp = shipmentRepository.findOne(id);
    if(resp != null){
      return resp.getDelivered();
    } else {
      return null;
    }
  }

  @GetMapping()
  List<ShipmentInfo> getShipmentsForAccount(@RequestParam("accountId")Long accountId){
    return shipmentService.getShipmentsForAccount(accountId);
  }

  @DeleteMapping("/{id}")
  ResponseEntity delete(@PathVariable("id")Long id){
    try{
      shipmentRepository.delete(id);
    } catch (Exception e){
      return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }
    return new ResponseEntity(HttpStatus.OK);
  }
}
