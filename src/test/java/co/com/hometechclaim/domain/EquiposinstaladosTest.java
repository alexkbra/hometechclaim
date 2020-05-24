package co.com.hometechclaim.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.com.hometechclaim.web.rest.TestUtil;

public class EquiposinstaladosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Equiposinstalados.class);
        Equiposinstalados equiposinstalados1 = new Equiposinstalados();
        equiposinstalados1.setId(1L);
        Equiposinstalados equiposinstalados2 = new Equiposinstalados();
        equiposinstalados2.setId(equiposinstalados1.getId());
        assertThat(equiposinstalados1).isEqualTo(equiposinstalados2);
        equiposinstalados2.setId(2L);
        assertThat(equiposinstalados1).isNotEqualTo(equiposinstalados2);
        equiposinstalados1.setId(null);
        assertThat(equiposinstalados1).isNotEqualTo(equiposinstalados2);
    }
}
