package com.srms.repository;

import com.srms.domain.KlasseFach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the KlasseFach entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KlasseFachRepository extends JpaRepository<KlasseFach,Long> {
    List<KlasseFach> findByLehrerId(Long lehrerId);
}
