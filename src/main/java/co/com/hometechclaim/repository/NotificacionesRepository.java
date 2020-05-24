package co.com.hometechclaim.repository;

import co.com.hometechclaim.domain.Notificaciones;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Notificaciones entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificacionesRepository extends JpaRepository<Notificaciones, Long> {

}
