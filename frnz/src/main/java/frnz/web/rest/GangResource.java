package frnz.web.rest;

import frnz.domain.Gang;
import frnz.repository.GangRepository;
import frnz.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link frnz.domain.Gang}.
 */
@RestController
@RequestMapping("/api")
public class GangResource {

    private final Logger log = LoggerFactory.getLogger(GangResource.class);

    private static final String ENTITY_NAME = "gang";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GangRepository gangRepository;

    public GangResource(GangRepository gangRepository) {
        this.gangRepository = gangRepository;
    }

    /**
     * {@code POST  /gangs} : Create a new gang.
     *
     * @param gang the gang to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gang, or with status {@code 400 (Bad Request)} if the gang has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gangs")
    public ResponseEntity<Gang> createGang(@Valid @RequestBody Gang gang) throws URISyntaxException {
        log.debug("REST request to save Gang : {}", gang);
        if (gang.getId() != null) {
            throw new BadRequestAlertException("A new gang cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Gang result = gangRepository.save(gang);
        return ResponseEntity
            .created(new URI("/api/gangs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /gangs/:id} : Updates an existing gang.
     *
     * @param id the id of the gang to save.
     * @param gang the gang to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gang,
     * or with status {@code 400 (Bad Request)} if the gang is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gang couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gangs/{id}")
    public ResponseEntity<Gang> updateGang(@PathVariable(value = "id", required = false) final String id, @Valid @RequestBody Gang gang)
        throws URISyntaxException {
        log.debug("REST request to update Gang : {}, {}", id, gang);
        if (gang.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gang.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gangRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Gang result = gangRepository.save(gang);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gang.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /gangs/:id} : Partial updates given fields of an existing gang, field will ignore if it is null
     *
     * @param id the id of the gang to save.
     * @param gang the gang to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gang,
     * or with status {@code 400 (Bad Request)} if the gang is not valid,
     * or with status {@code 404 (Not Found)} if the gang is not found,
     * or with status {@code 500 (Internal Server Error)} if the gang couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/gangs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Gang> partialUpdateGang(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody Gang gang
    ) throws URISyntaxException {
        log.debug("REST request to partial update Gang partially : {}, {}", id, gang);
        if (gang.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gang.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gangRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Gang> result = gangRepository
            .findById(gang.getId())
            .map(existingGang -> {
                if (gang.getName() != null) {
                    existingGang.setName(gang.getName());
                }
                if (gang.getHandle() != null) {
                    existingGang.setHandle(gang.getHandle());
                }
                if (gang.getHtmlContent() != null) {
                    existingGang.setHtmlContent(gang.getHtmlContent());
                }
                if (gang.getDescription() != null) {
                    existingGang.setDescription(gang.getDescription());
                }
                if (gang.getAnnouncement() != null) {
                    existingGang.setAnnouncement(gang.getAnnouncement());
                }
                if (gang.getLogo() != null) {
                    existingGang.setLogo(gang.getLogo());
                }

                return existingGang;
            })
            .map(gangRepository::save);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gang.getId()));
    }

    /**
     * {@code GET  /gangs} : get all the gangs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gangs in body.
     */
    @GetMapping("/gangs")
    public List<Gang> getAllGangs() {
        log.debug("REST request to get all Gangs");
        return gangRepository.findAll();
    }

    /**
     * {@code GET  /gangs/:id} : get the "id" gang.
     *
     * @param id the id of the gang to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gang, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gangs/{id}")
    public ResponseEntity<Gang> getGang(@PathVariable String id) {
        log.debug("REST request to get Gang : {}", id);
        Optional<Gang> gang = gangRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gang);
    }

    /**
     * {@code DELETE  /gangs/:id} : delete the "id" gang.
     *
     * @param id the id of the gang to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gangs/{id}")
    public ResponseEntity<Void> deleteGang(@PathVariable String id) {
        log.debug("REST request to delete Gang : {}", id);
        gangRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
