package co.com.hometechclaim.repository;

import co.com.hometechclaim.domain.InteresesToCliente;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the InteresesToCliente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InteresesToClienteRepository extends JpaRepository<InteresesToCliente, Long> {

}
