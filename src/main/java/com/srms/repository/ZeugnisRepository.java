package com.srms.repository;

import com.srms.domain.Zeugnis;
import com.srms.domain.enumeration.Zeugnis_typ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;


/**
 * Spring Data JPA repository for the Zeugnis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZeugnisRepository extends JpaRepository<Zeugnis,Long> {
    Zeugnis findById(Long id);
    List<Zeugnis> findBySchuelerId(Long id);
    Zeugnis findBySchuelerIdAndAndDatumAndAndZeugnistyp(Long schuelerId, ZonedDateTime datum, Zeugnis_typ zeugnisTyp);
}
