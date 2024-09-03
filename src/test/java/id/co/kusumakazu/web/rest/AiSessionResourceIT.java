package id.co.kusumakazu.web.rest;

import static id.co.kusumakazu.domain.AiSessionAsserts.*;
import static id.co.kusumakazu.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.kusumakazu.IntegrationTest;
import id.co.kusumakazu.domain.AiSession;
import id.co.kusumakazu.repository.AiSessionRepository;
import id.co.kusumakazu.service.dto.AiSessionDTO;
import id.co.kusumakazu.service.mapper.AiSessionMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link AiSessionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AiSessionResourceIT {

    private static final String DEFAULT_SESSION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SESSION_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_SESSION_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SESSION_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/ai-sessions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AiSessionRepository aiSessionRepository;

    @Autowired
    private AiSessionMapper aiSessionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAiSessionMockMvc;

    private AiSession aiSession;

    private AiSession insertedAiSession;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AiSession createEntity(EntityManager em) {
        AiSession aiSession = new AiSession().sessionName(DEFAULT_SESSION_NAME).sessionCreatedTime(DEFAULT_SESSION_CREATED_TIME);
        return aiSession;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AiSession createUpdatedEntity(EntityManager em) {
        AiSession aiSession = new AiSession().sessionName(UPDATED_SESSION_NAME).sessionCreatedTime(UPDATED_SESSION_CREATED_TIME);
        return aiSession;
    }

    @BeforeEach
    public void initTest() {
        aiSession = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAiSession != null) {
            aiSessionRepository.delete(insertedAiSession);
            insertedAiSession = null;
        }
    }

    @Test
    @Transactional
    void createAiSession() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AiSession
        AiSessionDTO aiSessionDTO = aiSessionMapper.toDto(aiSession);
        var returnedAiSessionDTO = om.readValue(
            restAiSessionMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aiSessionDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AiSessionDTO.class
        );

        // Validate the AiSession in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAiSession = aiSessionMapper.toEntity(returnedAiSessionDTO);
        assertAiSessionUpdatableFieldsEquals(returnedAiSession, getPersistedAiSession(returnedAiSession));

        insertedAiSession = returnedAiSession;
    }

    @Test
    @Transactional
    void createAiSessionWithExistingId() throws Exception {
        // Create the AiSession with an existing ID
        aiSession.setId(1L);
        AiSessionDTO aiSessionDTO = aiSessionMapper.toDto(aiSession);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAiSessionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aiSessionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AiSession in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAiSessions() throws Exception {
        // Initialize the database
        insertedAiSession = aiSessionRepository.saveAndFlush(aiSession);

        // Get all the aiSessionList
        restAiSessionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aiSession.getId().intValue())))
            .andExpect(jsonPath("$.[*].sessionName").value(hasItem(DEFAULT_SESSION_NAME)))
            .andExpect(jsonPath("$.[*].sessionCreatedTime").value(hasItem(DEFAULT_SESSION_CREATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getAiSession() throws Exception {
        // Initialize the database
        insertedAiSession = aiSessionRepository.saveAndFlush(aiSession);

        // Get the aiSession
        restAiSessionMockMvc
            .perform(get(ENTITY_API_URL_ID, aiSession.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aiSession.getId().intValue()))
            .andExpect(jsonPath("$.sessionName").value(DEFAULT_SESSION_NAME))
            .andExpect(jsonPath("$.sessionCreatedTime").value(DEFAULT_SESSION_CREATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAiSession() throws Exception {
        // Get the aiSession
        restAiSessionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAiSession() throws Exception {
        // Initialize the database
        insertedAiSession = aiSessionRepository.saveAndFlush(aiSession);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aiSession
        AiSession updatedAiSession = aiSessionRepository.findById(aiSession.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAiSession are not directly saved in db
        em.detach(updatedAiSession);
        updatedAiSession.sessionName(UPDATED_SESSION_NAME).sessionCreatedTime(UPDATED_SESSION_CREATED_TIME);
        AiSessionDTO aiSessionDTO = aiSessionMapper.toDto(updatedAiSession);

        restAiSessionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aiSessionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aiSessionDTO))
            )
            .andExpect(status().isOk());

        // Validate the AiSession in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAiSessionToMatchAllProperties(updatedAiSession);
    }

    @Test
    @Transactional
    void putNonExistingAiSession() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aiSession.setId(longCount.incrementAndGet());

        // Create the AiSession
        AiSessionDTO aiSessionDTO = aiSessionMapper.toDto(aiSession);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAiSessionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aiSessionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aiSessionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AiSession in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAiSession() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aiSession.setId(longCount.incrementAndGet());

        // Create the AiSession
        AiSessionDTO aiSessionDTO = aiSessionMapper.toDto(aiSession);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAiSessionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(aiSessionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AiSession in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAiSession() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aiSession.setId(longCount.incrementAndGet());

        // Create the AiSession
        AiSessionDTO aiSessionDTO = aiSessionMapper.toDto(aiSession);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAiSessionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(aiSessionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AiSession in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAiSessionWithPatch() throws Exception {
        // Initialize the database
        insertedAiSession = aiSessionRepository.saveAndFlush(aiSession);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aiSession using partial update
        AiSession partialUpdatedAiSession = new AiSession();
        partialUpdatedAiSession.setId(aiSession.getId());

        partialUpdatedAiSession.sessionCreatedTime(UPDATED_SESSION_CREATED_TIME);

        restAiSessionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAiSession.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAiSession))
            )
            .andExpect(status().isOk());

        // Validate the AiSession in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAiSessionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAiSession, aiSession),
            getPersistedAiSession(aiSession)
        );
    }

    @Test
    @Transactional
    void fullUpdateAiSessionWithPatch() throws Exception {
        // Initialize the database
        insertedAiSession = aiSessionRepository.saveAndFlush(aiSession);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the aiSession using partial update
        AiSession partialUpdatedAiSession = new AiSession();
        partialUpdatedAiSession.setId(aiSession.getId());

        partialUpdatedAiSession.sessionName(UPDATED_SESSION_NAME).sessionCreatedTime(UPDATED_SESSION_CREATED_TIME);

        restAiSessionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAiSession.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAiSession))
            )
            .andExpect(status().isOk());

        // Validate the AiSession in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAiSessionUpdatableFieldsEquals(partialUpdatedAiSession, getPersistedAiSession(partialUpdatedAiSession));
    }

    @Test
    @Transactional
    void patchNonExistingAiSession() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aiSession.setId(longCount.incrementAndGet());

        // Create the AiSession
        AiSessionDTO aiSessionDTO = aiSessionMapper.toDto(aiSession);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAiSessionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aiSessionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aiSessionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AiSession in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAiSession() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aiSession.setId(longCount.incrementAndGet());

        // Create the AiSession
        AiSessionDTO aiSessionDTO = aiSessionMapper.toDto(aiSession);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAiSessionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(aiSessionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AiSession in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAiSession() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        aiSession.setId(longCount.incrementAndGet());

        // Create the AiSession
        AiSessionDTO aiSessionDTO = aiSessionMapper.toDto(aiSession);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAiSessionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(aiSessionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AiSession in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAiSession() throws Exception {
        // Initialize the database
        insertedAiSession = aiSessionRepository.saveAndFlush(aiSession);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the aiSession
        restAiSessionMockMvc
            .perform(delete(ENTITY_API_URL_ID, aiSession.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return aiSessionRepository.count();
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

    protected AiSession getPersistedAiSession(AiSession aiSession) {
        return aiSessionRepository.findById(aiSession.getId()).orElseThrow();
    }

    protected void assertPersistedAiSessionToMatchAllProperties(AiSession expectedAiSession) {
        assertAiSessionAllPropertiesEquals(expectedAiSession, getPersistedAiSession(expectedAiSession));
    }

    protected void assertPersistedAiSessionToMatchUpdatableProperties(AiSession expectedAiSession) {
        assertAiSessionAllUpdatablePropertiesEquals(expectedAiSession, getPersistedAiSession(expectedAiSession));
    }
}
