package co.com.hometechclaim.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.com.hometechclaim.web.rest.TestUtil;

public class InteresesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Intereses.class);
        Intereses intereses1 = new Intereses();
        intereses1.setId(1L);
        Intereses intereses2 = new Intereses();
        intereses2.setId(intereses1.getId());
        assertThat(intereses1).isEqualTo(intereses2);
        intereses2.setId(2L);
        assertThat(intereses1).isNotEqualTo(intereses2);
        intereses1.setId(null);
        assertThat(intereses1).isNotEqualTo(intereses2);
    }
}
