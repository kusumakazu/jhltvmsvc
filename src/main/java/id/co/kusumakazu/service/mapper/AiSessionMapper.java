package id.co.kusumakazu.service.mapper;

import id.co.kusumakazu.domain.AiSession;
import id.co.kusumakazu.service.dto.AiSessionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AiSession} and its DTO {@link AiSessionDTO}.
 */
@Mapper(componentModel = "spring")
public interface AiSessionMapper extends EntityMapper<AiSessionDTO, AiSession> {}
