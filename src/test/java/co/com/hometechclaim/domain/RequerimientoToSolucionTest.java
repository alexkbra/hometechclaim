package co.com.hometechclaim.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.com.hometechclaim.web.rest.TestUtil;

public class RequerimientoToSolucionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequerimientoToSolucion.class);
        RequerimientoToSolucion requerimientoToSolucion1 = new RequerimientoToSolucion();
        requerimientoToSolucion1.setId(1L);
        RequerimientoToSolucion requerimientoToSolucion2 = new RequerimientoToSolucion();
        requerimientoToSolucion2.setId(requerimientoToSolucion1.getId());
        assertThat(requerimientoToSolucion1).isEqualTo(requerimientoToSolucion2);
        requerimientoToSolucion2.setId(2L);
        assertThat(requerimientoToSolucion1).isNotEqualTo(requerimientoToSolucion2);
        requerimientoToSolucion1.setId(null);
        assertThat(requerimientoToSolucion1).isNotEqualTo(requerimientoToSolucion2);
    }
}
