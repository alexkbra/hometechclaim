package co.com.hometechclaim.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.com.hometechclaim.web.rest.TestUtil;

public class SolucionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Solucion.class);
        Solucion solucion1 = new Solucion();
        solucion1.setId(1L);
        Solucion solucion2 = new Solucion();
        solucion2.setId(solucion1.getId());
        assertThat(solucion1).isEqualTo(solucion2);
        solucion2.setId(2L);
        assertThat(solucion1).isNotEqualTo(solucion2);
        solucion1.setId(null);
        assertThat(solucion1).isNotEqualTo(solucion2);
    }
}
