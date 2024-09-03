package id.co.kusumakazu.web.rest;

import id.co.kusumakazu.repository.SessionMessageRepository;
import id.co.kusumakazu.service.SessionMessageService;
import id.co.kusumakazu.service.dto.SessionMessageDTO;
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
 * REST controller for managing {@link id.co.kusumakazu.domain.SessionMessage}.
 */
@RestController
@RequestMapping("/api/session-messages")
public class SessionMessageResource {

    private static final Logger log = LoggerFactory.getLogger(SessionMessageResource.class);

    private static final String ENTITY_NAME = "jhltvmsvcSessionMessage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SessionMessageService sessionMessageService;

    private final SessionMessageRepository sessionMessageRepository;

    public SessionMessageResource(SessionMessageService sessionMessageService, SessionMessageRepository sessionMessageRepository) {
        this.sessionMessageService = sessionMessageService;
        this.sessionMessageRepository = sessionMessageRepository;
    }

    /**
     * {@code POST  /session-messages} : Create a new sessionMessage.
     *
     * @param sessionMessageDTO the sessionMessageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sessionMessageDTO, or with status {@code 400 (Bad Request)} if the sessionMessage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SessionMessageDTO> createSessionMessage(@RequestBody SessionMessageDTO sessionMessageDTO)
        throws URISyntaxException {
        log.debug("REST request to save SessionMessage : {}", sessionMessageDTO);
        if (sessionMessageDTO.getId() != null) {
            throw new BadRequestAlertException("A new sessionMessage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        sessionMessageDTO = sessionMessageService.save(sessionMessageDTO);
        return ResponseEntity.created(new URI("/api/session-messages/" + sessionMessageDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, sessionMessageDTO.getId().toString()))
            .body(sessionMessageDTO);
    }

    /**
     * {@code PUT  /session-messages/:id} : Updates an existing sessionMessage.
     *
     * @param id the id of the sessionMessageDTO to save.
     * @param sessionMessageDTO the sessionMessageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sessionMessageDTO,
     * or with status {@code 400 (Bad Request)} if the sessionMessageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sessionMessageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SessionMessageDTO> updateSessionMessage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SessionMessageDTO sessionMessageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SessionMessage : {}, {}", id, sessionMessageDTO);
        if (sessionMessageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sessionMessageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sessionMessageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        sessionMessageDTO = sessionMessageService.update(sessionMessageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sessionMessageDTO.getId().toString()))
            .body(sessionMessageDTO);
    }

    /**
     * {@code PATCH  /session-messages/:id} : Partial updates given fields of an existing sessionMessage, field will ignore if it is null
     *
     * @param id the id of the sessionMessageDTO to save.
     * @param sessionMessageDTO the sessionMessageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sessionMessageDTO,
     * or with status {@code 400 (Bad Request)} if the sessionMessageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sessionMessageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sessionMessageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SessionMessageDTO> partialUpdateSessionMessage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SessionMessageDTO sessionMessageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SessionMessage partially : {}, {}", id, sessionMessageDTO);
        if (sessionMessageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sessionMessageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sessionMessageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SessionMessageDTO> result = sessionMessageService.partialUpdate(sessionMessageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sessionMessageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /session-messages} : get all the sessionMessages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sessionMessages in body.
     */
    @GetMapping("")
    public List<SessionMessageDTO> getAllSessionMessages() {
        log.debug("REST request to get all SessionMessages");
        return sessionMessageService.findAll();
    }

    /**
     * {@code GET  /session-messages/:id} : get the "id" sessionMessage.
     *
     * @param id the id of the sessionMessageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sessionMessageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SessionMessageDTO> getSessionMessage(@PathVariable("id") Long id) {
        log.debug("REST request to get SessionMessage : {}", id);
        Optional<SessionMessageDTO> sessionMessageDTO = sessionMessageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sessionMessageDTO);
    }

    /**
     * {@code DELETE  /session-messages/:id} : delete the "id" sessionMessage.
     *
     * @param id the id of the sessionMessageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSessionMessage(@PathVariable("id") Long id) {
        log.debug("REST request to delete SessionMessage : {}", id);
        sessionMessageService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
