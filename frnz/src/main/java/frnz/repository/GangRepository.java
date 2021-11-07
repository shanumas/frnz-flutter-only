package frnz.repository;

import frnz.domain.Gang;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Gang entity.
 */
@Repository
public interface GangRepository extends MongoRepository<Gang, String> {
    @Query("{}")
    Page<Gang> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Gang> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Gang> findOneWithEagerRelationships(String id);
}
