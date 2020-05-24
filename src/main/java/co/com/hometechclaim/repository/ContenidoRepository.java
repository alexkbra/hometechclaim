package co.com.hometechclaim.repository;

import co.com.hometechclaim.domain.Contenido;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Contenido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContenidoRepository extends JpaRepository<Contenido, Long> {

}
