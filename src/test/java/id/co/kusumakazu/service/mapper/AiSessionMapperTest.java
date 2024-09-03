package id.co.kusumakazu.service.mapper;

import static id.co.kusumakazu.domain.AiSessionAsserts.*;
import static id.co.kusumakazu.domain.AiSessionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AiSessionMapperTest {

    private AiSessionMapper aiSessionMapper;

    @BeforeEach
    void setUp() {
        aiSessionMapper = new AiSessionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAiSessionSample1();
        var actual = aiSessionMapper.toEntity(aiSessionMapper.toDto(expected));
        assertAiSessionAllPropertiesEquals(expected, actual);
    }
}
