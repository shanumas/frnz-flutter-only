package frnz.repository;

import frnz.domain.Moderator;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Moderator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModeratorRepository extends MongoRepository<Moderator, String> {}
