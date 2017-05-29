package com.srms.repository;

import com.srms.domain.Fach;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Fach entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FachRepository extends JpaRepository<Fach,Long> {

}
