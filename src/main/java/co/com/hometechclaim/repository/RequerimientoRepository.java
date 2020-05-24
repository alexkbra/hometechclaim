package co.com.hometechclaim.repository;

import co.com.hometechclaim.domain.Requerimiento;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Requerimiento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequerimientoRepository extends JpaRepository<Requerimiento, Long> {

}
