package id.co.kusumakazu.service;

import id.co.kusumakazu.service.dto.AiSessionDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link id.co.kusumakazu.domain.AiSession}.
 */
public interface AiSessionService {
    /**
     * Save a aiSession.
     *
     * @param aiSessionDTO the entity to save.
     * @return the persisted entity.
     */
    AiSessionDTO save(AiSessionDTO aiSessionDTO);

    /**
     * Updates a aiSession.
     *
     * @param aiSessionDTO the entity to update.
     * @return the persisted entity.
     */
    AiSessionDTO update(AiSessionDTO aiSessionDTO);

    /**
     * Partially updates a aiSession.
     *
     * @param aiSessionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AiSessionDTO> partialUpdate(AiSessionDTO aiSessionDTO);

    /**
     * Get all the aiSessions.
     *
     * @return the list of entities.
     */
    List<AiSessionDTO> findAll();

    /**
     * Get the "id" aiSession.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AiSessionDTO> findOne(Long id);

    /**
     * Delete the "id" aiSession.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
