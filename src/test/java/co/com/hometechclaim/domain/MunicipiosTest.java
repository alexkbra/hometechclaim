package co.com.hometechclaim.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.com.hometechclaim.web.rest.TestUtil;

public class MunicipiosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Municipios.class);
        Municipios municipios1 = new Municipios();
        municipios1.setId(1L);
        Municipios municipios2 = new Municipios();
        municipios2.setId(municipios1.getId());
        assertThat(municipios1).isEqualTo(municipios2);
        municipios2.setId(2L);
        assertThat(municipios1).isNotEqualTo(municipios2);
        municipios1.setId(null);
        assertThat(municipios1).isNotEqualTo(municipios2);
    }
}
