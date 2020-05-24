package co.com.hometechclaim.repository;

import co.com.hometechclaim.domain.TipoSolucion;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TipoSolucion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoSolucionRepository extends JpaRepository<TipoSolucion, Long> {

}
