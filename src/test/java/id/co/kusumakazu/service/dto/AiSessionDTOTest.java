package id.co.kusumakazu.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import id.co.kusumakazu.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AiSessionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AiSessionDTO.class);
        AiSessionDTO aiSessionDTO1 = new AiSessionDTO();
        aiSessionDTO1.setId(1L);
        AiSessionDTO aiSessionDTO2 = new AiSessionDTO();
        assertThat(aiSessionDTO1).isNotEqualTo(aiSessionDTO2);
        aiSessionDTO2.setId(aiSessionDTO1.getId());
        assertThat(aiSessionDTO1).isEqualTo(aiSessionDTO2);
        aiSessionDTO2.setId(2L);
        assertThat(aiSessionDTO1).isNotEqualTo(aiSessionDTO2);
        aiSessionDTO1.setId(null);
        assertThat(aiSessionDTO1).isNotEqualTo(aiSessionDTO2);
    }
}
