package io.pivotal.shipment;

import io.pivotal.shipment.domain.Shipment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentRepository extends CrudRepository<Shipment, Long> {
  public List<Shipment> findByAccountIdOrderByDeliveredAsc(Long account_id);
}
