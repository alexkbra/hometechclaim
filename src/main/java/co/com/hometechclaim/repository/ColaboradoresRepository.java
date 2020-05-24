package co.com.hometechclaim.repository;

import co.com.hometechclaim.domain.Colaboradores;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Colaboradores entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ColaboradoresRepository extends JpaRepository<Colaboradores, Long> {

}
