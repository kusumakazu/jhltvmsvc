package id.co.kusumakazu.service.mapper;

import id.co.kusumakazu.domain.SessionMessage;
import id.co.kusumakazu.service.dto.SessionMessageDTO;

import java.util.List;

public record SessionMessageMapperImpl() implements SessionMessageMapper {
    @Override
    public SessionMessage toEntity(SessionMessageDTO dto) {
        return null;
    }

    @Override
    public SessionMessageDTO toDto(SessionMessage entity) {
        return null;
    }

    @Override
    public List<SessionMessage> toEntity(List<SessionMessageDTO> dtoList) {
        return List.of();
    }

    @Override
    public List<SessionMessageDTO> toDto(List<SessionMessage> entityList) {
        return List.of();
    }

    @Override
    public void partialUpdate(SessionMessage entity, SessionMessageDTO dto) {

    }
}
