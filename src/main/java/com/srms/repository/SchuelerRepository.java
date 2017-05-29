package com.srms.repository;

import com.srms.domain.Schueler;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Schueler entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SchuelerRepository extends JpaRepository<Schueler,Long> {

}
