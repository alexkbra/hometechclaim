package co.com.hometechclaim.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.com.hometechclaim.web.rest.TestUtil;

public class NovedadTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Novedad.class);
        Novedad novedad1 = new Novedad();
        novedad1.setId(1L);
        Novedad novedad2 = new Novedad();
        novedad2.setId(novedad1.getId());
        assertThat(novedad1).isEqualTo(novedad2);
        novedad2.setId(2L);
        assertThat(novedad1).isNotEqualTo(novedad2);
        novedad1.setId(null);
        assertThat(novedad1).isNotEqualTo(novedad2);
    }
}
