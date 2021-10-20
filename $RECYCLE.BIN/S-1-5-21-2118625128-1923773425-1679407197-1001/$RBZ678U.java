package com.kompi.web.rest;

import com.kompi.domain.MemberStatus;
import com.kompi.repository.MemberStatusRepository;
import com.kompi.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.kompi.domain.MemberStatus}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MemberStatusResource {

    private final Logger log = LoggerFactory.getLogger(MemberStatusResource.class);

    private static final String ENTITY_NAME = "memberStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MemberStatusRepository memberStatusRepository;

    public MemberStatusResource(MemberStatusRepository memberStatusRepository) {
        this.memberStatusRepository = memberStatusRepository;
    }

    /**
     * {@code POST  /member-statuses} : Create a new memberStatus.
     *
     * @param memberStatus the memberStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new memberStatus, or with status {@code 400 (Bad Request)} if the memberStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/member-statuses")
    public ResponseEntity<MemberStatus> createMemberStatus(@RequestBody MemberStatus memberStatus) throws URISyntaxException {
        log.debug("REST request to save MemberStatus : {}", memberStatus);
        if (memberStatus.getId() != null) {
            throw new BadRequestAlertException("A new memberStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MemberStatus result = memberStatusRepository.save(memberStatus);
        return ResponseEntity
            .created(new URI("/api/member-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /member-statuses/:id} : Updates an existing memberStatus.
     *
     * @param id the id of the memberStatus to save.
     * @param memberStatus the memberStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated memberStatus,
     * or with status {@code 400 (Bad Request)} if the memberStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the memberStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/member-statuses/{id}")
    public ResponseEntity<MemberStatus> updateMemberStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MemberStatus memberStatus
    ) throws URISyntaxException {
        log.debug("REST request to update MemberStatus : {}, {}", id, memberStatus);
        if (memberStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, memberStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!memberStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MemberStatus result = memberStatusRepository.save(memberStatus);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, memberStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /member-statuses/:id} : Partial updates given fields of an existing memberStatus, field will ignore if it is null
     *
     * @param id the id of the memberStatus to save.
     * @param memberStatus the memberStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated memberStatus,
     * or with status {@code 400 (Bad Request)} if the memberStatus is not valid,
     * or with status {@code 404 (Not Found)} if the memberStatus is not found,
     * or with status {@code 500 (Internal Server Error)} if the memberStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/member-statuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MemberStatus> partialUpdateMemberStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MemberStatus memberStatus
    ) throws URISyntaxException {
        log.debug("REST request to partial update MemberStatus partially : {}, {}", id, memberStatus);
        if (memberStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, memberStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!memberStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MemberStatus> result = memberStatusRepository
            .findById(memberStatus.getId())
            .map(existingMemberStatus -> {
                if (memberStatus.getSure() != null) {
                    existingMemberStatus.setSure(memberStatus.getSure());
                }
                if (memberStatus.getBooker() != null) {
                    existingMemberStatus.setBooker(memberStatus.getBooker());
                }
                if (memberStatus.getWaiting() != null) {
                    existingMemberStatus.setWaiting(memberStatus.getWaiting());
                }
                if (memberStatus.getShare() != null) {
                    existingMemberStatus.setShare(memberStatus.getShare());
                }

                return existingMemberStatus;
            })
            .map(memberStatusRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, memberStatus.getId().toString())
        );
    }

    /**
     * {@code GET  /member-statuses} : get all the memberStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of memberStatuses in body.
     */
    @GetMapping("/member-statuses")
    public List<MemberStatus> getAllMemberStatuses() {
        log.debug("REST request to get all MemberStatuses");
        return memberStatusRepository.findAll();
    }

    /**
     * {@code GET  /member-statuses/:id} : get the "id" memberStatus.
     *
     * @param id the id of the memberStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the memberStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/member-statuses/{id}")
    public ResponseEntity<MemberStatus> getMemberStatus(@PathVariable Long id) {
        log.debug("REST request to get MemberStatus : {}", id);
        Optional<MemberStatus> memberStatus = memberStatusRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(memberStatus);
    }

    /**
     * {@code DELETE  /member-statuses/:id} : delete the "id" memberStatus.
     *
     * @param id the id of the memberStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/member-statuses/{id}")
    public ResponseEntity<Void> deleteMemberStatus(@PathVariable Long id) {
        log.debug("REST request to delete MemberStatus : {}", id);
        memberStatusRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
