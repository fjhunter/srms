package com.srms.repository;

import com.srms.domain.Lehrer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Lehrer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LehrerRepository extends JpaRepository<Lehrer,Long> {

}
