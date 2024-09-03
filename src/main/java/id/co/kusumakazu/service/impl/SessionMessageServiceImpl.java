package id.co.kusumakazu.service.impl;

import id.co.kusumakazu.domain.SessionMessage;
import id.co.kusumakazu.repository.SessionMessageRepository;
import id.co.kusumakazu.service.SessionMessageService;
import id.co.kusumakazu.service.dto.SessionMessageDTO;
import id.co.kusumakazu.service.mapper.SessionMessageMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link id.co.kusumakazu.domain.SessionMessage}.
 */
@Service
@Transactional
public class SessionMessageServiceImpl implements SessionMessageService {

    private static final Logger log = LoggerFactory.getLogger(SessionMessageServiceImpl.class);

    private final SessionMessageRepository sessionMessageRepository;

    private final SessionMessageMapper sessionMessageMapper;

    public SessionMessageServiceImpl(SessionMessageRepository sessionMessageRepository, SessionMessageMapper sessionMessageMapper) {
        this.sessionMessageRepository = sessionMessageRepository;
        this.sessionMessageMapper = sessionMessageMapper;
    }

    @Override
    public SessionMessageDTO save(SessionMessageDTO sessionMessageDTO) {
        log.debug("Request to save SessionMessage : {}", sessionMessageDTO);
        SessionMessage sessionMessage = sessionMessageMapper.toEntity(sessionMessageDTO);
        sessionMessage = sessionMessageRepository.save(sessionMessage);
        return sessionMessageMapper.toDto(sessionMessage);
    }

    @Override
    public SessionMessageDTO update(SessionMessageDTO sessionMessageDTO) {
        log.debug("Request to update SessionMessage : {}", sessionMessageDTO);
        SessionMessage sessionMessage = sessionMessageMapper.toEntity(sessionMessageDTO);
        sessionMessage = sessionMessageRepository.save(sessionMessage);
        return sessionMessageMapper.toDto(sessionMessage);
    }

    @Override
    public Optional<SessionMessageDTO> partialUpdate(SessionMessageDTO sessionMessageDTO) {
        log.debug("Request to partially update SessionMessage : {}", sessionMessageDTO);

        return sessionMessageRepository
            .findById(sessionMessageDTO.getId())
            .map(existingSessionMessage -> {
                sessionMessageMapper.partialUpdate(existingSessionMessage, sessionMessageDTO);

                return existingSessionMessage;
            })
            .map(sessionMessageRepository::save)
            .map(sessionMessageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SessionMessageDTO> findAll() {
        log.debug("Request to get all SessionMessages");
        return sessionMessageRepository
            .findAll()
            .stream()
            .map(sessionMessageMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SessionMessageDTO> findOne(Long id) {
        log.debug("Request to get SessionMessage : {}", id);
        return sessionMessageRepository.findById(id).map(sessionMessageMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SessionMessage : {}", id);
        sessionMessageRepository.deleteById(id);
    }
}
