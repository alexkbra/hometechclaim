package co.com.hometechclaim.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.com.hometechclaim.web.rest.TestUtil;

public class RequerimientoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Requerimiento.class);
        Requerimiento requerimiento1 = new Requerimiento();
        requerimiento1.setId(1L);
        Requerimiento requerimiento2 = new Requerimiento();
        requerimiento2.setId(requerimiento1.getId());
        assertThat(requerimiento1).isEqualTo(requerimiento2);
        requerimiento2.setId(2L);
        assertThat(requerimiento1).isNotEqualTo(requerimiento2);
        requerimiento1.setId(null);
        assertThat(requerimiento1).isNotEqualTo(requerimiento2);
    }
}
