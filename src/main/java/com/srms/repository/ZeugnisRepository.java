package com.srms.repository;

import com.srms.domain.Zeugnis;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Zeugnis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZeugnisRepository extends JpaRepository<Zeugnis,Long> {

}
