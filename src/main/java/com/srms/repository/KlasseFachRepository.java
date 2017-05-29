package com.srms.repository;

import com.srms.domain.KlasseFach;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the KlasseFach entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KlasseFachRepository extends JpaRepository<KlasseFach,Long> {

}
