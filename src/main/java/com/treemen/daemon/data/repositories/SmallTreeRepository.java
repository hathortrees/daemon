package com.treemen.daemon.data.repositories;

import com.treemen.daemon.data.entities.SmallTree;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SmallTreeRepository extends CrudRepository<SmallTree, Integer> {

   @Query(nativeQuery = true, value = "SELECT * FROM mai_tree_small_trees t WHERE t.taken = 0 order by RAND() LIMIT :count")
   List<SmallTree> findNotTaken(@Param("count") int count);

   List<SmallTree> findByMintIsNullAndTakenIsTrue();

}
