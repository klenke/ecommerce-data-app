package io.pivotal.order.repository;

import io.pivotal.order.domain.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
  public List<Order> findByAccountIdOrderByOrderDateAsc(Long account_id);

  public Order findByAccountIdAndOrderNumber(Long account_id, Long order_number);

}
