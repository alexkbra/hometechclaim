package co.com.hometechclaim.repository;

import co.com.hometechclaim.domain.Solucion;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Solucion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SolucionRepository extends JpaRepository<Solucion, Long> {

}
