package co.com.hometechclaim.repository;

import co.com.hometechclaim.domain.Novedad;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Novedad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NovedadRepository extends JpaRepository<Novedad, Long> {

}
