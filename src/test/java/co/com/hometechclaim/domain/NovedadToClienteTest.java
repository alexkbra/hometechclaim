package co.com.hometechclaim.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.com.hometechclaim.web.rest.TestUtil;

public class NovedadToClienteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NovedadToCliente.class);
        NovedadToCliente novedadToCliente1 = new NovedadToCliente();
        novedadToCliente1.setId(1L);
        NovedadToCliente novedadToCliente2 = new NovedadToCliente();
        novedadToCliente2.setId(novedadToCliente1.getId());
        assertThat(novedadToCliente1).isEqualTo(novedadToCliente2);
        novedadToCliente2.setId(2L);
        assertThat(novedadToCliente1).isNotEqualTo(novedadToCliente2);
        novedadToCliente1.setId(null);
        assertThat(novedadToCliente1).isNotEqualTo(novedadToCliente2);
    }
}
