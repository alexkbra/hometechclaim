package co.com.hometechclaim.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.com.hometechclaim.web.rest.TestUtil;

public class TipoEquipoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoEquipo.class);
        TipoEquipo tipoEquipo1 = new TipoEquipo();
        tipoEquipo1.setId(1L);
        TipoEquipo tipoEquipo2 = new TipoEquipo();
        tipoEquipo2.setId(tipoEquipo1.getId());
        assertThat(tipoEquipo1).isEqualTo(tipoEquipo2);
        tipoEquipo2.setId(2L);
        assertThat(tipoEquipo1).isNotEqualTo(tipoEquipo2);
        tipoEquipo1.setId(null);
        assertThat(tipoEquipo1).isNotEqualTo(tipoEquipo2);
    }
}
