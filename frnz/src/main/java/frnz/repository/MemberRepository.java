package frnz.repository;

import frnz.domain.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Member entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MemberRepository extends MongoRepository<Member, String> {}
