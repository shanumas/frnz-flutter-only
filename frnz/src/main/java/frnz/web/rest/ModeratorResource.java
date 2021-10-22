package frnz.web.rest;

import frnz.domain.Moderator;
import frnz.repository.ModeratorRepository;
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
 * REST controller for managing {@link frnz.domain.Moderator}.
 */
@RestController
@RequestMapping("/api")
public class ModeratorResource {

    private final Logger log = LoggerFactory.getLogger(ModeratorResource.class);

    private static final String ENTITY_NAME = "moderator";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModeratorRepository moderatorRepository;

    public ModeratorResource(ModeratorRepository moderatorRepository) {
        this.moderatorRepository = moderatorRepository;
    }

    /**
     * {@code POST  /moderators} : Create a new moderator.
     *
     * @param moderator the moderator to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new moderator, or with status {@code 400 (Bad Request)} if the moderator has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/moderators")
    public ResponseEntity<Moderator> createModerator(@RequestBody Moderator moderator) throws URISyntaxException {
        log.debug("REST request to save Moderator : {}", moderator);
        if (moderator.getId() != null) {
            throw new BadRequestAlertException("A new moderator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Moderator result = moderatorRepository.save(moderator);
        return ResponseEntity
            .created(new URI("/api/moderators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /moderators/:id} : Updates an existing moderator.
     *
     * @param id the id of the moderator to save.
     * @param moderator the moderator to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moderator,
     * or with status {@code 400 (Bad Request)} if the moderator is not valid,
     * or with status {@code 500 (Internal Server Error)} if the moderator couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/moderators/{id}")
    public ResponseEntity<Moderator> updateModerator(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Moderator moderator
    ) throws URISyntaxException {
        log.debug("REST request to update Moderator : {}, {}", id, moderator);
        if (moderator.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, moderator.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moderatorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Moderator result = moderatorRepository.save(moderator);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, moderator.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /moderators/:id} : Partial updates given fields of an existing moderator, field will ignore if it is null
     *
     * @param id the id of the moderator to save.
     * @param moderator the moderator to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moderator,
     * or with status {@code 400 (Bad Request)} if the moderator is not valid,
     * or with status {@code 404 (Not Found)} if the moderator is not found,
     * or with status {@code 500 (Internal Server Error)} if the moderator couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/moderators/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Moderator> partialUpdateModerator(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Moderator moderator
    ) throws URISyntaxException {
        log.debug("REST request to partial update Moderator partially : {}, {}", id, moderator);
        if (moderator.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, moderator.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moderatorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Moderator> result = moderatorRepository
            .findById(moderator.getId())
            .map(existingModerator -> {
                return existingModerator;
            })
            .map(moderatorRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, moderator.getId())
        );
    }

    /**
     * {@code GET  /moderators} : get all the moderators.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of moderators in body.
     */
    @GetMapping("/moderators")
    public List<Moderator> getAllModerators() {
        log.debug("REST request to get all Moderators");
        return moderatorRepository.findAll();
    }

    /**
     * {@code GET  /moderators/:id} : get the "id" moderator.
     *
     * @param id the id of the moderator to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the moderator, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/moderators/{id}")
    public ResponseEntity<Moderator> getModerator(@PathVariable String id) {
        log.debug("REST request to get Moderator : {}", id);
        Optional<Moderator> moderator = moderatorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(moderator);
    }

    /**
     * {@code DELETE  /moderators/:id} : delete the "id" moderator.
     *
     * @param id the id of the moderator to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/moderators/{id}")
    public ResponseEntity<Void> deleteModerator(@PathVariable String id) {
        log.debug("REST request to delete Moderator : {}", id);
        moderatorRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
