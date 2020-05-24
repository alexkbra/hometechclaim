package co.com.hometechclaim.repository;

import co.com.hometechclaim.domain.TipoEquipo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TipoEquipo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoEquipoRepository extends JpaRepository<TipoEquipo, Long> {

}
