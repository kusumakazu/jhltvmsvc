package id.co.kusumakazu.service.mapper;

import id.co.kusumakazu.domain.AiSession;
import id.co.kusumakazu.service.dto.AiSessionDTO;

import java.util.List;

public record AiSessionMapperImpl() implements AiSessionMapper {
    @Override
    public AiSession toEntity(AiSessionDTO dto) {
        return null;
    }

    @Override
    public AiSessionDTO toDto(AiSession entity) {
        return null;
    }

    @Override
    public List<AiSession> toEntity(List<AiSessionDTO> dtoList) {
        return List.of();
    }

    @Override
    public List<AiSessionDTO> toDto(List<AiSession> entityList) {
        return List.of();
    }

    @Override
    public void partialUpdate(AiSession entity, AiSessionDTO dto) {

    }
}
