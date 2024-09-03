package id.co.kusumakazu.service.mapper;

import id.co.kusumakazu.domain.SessionMessage;
import id.co.kusumakazu.service.dto.SessionMessageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SessionMessage} and its DTO {@link SessionMessageDTO}.
 */
@Mapper(componentModel = "spring")
public interface SessionMessageMapper extends EntityMapper<SessionMessageDTO, SessionMessage> {}
