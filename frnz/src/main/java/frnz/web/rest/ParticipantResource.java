package frnz.web.rest;

import frnz.domain.Participant;
import frnz.repository.ParticipantRepository;
import frnz.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link frnz.domain.Participant}.
 */
@RestController
@RequestMapping("/api")
public class ParticipantResource {

    private final Logger log = LoggerFactory.getLogger(ParticipantResource.class);

    private static final String ENTITY_NAME = "participant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParticipantRepository participantRepository;

    public ParticipantResource(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    /**
     * {@code POST  /participants} : Create a new participant.
     *
     * @param participant the participant to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new participant, or with status {@code 400 (Bad Request)} if the participant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/participants")
    public ResponseEntity<Participant> createParticipant(@RequestBody Participant participant) throws URISyntaxException {
        log.debug("REST request to save Participant : {}", participant);
        if (participant.getId() != null) {
            throw new BadRequestAlertException("A new participant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Participant result = participantRepository.save(participant);
        return ResponseEntity
            .created(new URI("/api/participants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /participants/:id} : Updates an existing participant.
     *
     * @param id the id of the participant to save.
     * @param participant the participant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated participant,
     * or with status {@code 400 (Bad Request)} if the participant is not valid,
     * or with status {@code 500 (Internal Server Error)} if the participant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/participants/{id}")
    public ResponseEntity<Participant> updateParticipant(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Participant participant
    ) throws URISyntaxException {
        log.debug("REST request to update Participant : {}, {}", id, participant);
        if (participant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, participant.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!participantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Participant result = participantRepository.save(participant);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, participant.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /participants/:id} : Partial updates given fields of an existing participant, field will ignore if it is null
     *
     * @param id the id of the participant to save.
     * @param participant the participant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated participant,
     * or with status {@code 400 (Bad Request)} if the participant is not valid,
     * or with status {@code 404 (Not Found)} if the participant is not found,
     * or with status {@code 500 (Internal Server Error)} if the participant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/participants/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Participant> partialUpdateParticipant(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Participant participant
    ) throws URISyntaxException {
        log.debug("REST request to partial update Participant partially : {}, {}", id, participant);
        if (participant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, participant.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!participantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Participant> result = participantRepository
            .findById(participant.getId())
            .map(existingParticipant -> {
                if (participant.getSure() != null) {
                    existingParticipant.setSure(participant.getSure());
                }
                if (participant.getHost() != null) {
                    existingParticipant.setHost(participant.getHost());
                }
                if (participant.getBooker() != null) {
                    existingParticipant.setBooker(participant.getBooker());
                }
                if (participant.getWaiting() != null) {
                    existingParticipant.setWaiting(participant.getWaiting());
                }
                if (participant.getShare() != null) {
                    existingParticipant.setShare(participant.getShare());
                }

                return existingParticipant;
            })
            .map(participantRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, participant.getId())
        );
    }

    /**
     * {@code GET  /participants} : get all the participants.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of participants in body.
     */
    @GetMapping("/participants")
    public List<Participant> getAllParticipants() {
        log.debug("REST request to get all Participants");
        return participantRepository.findAll();
    }

    /**
     * {@code GET  /participants/:id} : get the "id" participant.
     *
     * @param id the id of the participant to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the participant, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/participants/{id}")
    public ResponseEntity<Participant> getParticipant(@PathVariable String id) {
        log.debug("REST request to get Participant : {}", id);
        Optional<Participant> participant = participantRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(participant);
    }

    /**
     * {@code DELETE  /participants/:id} : delete the "id" participant.
     *
     * @param id the id of the participant to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/participants/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable String id) {
        log.debug("REST request to delete Participant : {}", id);
        participantRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
