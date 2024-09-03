package id.co.kusumakazu.service.impl;

import id.co.kusumakazu.domain.AiSession;
import id.co.kusumakazu.repository.AiSessionRepository;
import id.co.kusumakazu.service.AiSessionService;
import id.co.kusumakazu.service.dto.AiSessionDTO;
import id.co.kusumakazu.service.mapper.AiSessionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link id.co.kusumakazu.domain.AiSession}.
 */
@Service
@Transactional
public class AiSessionServiceImpl implements AiSessionService {

    private static final Logger log = LoggerFactory.getLogger(AiSessionServiceImpl.class);

    private final AiSessionRepository aiSessionRepository;

    private final AiSessionMapper aiSessionMapper;

    public AiSessionServiceImpl(AiSessionRepository aiSessionRepository, AiSessionMapper aiSessionMapper) {
        this.aiSessionRepository = aiSessionRepository;
        this.aiSessionMapper = aiSessionMapper;
    }

    @Override
    public AiSessionDTO save(AiSessionDTO aiSessionDTO) {
        log.debug("Request to save AiSession : {}", aiSessionDTO);
        AiSession aiSession = aiSessionMapper.toEntity(aiSessionDTO);
        aiSession = aiSessionRepository.save(aiSession);
        return aiSessionMapper.toDto(aiSession);
    }

    @Override
    public AiSessionDTO update(AiSessionDTO aiSessionDTO) {
        log.debug("Request to update AiSession : {}", aiSessionDTO);
        AiSession aiSession = aiSessionMapper.toEntity(aiSessionDTO);
        aiSession = aiSessionRepository.save(aiSession);
        return aiSessionMapper.toDto(aiSession);
    }

    @Override
    public Optional<AiSessionDTO> partialUpdate(AiSessionDTO aiSessionDTO) {
        log.debug("Request to partially update AiSession : {}", aiSessionDTO);

        return aiSessionRepository
            .findById(aiSessionDTO.getId())
            .map(existingAiSession -> {
                aiSessionMapper.partialUpdate(existingAiSession, aiSessionDTO);

                return existingAiSession;
            })
            .map(aiSessionRepository::save)
            .map(aiSessionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AiSessionDTO> findAll() {
        log.debug("Request to get all AiSessions");
        return aiSessionRepository.findAll().stream().map(aiSessionMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AiSessionDTO> findOne(Long id) {
        log.debug("Request to get AiSession : {}", id);
        return aiSessionRepository.findById(id).map(aiSessionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AiSession : {}", id);
        aiSessionRepository.deleteById(id);
    }
}
