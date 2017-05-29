package com.srms.repository;

import com.srms.domain.Fehlzeiten;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Fehlzeiten entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FehlzeitenRepository extends JpaRepository<Fehlzeiten,Long> {

}
