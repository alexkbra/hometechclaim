package co.com.hometechclaim.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.com.hometechclaim.web.rest.TestUtil;

public class EquiposcotizadosToEquiposTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EquiposcotizadosToEquipos.class);
        EquiposcotizadosToEquipos equiposcotizadosToEquipos1 = new EquiposcotizadosToEquipos();
        equiposcotizadosToEquipos1.setId(1L);
        EquiposcotizadosToEquipos equiposcotizadosToEquipos2 = new EquiposcotizadosToEquipos();
        equiposcotizadosToEquipos2.setId(equiposcotizadosToEquipos1.getId());
        assertThat(equiposcotizadosToEquipos1).isEqualTo(equiposcotizadosToEquipos2);
        equiposcotizadosToEquipos2.setId(2L);
        assertThat(equiposcotizadosToEquipos1).isNotEqualTo(equiposcotizadosToEquipos2);
        equiposcotizadosToEquipos1.setId(null);
        assertThat(equiposcotizadosToEquipos1).isNotEqualTo(equiposcotizadosToEquipos2);
    }
}
