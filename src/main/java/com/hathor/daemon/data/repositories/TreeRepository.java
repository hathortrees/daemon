package com.hathor.daemon.data.repositories;

import com.hathor.daemon.data.entities.Tree;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TreeRepository extends CrudRepository<Tree, Integer> {

   @Query(nativeQuery = true, value = "SELECT * FROM tree t WHERE t.taken = 0 order by RAND() LIMIT :count")
   List<Tree> findNotTaken(@Param("count") int count);

}
