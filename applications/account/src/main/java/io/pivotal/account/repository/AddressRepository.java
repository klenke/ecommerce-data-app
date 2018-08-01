package io.pivotal.account.repository;

import io.pivotal.account.domain.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {
  public List<Address> findByAccountId(Long address_id);
}
