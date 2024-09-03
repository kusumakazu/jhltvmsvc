package id.co.kusumakazu.service;

import id.co.kusumakazu.service.dto.SessionMessageDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link id.co.kusumakazu.domain.SessionMessage}.
 */
public interface SessionMessageService {
    /**
     * Save a sessionMessage.
     *
     * @param sessionMessageDTO the entity to save.
     * @return the persisted entity.
     */
    SessionMessageDTO save(SessionMessageDTO sessionMessageDTO);

    /**
     * Updates a sessionMessage.
     *
     * @param sessionMessageDTO the entity to update.
     * @return the persisted entity.
     */
    SessionMessageDTO update(SessionMessageDTO sessionMessageDTO);

    /**
     * Partially updates a sessionMessage.
     *
     * @param sessionMessageDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SessionMessageDTO> partialUpdate(SessionMessageDTO sessionMessageDTO);

    /**
     * Get all the sessionMessages.
     *
     * @return the list of entities.
     */
    List<SessionMessageDTO> findAll();

    /**
     * Get the "id" sessionMessage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SessionMessageDTO> findOne(Long id);

    /**
     * Delete the "id" sessionMessage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
