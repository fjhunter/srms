package com.srms.repository;

import com.srms.domain.Schulform;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Schulform entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SchulformRepository extends JpaRepository<Schulform,Long> {

}
