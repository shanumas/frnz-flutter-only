package com.kompi.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kompi.IntegrationTest;
import com.kompi.domain.MemberStatus;
import com.kompi.repository.MemberStatusRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MemberStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MemberStatusResourceIT {

    private static final Boolean DEFAULT_SURE = false;
    private static final Boolean UPDATED_SURE = true;

    private static final Boolean DEFAULT_BOOKER = false;
    private static final Boolean UPDATED_BOOKER = true;

    private static final Boolean DEFAULT_WAITING = false;
    private static final Boolean UPDATED_WAITING = true;

    private static final Integer DEFAULT_SHARE = 1;
    private static final Integer UPDATED_SHARE = 2;

    private static final String ENTITY_API_URL = "/api/member-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MemberStatusRepository memberStatusRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMemberStatusMockMvc;

    private MemberStatus memberStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MemberStatus createEntity(EntityManager em) {
        MemberStatus memberStatus = new MemberStatus()
            .sure(DEFAULT_SURE)
            .booker(DEFAULT_BOOKER)
            .waiting(DEFAULT_WAITING)
            .share(DEFAULT_SHARE);
        return memberStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MemberStatus createUpdatedEntity(EntityManager em) {
        MemberStatus memberStatus = new MemberStatus()
            .sure(UPDATED_SURE)
            .booker(UPDATED_BOOKER)
            .waiting(UPDATED_WAITING)
            .share(UPDATED_SHARE);
        return memberStatus;
    }

    @BeforeEach
    public void initTest() {
        memberStatus = createEntity(em);
    }

    @Test
    @Transactional
    void createMemberStatus() throws Exception {
        int databaseSizeBeforeCreate = memberStatusRepository.findAll().size();
        // Create the MemberStatus
        restMemberStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberStatus)))
            .andExpect(status().isCreated());

        // Validate the MemberStatus in the database
        List<MemberStatus> memberStatusList = memberStatusRepository.findAll();
        assertThat(memberStatusList).hasSize(databaseSizeBeforeCreate + 1);
        MemberStatus testMemberStatus = memberStatusList.get(memberStatusList.size() - 1);
        assertThat(testMemberStatus.getSure()).isEqualTo(DEFAULT_SURE);
        assertThat(testMemberStatus.getBooker()).isEqualTo(DEFAULT_BOOKER);
        assertThat(testMemberStatus.getWaiting()).isEqualTo(DEFAULT_WAITING);
        assertThat(testMemberStatus.getShare()).isEqualTo(DEFAULT_SHARE);
    }

    @Test
    @Transactional
    void createMemberStatusWithExistingId() throws Exception {
        // Create the MemberStatus with an existing ID
        memberStatus.setId(1L);

        int databaseSizeBeforeCreate = memberStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemberStatusMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberStatus)))
            .andExpect(status().isBadRequest());

        // Validate the MemberStatus in the database
        List<MemberStatus> memberStatusList = memberStatusRepository.findAll();
        assertThat(memberStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMemberStatuses() throws Exception {
        // Initialize the database
        memberStatusRepository.saveAndFlush(memberStatus);

        // Get all the memberStatusList
        restMemberStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(memberStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].sure").value(hasItem(DEFAULT_SURE.booleanValue())))
            .andExpect(jsonPath("$.[*].booker").value(hasItem(DEFAULT_BOOKER.booleanValue())))
            .andExpect(jsonPath("$.[*].waiting").value(hasItem(DEFAULT_WAITING.booleanValue())))
            .andExpect(jsonPath("$.[*].share").value(hasItem(DEFAULT_SHARE)));
    }

    @Test
    @Transactional
    void getMemberStatus() throws Exception {
        // Initialize the database
        memberStatusRepository.saveAndFlush(memberStatus);

        // Get the memberStatus
        restMemberStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, memberStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(memberStatus.getId().intValue()))
            .andExpect(jsonPath("$.sure").value(DEFAULT_SURE.booleanValue()))
            .andExpect(jsonPath("$.booker").value(DEFAULT_BOOKER.booleanValue()))
            .andExpect(jsonPath("$.waiting").value(DEFAULT_WAITING.booleanValue()))
            .andExpect(jsonPath("$.share").value(DEFAULT_SHARE));
    }

    @Test
    @Transactional
    void getNonExistingMemberStatus() throws Exception {
        // Get the memberStatus
        restMemberStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMemberStatus() throws Exception {
        // Initialize the database
        memberStatusRepository.saveAndFlush(memberStatus);

        int databaseSizeBeforeUpdate = memberStatusRepository.findAll().size();

        // Update the memberStatus
        MemberStatus updatedMemberStatus = memberStatusRepository.findById(memberStatus.getId()).get();
        // Disconnect from session so that the updates on updatedMemberStatus are not directly saved in db
        em.detach(updatedMemberStatus);
        updatedMemberStatus.sure(UPDATED_SURE).booker(UPDATED_BOOKER).waiting(UPDATED_WAITING).share(UPDATED_SHARE);

        restMemberStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMemberStatus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMemberStatus))
            )
            .andExpect(status().isOk());

        // Validate the MemberStatus in the database
        List<MemberStatus> memberStatusList = memberStatusRepository.findAll();
        assertThat(memberStatusList).hasSize(databaseSizeBeforeUpdate);
        MemberStatus testMemberStatus = memberStatusList.get(memberStatusList.size() - 1);
        assertThat(testMemberStatus.getSure()).isEqualTo(UPDATED_SURE);
        assertThat(testMemberStatus.getBooker()).isEqualTo(UPDATED_BOOKER);
        assertThat(testMemberStatus.getWaiting()).isEqualTo(UPDATED_WAITING);
        assertThat(testMemberStatus.getShare()).isEqualTo(UPDATED_SHARE);
    }

    @Test
    @Transactional
    void putNonExistingMemberStatus() throws Exception {
        int databaseSizeBeforeUpdate = memberStatusRepository.findAll().size();
        memberStatus.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, memberStatus.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberStatus in the database
        List<MemberStatus> memberStatusList = memberStatusRepository.findAll();
        assertThat(memberStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMemberStatus() throws Exception {
        int databaseSizeBeforeUpdate = memberStatusRepository.findAll().size();
        memberStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberStatus in the database
        List<MemberStatus> memberStatusList = memberStatusRepository.findAll();
        assertThat(memberStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMemberStatus() throws Exception {
        int databaseSizeBeforeUpdate = memberStatusRepository.findAll().size();
        memberStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberStatusMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberStatus)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MemberStatus in the database
        List<MemberStatus> memberStatusList = memberStatusRepository.findAll();
        assertThat(memberStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMemberStatusWithPatch() throws Exception {
        // Initialize the database
        memberStatusRepository.saveAndFlush(memberStatus);

        int databaseSizeBeforeUpdate = memberStatusRepository.findAll().size();

        // Update the memberStatus using partial update
        MemberStatus partialUpdatedMemberStatus = new MemberStatus();
        partialUpdatedMemberStatus.setId(memberStatus.getId());

        partialUpdatedMemberStatus.waiting(UPDATED_WAITING).share(UPDATED_SHARE);

        restMemberStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMemberStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMemberStatus))
            )
            .andExpect(status().isOk());

        // Validate the MemberStatus in the database
        List<MemberStatus> memberStatusList = memberStatusRepository.findAll();
        assertThat(memberStatusList).hasSize(databaseSizeBeforeUpdate);
        MemberStatus testMemberStatus = memberStatusList.get(memberStatusList.size() - 1);
        assertThat(testMemberStatus.getSure()).isEqualTo(DEFAULT_SURE);
        assertThat(testMemberStatus.getBooker()).isEqualTo(DEFAULT_BOOKER);
        assertThat(testMemberStatus.getWaiting()).isEqualTo(UPDATED_WAITING);
        assertThat(testMemberStatus.getShare()).isEqualTo(UPDATED_SHARE);
    }

    @Test
    @Transactional
    void fullUpdateMemberStatusWithPatch() throws Exception {
        // Initialize the database
        memberStatusRepository.saveAndFlush(memberStatus);

        int databaseSizeBeforeUpdate = memberStatusRepository.findAll().size();

        // Update the memberStatus using partial update
        MemberStatus partialUpdatedMemberStatus = new MemberStatus();
        partialUpdatedMemberStatus.setId(memberStatus.getId());

        partialUpdatedMemberStatus.sure(UPDATED_SURE).booker(UPDATED_BOOKER).waiting(UPDATED_WAITING).share(UPDATED_SHARE);

        restMemberStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMemberStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMemberStatus))
            )
            .andExpect(status().isOk());

        // Validate the MemberStatus in the database
        List<MemberStatus> memberStatusList = memberStatusRepository.findAll();
        assertThat(memberStatusList).hasSize(databaseSizeBeforeUpdate);
        MemberStatus testMemberStatus = memberStatusList.get(memberStatusList.size() - 1);
        assertThat(testMemberStatus.getSure()).isEqualTo(UPDATED_SURE);
        assertThat(testMemberStatus.getBooker()).isEqualTo(UPDATED_BOOKER);
        assertThat(testMemberStatus.getWaiting()).isEqualTo(UPDATED_WAITING);
        assertThat(testMemberStatus.getShare()).isEqualTo(UPDATED_SHARE);
    }

    @Test
    @Transactional
    void patchNonExistingMemberStatus() throws Exception {
        int databaseSizeBeforeUpdate = memberStatusRepository.findAll().size();
        memberStatus.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, memberStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberStatus in the database
        List<MemberStatus> memberStatusList = memberStatusRepository.findAll();
        assertThat(memberStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMemberStatus() throws Exception {
        int databaseSizeBeforeUpdate = memberStatusRepository.findAll().size();
        memberStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberStatus in the database
        List<MemberStatus> memberStatusList = memberStatusRepository.findAll();
        assertThat(memberStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMemberStatus() throws Exception {
        int databaseSizeBeforeUpdate = memberStatusRepository.findAll().size();
        memberStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberStatusMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(memberStatus))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MemberStatus in the database
        List<MemberStatus> memberStatusList = memberStatusRepository.findAll();
        assertThat(memberStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMemberStatus() throws Exception {
        // Initialize the database
        memberStatusRepository.saveAndFlush(memberStatus);

        int databaseSizeBeforeDelete = memberStatusRepository.findAll().size();

        // Delete the memberStatus
        restMemberStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, memberStatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MemberStatus> memberStatusList = memberStatusRepository.findAll();
        assertThat(memberStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
