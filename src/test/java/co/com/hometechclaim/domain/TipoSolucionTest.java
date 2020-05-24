package co.com.hometechclaim.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.com.hometechclaim.web.rest.TestUtil;

public class TipoSolucionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoSolucion.class);
        TipoSolucion tipoSolucion1 = new TipoSolucion();
        tipoSolucion1.setId(1L);
        TipoSolucion tipoSolucion2 = new TipoSolucion();
        tipoSolucion2.setId(tipoSolucion1.getId());
        assertThat(tipoSolucion1).isEqualTo(tipoSolucion2);
        tipoSolucion2.setId(2L);
        assertThat(tipoSolucion1).isNotEqualTo(tipoSolucion2);
        tipoSolucion1.setId(null);
        assertThat(tipoSolucion1).isNotEqualTo(tipoSolucion2);
    }
}
