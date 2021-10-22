package frnz.domain;

import static org.assertj.core.api.Assertions.assertThat;

import frnz.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GangTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gang.class);
        Gang gang1 = new Gang();
        gang1.setId("id1");
        Gang gang2 = new Gang();
        gang2.setId(gang1.getId());
        assertThat(gang1).isEqualTo(gang2);
        gang2.setId("id2");
        assertThat(gang1).isNotEqualTo(gang2);
        gang1.setId(null);
        assertThat(gang1).isNotEqualTo(gang2);
    }
}
