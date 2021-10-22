package frnz.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import frnz.IntegrationTest;
import frnz.domain.Gang;
import frnz.repository.GangRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link GangResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GangResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HANDLE = "AAAAAAAAAA";
    private static final String UPDATED_HANDLE = "BBBBBBBBBB";

    private static final String DEFAULT_HTML_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_HTML_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ANNOUNCEMENT = "AAAAAAAAAA";
    private static final String UPDATED_ANNOUNCEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO = "AAAAAAAAAA";
    private static final String UPDATED_LOGO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/gangs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private GangRepository gangRepository;

    @Autowired
    private MockMvc restGangMockMvc;

    private Gang gang;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gang createEntity() {
        Gang gang = new Gang()
            .name(DEFAULT_NAME)
            .handle(DEFAULT_HANDLE)
            .htmlContent(DEFAULT_HTML_CONTENT)
            .description(DEFAULT_DESCRIPTION)
            .announcement(DEFAULT_ANNOUNCEMENT)
            .logo(DEFAULT_LOGO);
        return gang;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gang createUpdatedEntity() {
        Gang gang = new Gang()
            .name(UPDATED_NAME)
            .handle(UPDATED_HANDLE)
            .htmlContent(UPDATED_HTML_CONTENT)
            .description(UPDATED_DESCRIPTION)
            .announcement(UPDATED_ANNOUNCEMENT)
            .logo(UPDATED_LOGO);
        return gang;
    }

    @BeforeEach
    public void initTest() {
        gangRepository.deleteAll();
        gang = createEntity();
    }

    @Test
    void createGang() throws Exception {
        int databaseSizeBeforeCreate = gangRepository.findAll().size();
        // Create the Gang
        restGangMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gang)))
            .andExpect(status().isCreated());

        // Validate the Gang in the database
        List<Gang> gangList = gangRepository.findAll();
        assertThat(gangList).hasSize(databaseSizeBeforeCreate + 1);
        Gang testGang = gangList.get(gangList.size() - 1);
        assertThat(testGang.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGang.getHandle()).isEqualTo(DEFAULT_HANDLE);
        assertThat(testGang.getHtmlContent()).isEqualTo(DEFAULT_HTML_CONTENT);
        assertThat(testGang.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGang.getAnnouncement()).isEqualTo(DEFAULT_ANNOUNCEMENT);
        assertThat(testGang.getLogo()).isEqualTo(DEFAULT_LOGO);
    }

    @Test
    void createGangWithExistingId() throws Exception {
        // Create the Gang with an existing ID
        gang.setId("existing_id");

        int databaseSizeBeforeCreate = gangRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGangMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gang)))
            .andExpect(status().isBadRequest());

        // Validate the Gang in the database
        List<Gang> gangList = gangRepository.findAll();
        assertThat(gangList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = gangRepository.findAll().size();
        // set the field null
        gang.setName(null);

        // Create the Gang, which fails.

        restGangMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gang)))
            .andExpect(status().isBadRequest());

        List<Gang> gangList = gangRepository.findAll();
        assertThat(gangList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkHandleIsRequired() throws Exception {
        int databaseSizeBeforeTest = gangRepository.findAll().size();
        // set the field null
        gang.setHandle(null);

        // Create the Gang, which fails.

        restGangMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gang)))
            .andExpect(status().isBadRequest());

        List<Gang> gangList = gangRepository.findAll();
        assertThat(gangList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllGangs() throws Exception {
        // Initialize the database
        gangRepository.save(gang);

        // Get all the gangList
        restGangMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gang.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].handle").value(hasItem(DEFAULT_HANDLE)))
            .andExpect(jsonPath("$.[*].htmlContent").value(hasItem(DEFAULT_HTML_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].announcement").value(hasItem(DEFAULT_ANNOUNCEMENT)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO)));
    }

    @Test
    void getGang() throws Exception {
        // Initialize the database
        gangRepository.save(gang);

        // Get the gang
        restGangMockMvc
            .perform(get(ENTITY_API_URL_ID, gang.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gang.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.handle").value(DEFAULT_HANDLE))
            .andExpect(jsonPath("$.htmlContent").value(DEFAULT_HTML_CONTENT.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.announcement").value(DEFAULT_ANNOUNCEMENT))
            .andExpect(jsonPath("$.logo").value(DEFAULT_LOGO));
    }

    @Test
    void getNonExistingGang() throws Exception {
        // Get the gang
        restGangMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewGang() throws Exception {
        // Initialize the database
        gangRepository.save(gang);

        int databaseSizeBeforeUpdate = gangRepository.findAll().size();

        // Update the gang
        Gang updatedGang = gangRepository.findById(gang.getId()).get();
        updatedGang
            .name(UPDATED_NAME)
            .handle(UPDATED_HANDLE)
            .htmlContent(UPDATED_HTML_CONTENT)
            .description(UPDATED_DESCRIPTION)
            .announcement(UPDATED_ANNOUNCEMENT)
            .logo(UPDATED_LOGO);

        restGangMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGang.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGang))
            )
            .andExpect(status().isOk());

        // Validate the Gang in the database
        List<Gang> gangList = gangRepository.findAll();
        assertThat(gangList).hasSize(databaseSizeBeforeUpdate);
        Gang testGang = gangList.get(gangList.size() - 1);
        assertThat(testGang.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGang.getHandle()).isEqualTo(UPDATED_HANDLE);
        assertThat(testGang.getHtmlContent()).isEqualTo(UPDATED_HTML_CONTENT);
        assertThat(testGang.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGang.getAnnouncement()).isEqualTo(UPDATED_ANNOUNCEMENT);
        assertThat(testGang.getLogo()).isEqualTo(UPDATED_LOGO);
    }

    @Test
    void putNonExistingGang() throws Exception {
        int databaseSizeBeforeUpdate = gangRepository.findAll().size();
        gang.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGangMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gang.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gang))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gang in the database
        List<Gang> gangList = gangRepository.findAll();
        assertThat(gangList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchGang() throws Exception {
        int databaseSizeBeforeUpdate = gangRepository.findAll().size();
        gang.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGangMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gang))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gang in the database
        List<Gang> gangList = gangRepository.findAll();
        assertThat(gangList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamGang() throws Exception {
        int databaseSizeBeforeUpdate = gangRepository.findAll().size();
        gang.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGangMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gang)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gang in the database
        List<Gang> gangList = gangRepository.findAll();
        assertThat(gangList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateGangWithPatch() throws Exception {
        // Initialize the database
        gangRepository.save(gang);

        int databaseSizeBeforeUpdate = gangRepository.findAll().size();

        // Update the gang using partial update
        Gang partialUpdatedGang = new Gang();
        partialUpdatedGang.setId(gang.getId());

        partialUpdatedGang
            .name(UPDATED_NAME)
            .handle(UPDATED_HANDLE)
            .description(UPDATED_DESCRIPTION)
            .announcement(UPDATED_ANNOUNCEMENT)
            .logo(UPDATED_LOGO);

        restGangMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGang.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGang))
            )
            .andExpect(status().isOk());

        // Validate the Gang in the database
        List<Gang> gangList = gangRepository.findAll();
        assertThat(gangList).hasSize(databaseSizeBeforeUpdate);
        Gang testGang = gangList.get(gangList.size() - 1);
        assertThat(testGang.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGang.getHandle()).isEqualTo(UPDATED_HANDLE);
        assertThat(testGang.getHtmlContent()).isEqualTo(DEFAULT_HTML_CONTENT);
        assertThat(testGang.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGang.getAnnouncement()).isEqualTo(UPDATED_ANNOUNCEMENT);
        assertThat(testGang.getLogo()).isEqualTo(UPDATED_LOGO);
    }

    @Test
    void fullUpdateGangWithPatch() throws Exception {
        // Initialize the database
        gangRepository.save(gang);

        int databaseSizeBeforeUpdate = gangRepository.findAll().size();

        // Update the gang using partial update
        Gang partialUpdatedGang = new Gang();
        partialUpdatedGang.setId(gang.getId());

        partialUpdatedGang
            .name(UPDATED_NAME)
            .handle(UPDATED_HANDLE)
            .htmlContent(UPDATED_HTML_CONTENT)
            .description(UPDATED_DESCRIPTION)
            .announcement(UPDATED_ANNOUNCEMENT)
            .logo(UPDATED_LOGO);

        restGangMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGang.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGang))
            )
            .andExpect(status().isOk());

        // Validate the Gang in the database
        List<Gang> gangList = gangRepository.findAll();
        assertThat(gangList).hasSize(databaseSizeBeforeUpdate);
        Gang testGang = gangList.get(gangList.size() - 1);
        assertThat(testGang.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGang.getHandle()).isEqualTo(UPDATED_HANDLE);
        assertThat(testGang.getHtmlContent()).isEqualTo(UPDATED_HTML_CONTENT);
        assertThat(testGang.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGang.getAnnouncement()).isEqualTo(UPDATED_ANNOUNCEMENT);
        assertThat(testGang.getLogo()).isEqualTo(UPDATED_LOGO);
    }

    @Test
    void patchNonExistingGang() throws Exception {
        int databaseSizeBeforeUpdate = gangRepository.findAll().size();
        gang.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGangMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gang.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gang))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gang in the database
        List<Gang> gangList = gangRepository.findAll();
        assertThat(gangList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchGang() throws Exception {
        int databaseSizeBeforeUpdate = gangRepository.findAll().size();
        gang.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGangMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gang))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gang in the database
        List<Gang> gangList = gangRepository.findAll();
        assertThat(gangList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamGang() throws Exception {
        int databaseSizeBeforeUpdate = gangRepository.findAll().size();
        gang.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGangMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(gang)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gang in the database
        List<Gang> gangList = gangRepository.findAll();
        assertThat(gangList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteGang() throws Exception {
        // Initialize the database
        gangRepository.save(gang);

        int databaseSizeBeforeDelete = gangRepository.findAll().size();

        // Delete the gang
        restGangMockMvc
            .perform(delete(ENTITY_API_URL_ID, gang.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Gang> gangList = gangRepository.findAll();
        assertThat(gangList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
