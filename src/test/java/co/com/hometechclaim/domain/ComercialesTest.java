package co.com.hometechclaim.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.com.hometechclaim.web.rest.TestUtil;

public class ComercialesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comerciales.class);
        Comerciales comerciales1 = new Comerciales();
        comerciales1.setId(1L);
        Comerciales comerciales2 = new Comerciales();
        comerciales2.setId(comerciales1.getId());
        assertThat(comerciales1).isEqualTo(comerciales2);
        comerciales2.setId(2L);
        assertThat(comerciales1).isNotEqualTo(comerciales2);
        comerciales1.setId(null);
        assertThat(comerciales1).isNotEqualTo(comerciales2);
    }
}
