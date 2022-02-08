package com.treemen.daemon.data.repositories;

import com.treemen.daemon.data.entities.SmallTree;
import com.treemen.daemon.data.entities.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CrudRepository<Team, Integer> {
}
