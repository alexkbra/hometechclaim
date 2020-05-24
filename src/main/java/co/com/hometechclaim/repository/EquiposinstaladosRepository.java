package co.com.hometechclaim.repository;

import co.com.hometechclaim.domain.Equiposinstalados;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Equiposinstalados entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EquiposinstaladosRepository extends JpaRepository<Equiposinstalados, Long> {

}
