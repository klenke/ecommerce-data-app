package io.pivotal.shipment.service;

import io.pivotal.shipment.ShipmentRepository;
import io.pivotal.shipment.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShipmentService {

  private OrderLineItemService orderLineItemService;

  private ProductService productService;

  private ShipmentRepository shipmentRepository;

  public ShipmentService(OrderLineItemService orderLineItemService, ProductService productService, ShipmentRepository shipmentRepository) {
    this.orderLineItemService = orderLineItemService;
    this.productService = productService;
    this.shipmentRepository = shipmentRepository;
  }

  public List<ShipmentInfo> getShipmentsForAccount(Long accountId){
    List<ShipmentInfo> shipmentInfoList = new ArrayList<>();

    List<Shipment> shipmentList = shipmentRepository.findByAccountIdOrderByDeliveredAsc(accountId);

    for(Shipment s: shipmentList){

      ShipmentInfo shipmentInfo = new ShipmentInfo();

      shipmentInfo.setShipped(s.getShipped());
      shipmentInfo.setDelivered(s.getDelivered());

      OrderLineItemList orderLineItemList = orderLineItemService.getOrderLineItemsByShipmentId(s.getId());

      Long orderNumber = null;

      List<InfoLineItems> infoLineItems = new ArrayList<>();
      String name;
      for(OrderLineItem o: orderLineItemList.getItems()){
        orderNumber = o.getOrder().getOrderNumber();
        //iterate through order line items
        name = productService.getProductName(o.getProductId());
        infoLineItems.add(new InfoLineItems(name, o.getQuantity()));
      }
      shipmentInfo.setItems(infoLineItems);
      shipmentInfo.setOrderNumber(orderNumber);

      shipmentInfoList.add(shipmentInfo);
    }

    return shipmentInfoList;
  }
}
