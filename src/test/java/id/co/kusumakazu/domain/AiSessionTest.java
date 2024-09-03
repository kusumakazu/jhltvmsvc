package id.co.kusumakazu.domain;

import static id.co.kusumakazu.domain.AiSessionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import id.co.kusumakazu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AiSessionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AiSession.class);
        AiSession aiSession1 = getAiSessionSample1();
        AiSession aiSession2 = new AiSession();
        assertThat(aiSession1).isNotEqualTo(aiSession2);

        aiSession2.setId(aiSession1.getId());
        assertThat(aiSession1).isEqualTo(aiSession2);

        aiSession2 = getAiSessionSample2();
        assertThat(aiSession1).isNotEqualTo(aiSession2);
    }
}
