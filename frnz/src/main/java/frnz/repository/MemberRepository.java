package frnz.repository;

import frnz.domain.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Member entity.
 */
@Repository
public interface MemberRepository extends MongoRepository<Member, String> {
    @Query("{}")
    Page<Member> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Member> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Member> findOneWithEagerRelationships(String id);
}
