package id.co.kusumakazu.service.mapper;

import static id.co.kusumakazu.domain.SessionMessageAsserts.*;
import static id.co.kusumakazu.domain.SessionMessageTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SessionMessageMapperTest {

    private SessionMessageMapper sessionMessageMapper;

    @BeforeEach
    void setUp() {
        sessionMessageMapper = new SessionMessageMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSessionMessageSample1();
        var actual = sessionMessageMapper.toEntity(sessionMessageMapper.toDto(expected));
        assertSessionMessageAllPropertiesEquals(expected, actual);
    }
}
