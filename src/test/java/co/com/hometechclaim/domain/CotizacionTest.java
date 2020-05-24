package co.com.hometechclaim.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.com.hometechclaim.web.rest.TestUtil;

public class CotizacionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cotizacion.class);
        Cotizacion cotizacion1 = new Cotizacion();
        cotizacion1.setId(1L);
        Cotizacion cotizacion2 = new Cotizacion();
        cotizacion2.setId(cotizacion1.getId());
        assertThat(cotizacion1).isEqualTo(cotizacion2);
        cotizacion2.setId(2L);
        assertThat(cotizacion1).isNotEqualTo(cotizacion2);
        cotizacion1.setId(null);
        assertThat(cotizacion1).isNotEqualTo(cotizacion2);
    }
}
