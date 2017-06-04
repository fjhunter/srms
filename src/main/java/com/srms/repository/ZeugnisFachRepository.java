package com.srms.repository;

import com.srms.domain.ZeugnisFach;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ZeugnisFach entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZeugnisFachRepository extends JpaRepository<ZeugnisFach,Long> {
    ZeugnisFach findByZeugnisIdAndFachId(Long zeugnisId, Long fachId);
}
