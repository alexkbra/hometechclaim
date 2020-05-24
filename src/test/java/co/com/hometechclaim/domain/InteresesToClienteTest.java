package co.com.hometechclaim.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.com.hometechclaim.web.rest.TestUtil;

public class InteresesToClienteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InteresesToCliente.class);
        InteresesToCliente interesesToCliente1 = new InteresesToCliente();
        interesesToCliente1.setId(1L);
        InteresesToCliente interesesToCliente2 = new InteresesToCliente();
        interesesToCliente2.setId(interesesToCliente1.getId());
        assertThat(interesesToCliente1).isEqualTo(interesesToCliente2);
        interesesToCliente2.setId(2L);
        assertThat(interesesToCliente1).isNotEqualTo(interesesToCliente2);
        interesesToCliente1.setId(null);
        assertThat(interesesToCliente1).isNotEqualTo(interesesToCliente2);
    }
}
