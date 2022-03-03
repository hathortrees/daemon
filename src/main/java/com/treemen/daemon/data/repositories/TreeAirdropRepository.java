package com.treemen.daemon.data.repositories;

import com.treemen.daemon.data.entities.Airdrop;
import com.treemen.daemon.data.entities.TreeAirdrop;
import org.springframework.data.repository.CrudRepository;

public interface TreeAirdropRepository extends CrudRepository<TreeAirdrop, Integer> {
}
