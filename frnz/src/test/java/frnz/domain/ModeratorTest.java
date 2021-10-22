package frnz.domain;

import static org.assertj.core.api.Assertions.assertThat;

import frnz.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ModeratorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Moderator.class);
        Moderator moderator1 = new Moderator();
        moderator1.setId("id1");
        Moderator moderator2 = new Moderator();
        moderator2.setId(moderator1.getId());
        assertThat(moderator1).isEqualTo(moderator2);
        moderator2.setId("id2");
        assertThat(moderator1).isNotEqualTo(moderator2);
        moderator1.setId(null);
        assertThat(moderator1).isNotEqualTo(moderator2);
    }
}
