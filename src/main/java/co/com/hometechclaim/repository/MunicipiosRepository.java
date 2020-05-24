package co.com.hometechclaim.repository;

import co.com.hometechclaim.domain.Municipios;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Municipios entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MunicipiosRepository extends JpaRepository<Municipios, Long> {

}
