package co.com.hometechclaim.repository;

import co.com.hometechclaim.domain.EquiposcotizadosToEquipos;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EquiposcotizadosToEquipos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EquiposcotizadosToEquiposRepository extends JpaRepository<EquiposcotizadosToEquipos, Long> {

}
