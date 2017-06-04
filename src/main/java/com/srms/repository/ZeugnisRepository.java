package com.srms.repository;

import com.srms.domain.Zeugnis;
import com.srms.domain.enumeration.Zeugnis_typ;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.ZonedDateTime;


/**
 * Spring Data JPA repository for the Zeugnis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZeugnisRepository extends JpaRepository<Zeugnis,Long> {
    Zeugnis findBySchuelerId(Long id);
    Zeugnis findBySchuelerIdAndAndDatumAndAndZeugnistyp(Long schulerId, ZonedDateTime datum, Zeugnis_typ zeugnisTyp);
}
