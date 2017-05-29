package com.srms.repository;

import com.srms.domain.Anschrift;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Anschrift entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnschriftRepository extends JpaRepository<Anschrift,Long> {

}
