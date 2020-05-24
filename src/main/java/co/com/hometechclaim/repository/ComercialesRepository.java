package co.com.hometechclaim.repository;

import co.com.hometechclaim.domain.Comerciales;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Comerciales entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComercialesRepository extends JpaRepository<Comerciales, Long> {

}
