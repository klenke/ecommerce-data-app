package io.pivotal.order.repository;

import io.pivotal.order.domain.OrderLineItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineItemRepository extends CrudRepository<OrderLineItem, Long> {
  List<OrderLineItem> findByOrderOrderNumber(Long order_number);

  List<OrderLineItem> findByShipmentId(Long shipmentId);
}
