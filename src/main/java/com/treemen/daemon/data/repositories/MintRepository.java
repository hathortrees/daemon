package com.treemen.daemon.data.repositories;

import com.treemen.daemon.data.entities.Mint;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MintRepository extends CrudRepository<Mint, String> {

   List<Mint> getAllByState(int state);

   List<Mint> getAllByStateAndDead(int state, boolean dead);
}
