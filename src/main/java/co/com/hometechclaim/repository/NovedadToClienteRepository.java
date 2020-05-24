package co.com.hometechclaim.repository;

import co.com.hometechclaim.domain.NovedadToCliente;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the NovedadToCliente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NovedadToClienteRepository extends JpaRepository<NovedadToCliente, Long> {

}
