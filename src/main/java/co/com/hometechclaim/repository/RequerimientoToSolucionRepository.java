package co.com.hometechclaim.repository;

import co.com.hometechclaim.domain.RequerimientoToSolucion;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RequerimientoToSolucion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequerimientoToSolucionRepository extends JpaRepository<RequerimientoToSolucion, Long> {

}
