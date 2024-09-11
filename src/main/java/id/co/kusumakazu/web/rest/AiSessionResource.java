package id.co.kusumakazu.web.rest;

import id.co.kusumakazu.repository.AiSessionRepository;
import id.co.kusumakazu.service.AiSessionService;
import id.co.kusumakazu.service.dto.AiSessionDTO;
import id.co.kusumakazu.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link id.co.kusumakazu.domain.AiSession}.
 */
@RestController
@RequestMapping("/api/ai-sessions")
public class AiSessionResource {

    private static final Logger log = LoggerFactory.getLogger(AiSessionResource.class);

    private static final String ENTITY_NAME = "jhltvmsvcAiSession";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AiSessionService aiSessionService;

    private final AiSessionRepository aiSessionRepository;

    public AiSessionResource(AiSessionService aiSessionService, AiSessionRepository aiSessionRepository) {
        this.aiSessionService = aiSessionService;
        this.aiSessionRepository = aiSessionRepository;
    }

    /**
     * {@code POST  /ai-sessions} : Create a new aiSession.
     *
     * @param aiSessionDTO the aiSessionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aiSessionDTO, or with status {@code 400 (Bad Request)} if the aiSession has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * you need AiSession.Id for start conversation
     */
    @PostMapping("")
    public ResponseEntity<AiSessionDTO> createAiSession(@RequestBody AiSessionDTO aiSessionDTO) throws URISyntaxException {
        log.debug("REST request to save AiSession : {}", aiSessionDTO);
        if (aiSessionDTO.getId() != null) {
            throw new BadRequestAlertException("A new aiSession cannot already have an ID", ENTITY_NAME, "idexists");
        }
        aiSessionDTO = aiSessionService.save(aiSessionDTO);
        return ResponseEntity.created(new URI("/api/ai-sessions/" + aiSessionDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, aiSessionDTO.getId().toString()))
            .body(aiSessionDTO);
    }

    /**
     * {@code PUT  /ai-sessions/:id} : Updates an existing aiSession.
     *
     * @param id the id of the aiSessionDTO to save.
     * @param aiSessionDTO the aiSessionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aiSessionDTO,
     * or with status {@code 400 (Bad Request)} if the aiSessionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aiSessionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AiSessionDTO> updateAiSession(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AiSessionDTO aiSessionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AiSession : {}, {}", id, aiSessionDTO);
        if (aiSessionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aiSessionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aiSessionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        aiSessionDTO = aiSessionService.update(aiSessionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aiSessionDTO.getId().toString()))
            .body(aiSessionDTO);
    }

    /**
     * {@code PATCH  /ai-sessions/:id} : Partial updates given fields of an existing aiSession, field will ignore if it is null
     *
     * @param id the id of the aiSessionDTO to save.
     * @param aiSessionDTO the aiSessionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aiSessionDTO,
     * or with status {@code 400 (Bad Request)} if the aiSessionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the aiSessionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the aiSessionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AiSessionDTO> partialUpdateAiSession(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AiSessionDTO aiSessionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AiSession partially : {}, {}", id, aiSessionDTO);
        if (aiSessionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aiSessionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aiSessionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AiSessionDTO> result = aiSessionService.partialUpdate(aiSessionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aiSessionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ai-sessions} : get all the aiSessions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aiSessions in body.
     */
    @GetMapping("")
    public List<AiSessionDTO> getAllAiSessions() {
        log.debug("REST request to get all AiSessions");
        return aiSessionService.findAll();
    }

    /**
     * {@code GET  /ai-sessions/:id} : get the "id" aiSession.
     *
     * @param id the id of the aiSessionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aiSessionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AiSessionDTO> getAiSession(@PathVariable("id") Long id) {
        log.debug("REST request to get AiSession : {}", id);
        Optional<AiSessionDTO> aiSessionDTO = aiSessionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(aiSessionDTO);
    }

    /**
     * {@code DELETE  /ai-sessions/:id} : delete the "id" aiSession.
     *
     * @param id the id of the aiSessionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAiSession(@PathVariable("id") Long id) {
        log.debug("REST request to delete AiSession : {}", id);
        aiSessionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
