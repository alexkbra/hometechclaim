package co.com.hometechclaim.repository;

import co.com.hometechclaim.domain.Intereses;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Intereses entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InteresesRepository extends JpaRepository<Intereses, Long> {

}
