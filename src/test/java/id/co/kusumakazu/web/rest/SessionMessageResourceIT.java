package id.co.kusumakazu.web.rest;

import static id.co.kusumakazu.domain.SessionMessageAsserts.*;
import static id.co.kusumakazu.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.kusumakazu.IntegrationTest;
import id.co.kusumakazu.domain.SessionMessage;
import id.co.kusumakazu.repository.SessionMessageRepository;
import id.co.kusumakazu.service.dto.SessionMessageDTO;
import id.co.kusumakazu.service.mapper.SessionMessageMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SessionMessageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SessionMessageResourceIT {

    private static final String DEFAULT_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Long DEFAULT_AI_SESSION_ID = 1L;
    private static final Long UPDATED_AI_SESSION_ID = 2L;

    private static final String ENTITY_API_URL = "/api/session-messages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SessionMessageRepository sessionMessageRepository;

    @Autowired
    private SessionMessageMapper sessionMessageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSessionMessageMockMvc;

    private SessionMessage sessionMessage;

    private SessionMessage insertedSessionMessage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SessionMessage createEntity(EntityManager em) {
        SessionMessage sessionMessage = new SessionMessage().role(DEFAULT_ROLE).content(DEFAULT_CONTENT).aiSessionId(DEFAULT_AI_SESSION_ID);
        return sessionMessage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SessionMessage createUpdatedEntity(EntityManager em) {
        SessionMessage sessionMessage = new SessionMessage().role(UPDATED_ROLE).content(UPDATED_CONTENT).aiSessionId(UPDATED_AI_SESSION_ID);
        return sessionMessage;
    }

    @BeforeEach
    public void initTest() {
        sessionMessage = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedSessionMessage != null) {
            sessionMessageRepository.delete(insertedSessionMessage);
            insertedSessionMessage = null;
        }
    }

    @Test
    @Transactional
    void createSessionMessage() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SessionMessage
        SessionMessageDTO sessionMessageDTO = sessionMessageMapper.toDto(sessionMessage);
        var returnedSessionMessageDTO = om.readValue(
            restSessionMessageMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sessionMessageDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SessionMessageDTO.class
        );

        // Validate the SessionMessage in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSessionMessage = sessionMessageMapper.toEntity(returnedSessionMessageDTO);
        assertSessionMessageUpdatableFieldsEquals(returnedSessionMessage, getPersistedSessionMessage(returnedSessionMessage));

        insertedSessionMessage = returnedSessionMessage;
    }

    @Test
    @Transactional
    void createSessionMessageWithExistingId() throws Exception {
        // Create the SessionMessage with an existing ID
        sessionMessage.setId(1L);
        SessionMessageDTO sessionMessageDTO = sessionMessageMapper.toDto(sessionMessage);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSessionMessageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sessionMessageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SessionMessage in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSessionMessages() throws Exception {
        // Initialize the database
        insertedSessionMessage = sessionMessageRepository.saveAndFlush(sessionMessage);

        // Get all the sessionMessageList
        restSessionMessageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sessionMessage.getId().intValue())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].aiSessionId").value(hasItem(DEFAULT_AI_SESSION_ID.intValue())));
    }

    @Test
    @Transactional
    void getSessionMessage() throws Exception {
        // Initialize the database
        insertedSessionMessage = sessionMessageRepository.saveAndFlush(sessionMessage);

        // Get the sessionMessage
        restSessionMessageMockMvc
            .perform(get(ENTITY_API_URL_ID, sessionMessage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sessionMessage.getId().intValue()))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.aiSessionId").value(DEFAULT_AI_SESSION_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingSessionMessage() throws Exception {
        // Get the sessionMessage
        restSessionMessageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSessionMessage() throws Exception {
        // Initialize the database
        insertedSessionMessage = sessionMessageRepository.saveAndFlush(sessionMessage);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sessionMessage
        SessionMessage updatedSessionMessage = sessionMessageRepository.findById(sessionMessage.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSessionMessage are not directly saved in db
        em.detach(updatedSessionMessage);
        updatedSessionMessage.role(UPDATED_ROLE).content(UPDATED_CONTENT).aiSessionId(UPDATED_AI_SESSION_ID);
        SessionMessageDTO sessionMessageDTO = sessionMessageMapper.toDto(updatedSessionMessage);

        restSessionMessageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sessionMessageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sessionMessageDTO))
            )
            .andExpect(status().isOk());

        // Validate the SessionMessage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSessionMessageToMatchAllProperties(updatedSessionMessage);
    }

    @Test
    @Transactional
    void putNonExistingSessionMessage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sessionMessage.setId(longCount.incrementAndGet());

        // Create the SessionMessage
        SessionMessageDTO sessionMessageDTO = sessionMessageMapper.toDto(sessionMessage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSessionMessageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sessionMessageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sessionMessageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SessionMessage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSessionMessage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sessionMessage.setId(longCount.incrementAndGet());

        // Create the SessionMessage
        SessionMessageDTO sessionMessageDTO = sessionMessageMapper.toDto(sessionMessage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSessionMessageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sessionMessageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SessionMessage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSessionMessage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sessionMessage.setId(longCount.incrementAndGet());

        // Create the SessionMessage
        SessionMessageDTO sessionMessageDTO = sessionMessageMapper.toDto(sessionMessage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSessionMessageMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sessionMessageDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SessionMessage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSessionMessageWithPatch() throws Exception {
        // Initialize the database
        insertedSessionMessage = sessionMessageRepository.saveAndFlush(sessionMessage);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sessionMessage using partial update
        SessionMessage partialUpdatedSessionMessage = new SessionMessage();
        partialUpdatedSessionMessage.setId(sessionMessage.getId());

        partialUpdatedSessionMessage.role(UPDATED_ROLE).content(UPDATED_CONTENT);

        restSessionMessageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSessionMessage.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSessionMessage))
            )
            .andExpect(status().isOk());

        // Validate the SessionMessage in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSessionMessageUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSessionMessage, sessionMessage),
            getPersistedSessionMessage(sessionMessage)
        );
    }

    @Test
    @Transactional
    void fullUpdateSessionMessageWithPatch() throws Exception {
        // Initialize the database
        insertedSessionMessage = sessionMessageRepository.saveAndFlush(sessionMessage);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sessionMessage using partial update
        SessionMessage partialUpdatedSessionMessage = new SessionMessage();
        partialUpdatedSessionMessage.setId(sessionMessage.getId());

        partialUpdatedSessionMessage.role(UPDATED_ROLE).content(UPDATED_CONTENT).aiSessionId(UPDATED_AI_SESSION_ID);

        restSessionMessageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSessionMessage.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSessionMessage))
            )
            .andExpect(status().isOk());

        // Validate the SessionMessage in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSessionMessageUpdatableFieldsEquals(partialUpdatedSessionMessage, getPersistedSessionMessage(partialUpdatedSessionMessage));
    }

    @Test
    @Transactional
    void patchNonExistingSessionMessage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sessionMessage.setId(longCount.incrementAndGet());

        // Create the SessionMessage
        SessionMessageDTO sessionMessageDTO = sessionMessageMapper.toDto(sessionMessage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSessionMessageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sessionMessageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sessionMessageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SessionMessage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSessionMessage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sessionMessage.setId(longCount.incrementAndGet());

        // Create the SessionMessage
        SessionMessageDTO sessionMessageDTO = sessionMessageMapper.toDto(sessionMessage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSessionMessageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sessionMessageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SessionMessage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSessionMessage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sessionMessage.setId(longCount.incrementAndGet());

        // Create the SessionMessage
        SessionMessageDTO sessionMessageDTO = sessionMessageMapper.toDto(sessionMessage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSessionMessageMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sessionMessageDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SessionMessage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSessionMessage() throws Exception {
        // Initialize the database
        insertedSessionMessage = sessionMessageRepository.saveAndFlush(sessionMessage);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sessionMessage
        restSessionMessageMockMvc
            .perform(delete(ENTITY_API_URL_ID, sessionMessage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sessionMessageRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected SessionMessage getPersistedSessionMessage(SessionMessage sessionMessage) {
        return sessionMessageRepository.findById(sessionMessage.getId()).orElseThrow();
    }

    protected void assertPersistedSessionMessageToMatchAllProperties(SessionMessage expectedSessionMessage) {
        assertSessionMessageAllPropertiesEquals(expectedSessionMessage, getPersistedSessionMessage(expectedSessionMessage));
    }

    protected void assertPersistedSessionMessageToMatchUpdatableProperties(SessionMessage expectedSessionMessage) {
        assertSessionMessageAllUpdatablePropertiesEquals(expectedSessionMessage, getPersistedSessionMessage(expectedSessionMessage));
    }
}
