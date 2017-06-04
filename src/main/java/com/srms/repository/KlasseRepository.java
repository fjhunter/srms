package com.srms.repository;

import com.srms.domain.Klasse;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Klasse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KlasseRepository extends JpaRepository<Klasse,Long> {
    List<Klasse> findByLehrerId(Long lehrerId);
}
