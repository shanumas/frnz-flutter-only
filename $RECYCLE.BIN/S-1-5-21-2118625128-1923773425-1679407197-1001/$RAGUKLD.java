package com.kompi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kompi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MemberStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MemberStatus.class);
        MemberStatus memberStatus1 = new MemberStatus();
        memberStatus1.setId(1L);
        MemberStatus memberStatus2 = new MemberStatus();
        memberStatus2.setId(memberStatus1.getId());
        assertThat(memberStatus1).isEqualTo(memberStatus2);
        memberStatus2.setId(2L);
        assertThat(memberStatus1).isNotEqualTo(memberStatus2);
        memberStatus1.setId(null);
        assertThat(memberStatus1).isNotEqualTo(memberStatus2);
    }
}
