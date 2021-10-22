package frnz.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import frnz.IntegrationTest;
import frnz.domain.Moderator;
import frnz.repository.ModeratorRepository;
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
 * Integration tests for the {@link ModeratorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ModeratorResourceIT {

    private static final String ENTITY_API_URL = "/api/moderators";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ModeratorRepository moderatorRepository;

    @Autowired
    private MockMvc restModeratorMockMvc;

    private Moderator moderator;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Moderator createEntity() {
        Moderator moderator = new Moderator();
        return moderator;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Moderator createUpdatedEntity() {
        Moderator moderator = new Moderator();
        return moderator;
    }

    @BeforeEach
    public void initTest() {
        moderatorRepository.deleteAll();
        moderator = createEntity();
    }

    @Test
    void createModerator() throws Exception {
        int databaseSizeBeforeCreate = moderatorRepository.findAll().size();
        // Create the Moderator
        restModeratorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(moderator)))
            .andExpect(status().isCreated());

        // Validate the Moderator in the database
        List<Moderator> moderatorList = moderatorRepository.findAll();
        assertThat(moderatorList).hasSize(databaseSizeBeforeCreate + 1);
        Moderator testModerator = moderatorList.get(moderatorList.size() - 1);
    }

    @Test
    void createModeratorWithExistingId() throws Exception {
        // Create the Moderator with an existing ID
        moderator.setId("existing_id");

        int databaseSizeBeforeCreate = moderatorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restModeratorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(moderator)))
            .andExpect(status().isBadRequest());

        // Validate the Moderator in the database
        List<Moderator> moderatorList = moderatorRepository.findAll();
        assertThat(moderatorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllModerators() throws Exception {
        // Initialize the database
        moderatorRepository.save(moderator);

        // Get all the moderatorList
        restModeratorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moderator.getId())));
    }

    @Test
    void getModerator() throws Exception {
        // Initialize the database
        moderatorRepository.save(moderator);

        // Get the moderator
        restModeratorMockMvc
            .perform(get(ENTITY_API_URL_ID, moderator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(moderator.getId()));
    }

    @Test
    void getNonExistingModerator() throws Exception {
        // Get the moderator
        restModeratorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewModerator() throws Exception {
        // Initialize the database
        moderatorRepository.save(moderator);

        int databaseSizeBeforeUpdate = moderatorRepository.findAll().size();

        // Update the moderator
        Moderator updatedModerator = moderatorRepository.findById(moderator.getId()).get();

        restModeratorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedModerator.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedModerator))
            )
            .andExpect(status().isOk());

        // Validate the Moderator in the database
        List<Moderator> moderatorList = moderatorRepository.findAll();
        assertThat(moderatorList).hasSize(databaseSizeBeforeUpdate);
        Moderator testModerator = moderatorList.get(moderatorList.size() - 1);
    }

    @Test
    void putNonExistingModerator() throws Exception {
        int databaseSizeBeforeUpdate = moderatorRepository.findAll().size();
        moderator.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModeratorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, moderator.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moderator))
            )
            .andExpect(status().isBadRequest());

        // Validate the Moderator in the database
        List<Moderator> moderatorList = moderatorRepository.findAll();
        assertThat(moderatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchModerator() throws Exception {
        int databaseSizeBeforeUpdate = moderatorRepository.findAll().size();
        moderator.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModeratorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moderator))
            )
            .andExpect(status().isBadRequest());

        // Validate the Moderator in the database
        List<Moderator> moderatorList = moderatorRepository.findAll();
        assertThat(moderatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamModerator() throws Exception {
        int databaseSizeBeforeUpdate = moderatorRepository.findAll().size();
        moderator.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModeratorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(moderator)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Moderator in the database
        List<Moderator> moderatorList = moderatorRepository.findAll();
        assertThat(moderatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateModeratorWithPatch() throws Exception {
        // Initialize the database
        moderatorRepository.save(moderator);

        int databaseSizeBeforeUpdate = moderatorRepository.findAll().size();

        // Update the moderator using partial update
        Moderator partialUpdatedModerator = new Moderator();
        partialUpdatedModerator.setId(moderator.getId());

        restModeratorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModerator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedModerator))
            )
            .andExpect(status().isOk());

        // Validate the Moderator in the database
        List<Moderator> moderatorList = moderatorRepository.findAll();
        assertThat(moderatorList).hasSize(databaseSizeBeforeUpdate);
        Moderator testModerator = moderatorList.get(moderatorList.size() - 1);
    }

    @Test
    void fullUpdateModeratorWithPatch() throws Exception {
        // Initialize the database
        moderatorRepository.save(moderator);

        int databaseSizeBeforeUpdate = moderatorRepository.findAll().size();

        // Update the moderator using partial update
        Moderator partialUpdatedModerator = new Moderator();
        partialUpdatedModerator.setId(moderator.getId());

        restModeratorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModerator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedModerator))
            )
            .andExpect(status().isOk());

        // Validate the Moderator in the database
        List<Moderator> moderatorList = moderatorRepository.findAll();
        assertThat(moderatorList).hasSize(databaseSizeBeforeUpdate);
        Moderator testModerator = moderatorList.get(moderatorList.size() - 1);
    }

    @Test
    void patchNonExistingModerator() throws Exception {
        int databaseSizeBeforeUpdate = moderatorRepository.findAll().size();
        moderator.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModeratorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, moderator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(moderator))
            )
            .andExpect(status().isBadRequest());

        // Validate the Moderator in the database
        List<Moderator> moderatorList = moderatorRepository.findAll();
        assertThat(moderatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchModerator() throws Exception {
        int databaseSizeBeforeUpdate = moderatorRepository.findAll().size();
        moderator.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModeratorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(moderator))
            )
            .andExpect(status().isBadRequest());

        // Validate the Moderator in the database
        List<Moderator> moderatorList = moderatorRepository.findAll();
        assertThat(moderatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamModerator() throws Exception {
        int databaseSizeBeforeUpdate = moderatorRepository.findAll().size();
        moderator.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModeratorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(moderator))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Moderator in the database
        List<Moderator> moderatorList = moderatorRepository.findAll();
        assertThat(moderatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteModerator() throws Exception {
        // Initialize the database
        moderatorRepository.save(moderator);

        int databaseSizeBeforeDelete = moderatorRepository.findAll().size();

        // Delete the moderator
        restModeratorMockMvc
            .perform(delete(ENTITY_API_URL_ID, moderator.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Moderator> moderatorList = moderatorRepository.findAll();
        assertThat(moderatorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
