package com.srms.repository;

import com.srms.domain.Schueler;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Schueler entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SchuelerRepository extends JpaRepository<Schueler,Long> {

    List<Schueler> findByKlasse_KlasseFachesLehrerId(Long lehrerId);
    List<Schueler> findByKlasse_Id(Long klasseId);
}
