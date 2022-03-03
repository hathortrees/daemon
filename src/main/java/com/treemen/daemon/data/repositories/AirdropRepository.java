package com.treemen.daemon.data.repositories;

import com.treemen.daemon.data.entities.Address;
import com.treemen.daemon.data.entities.Airdrop;
import org.springframework.data.repository.CrudRepository;

public interface AirdropRepository extends CrudRepository<Airdrop, Integer> {
}
