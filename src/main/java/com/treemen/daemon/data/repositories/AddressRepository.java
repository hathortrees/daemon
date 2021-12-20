package com.treemen.daemon.data.repositories;

import com.treemen.daemon.data.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Integer> {

   Address findTopByTaken(boolean taken);
}
