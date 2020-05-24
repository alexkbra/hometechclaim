package co.com.hometechclaim.repository;

import co.com.hometechclaim.domain.Cotizacion;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Cotizacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CotizacionRepository extends JpaRepository<Cotizacion, Long> {

}
