package id.co.kusumakazu.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import id.co.kusumakazu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SessionMessageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SessionMessageDTO.class);
        SessionMessageDTO sessionMessageDTO1 = new SessionMessageDTO();
        sessionMessageDTO1.setId(1L);
        SessionMessageDTO sessionMessageDTO2 = new SessionMessageDTO();
        assertThat(sessionMessageDTO1).isNotEqualTo(sessionMessageDTO2);
        sessionMessageDTO2.setId(sessionMessageDTO1.getId());
        assertThat(sessionMessageDTO1).isEqualTo(sessionMessageDTO2);
        sessionMessageDTO2.setId(2L);
        assertThat(sessionMessageDTO1).isNotEqualTo(sessionMessageDTO2);
        sessionMessageDTO1.setId(null);
        assertThat(sessionMessageDTO1).isNotEqualTo(sessionMessageDTO2);
    }
}
