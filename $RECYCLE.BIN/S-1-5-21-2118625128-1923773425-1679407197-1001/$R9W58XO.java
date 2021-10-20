package com.kompi.repository;

import com.kompi.domain.MemberStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MemberStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MemberStatusRepository extends JpaRepository<MemberStatus, Long> {}
