package id.co.kusumakazu.domain;

import static id.co.kusumakazu.domain.SessionMessageTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import id.co.kusumakazu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SessionMessageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SessionMessage.class);
        SessionMessage sessionMessage1 = getSessionMessageSample1();
        SessionMessage sessionMessage2 = new SessionMessage();
        assertThat(sessionMessage1).isNotEqualTo(sessionMessage2);

        sessionMessage2.setId(sessionMessage1.getId());
        assertThat(sessionMessage1).isEqualTo(sessionMessage2);

        sessionMessage2 = getSessionMessageSample2();
        assertThat(sessionMessage1).isNotEqualTo(sessionMessage2);
    }
}
