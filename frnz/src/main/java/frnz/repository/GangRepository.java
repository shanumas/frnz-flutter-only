package frnz.repository;

import frnz.domain.Gang;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Gang entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GangRepository extends MongoRepository<Gang, String> {}
