package com.hathor.daemon.data.repositories;

import com.hathor.daemon.data.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Integer> {

   Address findTopByTaken(boolean taken);
}
