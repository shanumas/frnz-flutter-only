package frnz.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import frnz.IntegrationTest;
import frnz.domain.Participant;
import frnz.repository.ParticipantRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link ParticipantResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParticipantResourceIT {

    private static final Boolean DEFAULT_SURE = false;
    private static final Boolean UPDATED_SURE = true;

    private static final Boolean DEFAULT_HOST = false;
    private static final Boolean UPDATED_HOST = true;

    private static final Boolean DEFAULT_BOOKER = false;
    private static final Boolean UPDATED_BOOKER = true;

    private static final Boolean DEFAULT_WAITING = false;
    private static final Boolean UPDATED_WAITING = true;

    private static final Integer DEFAULT_SHARE = 1;
    private static final Integer UPDATED_SHARE = 2;

    private static final String ENTITY_API_URL = "/api/participants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private MockMvc restParticipantMockMvc;

    private Participant participant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Participant createEntity() {
        Participant participant = new Participant()
            .sure(DEFAULT_SURE)
            .host(DEFAULT_HOST)
            .booker(DEFAULT_BOOKER)
            .waiting(DEFAULT_WAITING)
            .share(DEFAULT_SHARE);
        return participant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Participant createUpdatedEntity() {
        Participant participant = new Participant()
            .sure(UPDATED_SURE)
            .host(UPDATED_HOST)
            .booker(UPDATED_BOOKER)
            .waiting(UPDATED_WAITING)
            .share(UPDATED_SHARE);
        return participant;
    }

    @BeforeEach
    public void initTest() {
        participantRepository.deleteAll();
        participant = createEntity();
    }

    @Test
    void createParticipant() throws Exception {
        int databaseSizeBeforeCreate = participantRepository.findAll().size();
        // Create the Participant
        restParticipantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(participant)))
            .andExpect(status().isCreated());

        // Validate the Participant in the database
        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeCreate + 1);
        Participant testParticipant = participantList.get(participantList.size() - 1);
        assertThat(testParticipant.getSure()).isEqualTo(DEFAULT_SURE);
        assertThat(testParticipant.getHost()).isEqualTo(DEFAULT_HOST);
        assertThat(testParticipant.getBooker()).isEqualTo(DEFAULT_BOOKER);
        assertThat(testParticipant.getWaiting()).isEqualTo(DEFAULT_WAITING);
        assertThat(testParticipant.getShare()).isEqualTo(DEFAULT_SHARE);
    }

    @Test
    void createParticipantWithExistingId() throws Exception {
        // Create the Participant with an existing ID
        participant.setId("existing_id");

        int databaseSizeBeforeCreate = participantRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParticipantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(participant)))
            .andExpect(status().isBadRequest());

        // Validate the Participant in the database
        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllParticipants() throws Exception {
        // Initialize the database
        participantRepository.save(participant);

        // Get all the participantList
        restParticipantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(participant.getId())))
            .andExpect(jsonPath("$.[*].sure").value(hasItem(DEFAULT_SURE.booleanValue())))
            .andExpect(jsonPath("$.[*].host").value(hasItem(DEFAULT_HOST.booleanValue())))
            .andExpect(jsonPath("$.[*].booker").value(hasItem(DEFAULT_BOOKER.booleanValue())))
            .andExpect(jsonPath("$.[*].waiting").value(hasItem(DEFAULT_WAITING.booleanValue())))
            .andExpect(jsonPath("$.[*].share").value(hasItem(DEFAULT_SHARE)));
    }

    @Test
    void getParticipant() throws Exception {
        // Initialize the database
        participantRepository.save(participant);

        // Get the participant
        restParticipantMockMvc
            .perform(get(ENTITY_API_URL_ID, participant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(participant.getId()))
            .andExpect(jsonPath("$.sure").value(DEFAULT_SURE.booleanValue()))
            .andExpect(jsonPath("$.host").value(DEFAULT_HOST.booleanValue()))
            .andExpect(jsonPath("$.booker").value(DEFAULT_BOOKER.booleanValue()))
            .andExpect(jsonPath("$.waiting").value(DEFAULT_WAITING.booleanValue()))
            .andExpect(jsonPath("$.share").value(DEFAULT_SHARE));
    }

    @Test
    void getNonExistingParticipant() throws Exception {
        // Get the participant
        restParticipantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewParticipant() throws Exception {
        // Initialize the database
        participantRepository.save(participant);

        int databaseSizeBeforeUpdate = participantRepository.findAll().size();

        // Update the participant
        Participant updatedParticipant = participantRepository.findById(participant.getId()).get();
        updatedParticipant.sure(UPDATED_SURE).host(UPDATED_HOST).booker(UPDATED_BOOKER).waiting(UPDATED_WAITING).share(UPDATED_SHARE);

        restParticipantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedParticipant.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedParticipant))
            )
            .andExpect(status().isOk());

        // Validate the Participant in the database
        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeUpdate);
        Participant testParticipant = participantList.get(participantList.size() - 1);
        assertThat(testParticipant.getSure()).isEqualTo(UPDATED_SURE);
        assertThat(testParticipant.getHost()).isEqualTo(UPDATED_HOST);
        assertThat(testParticipant.getBooker()).isEqualTo(UPDATED_BOOKER);
        assertThat(testParticipant.getWaiting()).isEqualTo(UPDATED_WAITING);
        assertThat(testParticipant.getShare()).isEqualTo(UPDATED_SHARE);
    }

    @Test
    void putNonExistingParticipant() throws Exception {
        int databaseSizeBeforeUpdate = participantRepository.findAll().size();
        participant.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParticipantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, participant.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(participant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Participant in the database
        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchParticipant() throws Exception {
        int databaseSizeBeforeUpdate = participantRepository.findAll().size();
        participant.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(participant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Participant in the database
        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamParticipant() throws Exception {
        int databaseSizeBeforeUpdate = participantRepository.findAll().size();
        participant.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipantMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(participant)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Participant in the database
        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateParticipantWithPatch() throws Exception {
        // Initialize the database
        participantRepository.save(participant);

        int databaseSizeBeforeUpdate = participantRepository.findAll().size();

        // Update the participant using partial update
        Participant partialUpdatedParticipant = new Participant();
        partialUpdatedParticipant.setId(participant.getId());

        partialUpdatedParticipant.booker(UPDATED_BOOKER).waiting(UPDATED_WAITING);

        restParticipantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParticipant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParticipant))
            )
            .andExpect(status().isOk());

        // Validate the Participant in the database
        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeUpdate);
        Participant testParticipant = participantList.get(participantList.size() - 1);
        assertThat(testParticipant.getSure()).isEqualTo(DEFAULT_SURE);
        assertThat(testParticipant.getHost()).isEqualTo(DEFAULT_HOST);
        assertThat(testParticipant.getBooker()).isEqualTo(UPDATED_BOOKER);
        assertThat(testParticipant.getWaiting()).isEqualTo(UPDATED_WAITING);
        assertThat(testParticipant.getShare()).isEqualTo(DEFAULT_SHARE);
    }

    @Test
    void fullUpdateParticipantWithPatch() throws Exception {
        // Initialize the database
        participantRepository.save(participant);

        int databaseSizeBeforeUpdate = participantRepository.findAll().size();

        // Update the participant using partial update
        Participant partialUpdatedParticipant = new Participant();
        partialUpdatedParticipant.setId(participant.getId());

        partialUpdatedParticipant
            .sure(UPDATED_SURE)
            .host(UPDATED_HOST)
            .booker(UPDATED_BOOKER)
            .waiting(UPDATED_WAITING)
            .share(UPDATED_SHARE);

        restParticipantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParticipant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParticipant))
            )
            .andExpect(status().isOk());

        // Validate the Participant in the database
        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeUpdate);
        Participant testParticipant = participantList.get(participantList.size() - 1);
        assertThat(testParticipant.getSure()).isEqualTo(UPDATED_SURE);
        assertThat(testParticipant.getHost()).isEqualTo(UPDATED_HOST);
        assertThat(testParticipant.getBooker()).isEqualTo(UPDATED_BOOKER);
        assertThat(testParticipant.getWaiting()).isEqualTo(UPDATED_WAITING);
        assertThat(testParticipant.getShare()).isEqualTo(UPDATED_SHARE);
    }

    @Test
    void patchNonExistingParticipant() throws Exception {
        int databaseSizeBeforeUpdate = participantRepository.findAll().size();
        participant.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParticipantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, participant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(participant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Participant in the database
        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchParticipant() throws Exception {
        int databaseSizeBeforeUpdate = participantRepository.findAll().size();
        participant.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(participant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Participant in the database
        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamParticipant() throws Exception {
        int databaseSizeBeforeUpdate = participantRepository.findAll().size();
        participant.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipantMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(participant))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Participant in the database
        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteParticipant() throws Exception {
        // Initialize the database
        participantRepository.save(participant);

        int databaseSizeBeforeDelete = participantRepository.findAll().size();

        // Delete the participant
        restParticipantMockMvc
            .perform(delete(ENTITY_API_URL_ID, participant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
