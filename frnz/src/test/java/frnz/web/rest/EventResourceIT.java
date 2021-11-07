package frnz.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import frnz.IntegrationTest;
import frnz.domain.Event;
import frnz.domain.enumeration.EventType;
import frnz.repository.EventRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link EventResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EventResourceIT {

    private static final EventType DEFAULT_TYPE = EventType.SOCIAL;
    private static final EventType UPDATED_TYPE = EventType.SPORT;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_START_TIME = "AAAAAAAAAA";
    private static final String UPDATED_START_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_END_TIME = "AAAAAAAAAA";
    private static final String UPDATED_END_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_NONMEMBERS = "AAAAAAAAAA";
    private static final String UPDATED_NONMEMBERS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CONFIRMED = false;
    private static final Boolean UPDATED_CONFIRMED = true;

    private static final Boolean DEFAULT_CANCELLED = false;
    private static final Boolean UPDATED_CANCELLED = true;

    private static final Integer DEFAULT_MINIMUM = 1;
    private static final Integer UPDATED_MINIMUM = 2;

    private static final Integer DEFAULT_MAXIMUM = 1;
    private static final Integer UPDATED_MAXIMUM = 2;

    private static final Integer DEFAULT_IDEAL = 1;
    private static final Integer UPDATED_IDEAL = 2;

    private static final Float DEFAULT_COST = 1F;
    private static final Float UPDATED_COST = 2F;

    private static final Float DEFAULT_SHARE = 1F;
    private static final Float UPDATED_SHARE = 2F;

    private static final String ENTITY_API_URL = "/api/events";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private MockMvc restEventMockMvc;

    private Event event;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Event createEntity() {
        Event event = new Event()
            .type(DEFAULT_TYPE)
            .date(DEFAULT_DATE)
            .name(DEFAULT_NAME)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .nonmembers(DEFAULT_NONMEMBERS)
            .confirmed(DEFAULT_CONFIRMED)
            .cancelled(DEFAULT_CANCELLED)
            .minimum(DEFAULT_MINIMUM)
            .maximum(DEFAULT_MAXIMUM)
            .ideal(DEFAULT_IDEAL)
            .cost(DEFAULT_COST)
            .share(DEFAULT_SHARE);
        return event;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Event createUpdatedEntity() {
        Event event = new Event()
            .type(UPDATED_TYPE)
            .date(UPDATED_DATE)
            .name(UPDATED_NAME)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .nonmembers(UPDATED_NONMEMBERS)
            .confirmed(UPDATED_CONFIRMED)
            .cancelled(UPDATED_CANCELLED)
            .minimum(UPDATED_MINIMUM)
            .maximum(UPDATED_MAXIMUM)
            .ideal(UPDATED_IDEAL)
            .cost(UPDATED_COST)
            .share(UPDATED_SHARE);
        return event;
    }

    @BeforeEach
    public void initTest() {
        eventRepository.deleteAll();
        event = createEntity();
    }

    @Test
    void createEvent() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().size();
        // Create the Event
        restEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(event)))
            .andExpect(status().isCreated());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate + 1);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEvent.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testEvent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEvent.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testEvent.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testEvent.getNonmembers()).isEqualTo(DEFAULT_NONMEMBERS);
        assertThat(testEvent.getConfirmed()).isEqualTo(DEFAULT_CONFIRMED);
        assertThat(testEvent.getCancelled()).isEqualTo(DEFAULT_CANCELLED);
        assertThat(testEvent.getMinimum()).isEqualTo(DEFAULT_MINIMUM);
        assertThat(testEvent.getMaximum()).isEqualTo(DEFAULT_MAXIMUM);
        assertThat(testEvent.getIdeal()).isEqualTo(DEFAULT_IDEAL);
        assertThat(testEvent.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testEvent.getShare()).isEqualTo(DEFAULT_SHARE);
    }

    @Test
    void createEventWithExistingId() throws Exception {
        // Create the Event with an existing ID
        event.setId("existing_id");

        int databaseSizeBeforeCreate = eventRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(event)))
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllEvents() throws Exception {
        // Initialize the database
        eventRepository.save(event);

        // Get all the eventList
        restEventMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(event.getId())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME)))
            .andExpect(jsonPath("$.[*].nonmembers").value(hasItem(DEFAULT_NONMEMBERS)))
            .andExpect(jsonPath("$.[*].confirmed").value(hasItem(DEFAULT_CONFIRMED.booleanValue())))
            .andExpect(jsonPath("$.[*].cancelled").value(hasItem(DEFAULT_CANCELLED.booleanValue())))
            .andExpect(jsonPath("$.[*].minimum").value(hasItem(DEFAULT_MINIMUM)))
            .andExpect(jsonPath("$.[*].maximum").value(hasItem(DEFAULT_MAXIMUM)))
            .andExpect(jsonPath("$.[*].ideal").value(hasItem(DEFAULT_IDEAL)))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].share").value(hasItem(DEFAULT_SHARE.doubleValue())));
    }

    @Test
    void getEvent() throws Exception {
        // Initialize the database
        eventRepository.save(event);

        // Get the event
        restEventMockMvc
            .perform(get(ENTITY_API_URL_ID, event.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(event.getId()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME))
            .andExpect(jsonPath("$.nonmembers").value(DEFAULT_NONMEMBERS))
            .andExpect(jsonPath("$.confirmed").value(DEFAULT_CONFIRMED.booleanValue()))
            .andExpect(jsonPath("$.cancelled").value(DEFAULT_CANCELLED.booleanValue()))
            .andExpect(jsonPath("$.minimum").value(DEFAULT_MINIMUM))
            .andExpect(jsonPath("$.maximum").value(DEFAULT_MAXIMUM))
            .andExpect(jsonPath("$.ideal").value(DEFAULT_IDEAL))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.doubleValue()))
            .andExpect(jsonPath("$.share").value(DEFAULT_SHARE.doubleValue()));
    }

    @Test
    void getNonExistingEvent() throws Exception {
        // Get the event
        restEventMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewEvent() throws Exception {
        // Initialize the database
        eventRepository.save(event);

        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Update the event
        Event updatedEvent = eventRepository.findById(event.getId()).get();
        updatedEvent
            .type(UPDATED_TYPE)
            .date(UPDATED_DATE)
            .name(UPDATED_NAME)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .nonmembers(UPDATED_NONMEMBERS)
            .confirmed(UPDATED_CONFIRMED)
            .cancelled(UPDATED_CANCELLED)
            .minimum(UPDATED_MINIMUM)
            .maximum(UPDATED_MAXIMUM)
            .ideal(UPDATED_IDEAL)
            .cost(UPDATED_COST)
            .share(UPDATED_SHARE);

        restEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEvent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEvent))
            )
            .andExpect(status().isOk());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEvent.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEvent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEvent.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testEvent.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testEvent.getNonmembers()).isEqualTo(UPDATED_NONMEMBERS);
        assertThat(testEvent.getConfirmed()).isEqualTo(UPDATED_CONFIRMED);
        assertThat(testEvent.getCancelled()).isEqualTo(UPDATED_CANCELLED);
        assertThat(testEvent.getMinimum()).isEqualTo(UPDATED_MINIMUM);
        assertThat(testEvent.getMaximum()).isEqualTo(UPDATED_MAXIMUM);
        assertThat(testEvent.getIdeal()).isEqualTo(UPDATED_IDEAL);
        assertThat(testEvent.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testEvent.getShare()).isEqualTo(UPDATED_SHARE);
    }

    @Test
    void putNonExistingEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, event.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(event))
            )
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(event))
            )
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(event)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateEventWithPatch() throws Exception {
        // Initialize the database
        eventRepository.save(event);

        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Update the event using partial update
        Event partialUpdatedEvent = new Event();
        partialUpdatedEvent.setId(event.getId());

        partialUpdatedEvent.date(UPDATED_DATE).cancelled(UPDATED_CANCELLED).cost(UPDATED_COST).share(UPDATED_SHARE);

        restEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvent))
            )
            .andExpect(status().isOk());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEvent.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEvent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEvent.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testEvent.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testEvent.getNonmembers()).isEqualTo(DEFAULT_NONMEMBERS);
        assertThat(testEvent.getConfirmed()).isEqualTo(DEFAULT_CONFIRMED);
        assertThat(testEvent.getCancelled()).isEqualTo(UPDATED_CANCELLED);
        assertThat(testEvent.getMinimum()).isEqualTo(DEFAULT_MINIMUM);
        assertThat(testEvent.getMaximum()).isEqualTo(DEFAULT_MAXIMUM);
        assertThat(testEvent.getIdeal()).isEqualTo(DEFAULT_IDEAL);
        assertThat(testEvent.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testEvent.getShare()).isEqualTo(UPDATED_SHARE);
    }

    @Test
    void fullUpdateEventWithPatch() throws Exception {
        // Initialize the database
        eventRepository.save(event);

        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Update the event using partial update
        Event partialUpdatedEvent = new Event();
        partialUpdatedEvent.setId(event.getId());

        partialUpdatedEvent
            .type(UPDATED_TYPE)
            .date(UPDATED_DATE)
            .name(UPDATED_NAME)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .nonmembers(UPDATED_NONMEMBERS)
            .confirmed(UPDATED_CONFIRMED)
            .cancelled(UPDATED_CANCELLED)
            .minimum(UPDATED_MINIMUM)
            .maximum(UPDATED_MAXIMUM)
            .ideal(UPDATED_IDEAL)
            .cost(UPDATED_COST)
            .share(UPDATED_SHARE);

        restEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvent))
            )
            .andExpect(status().isOk());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEvent.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEvent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEvent.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testEvent.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testEvent.getNonmembers()).isEqualTo(UPDATED_NONMEMBERS);
        assertThat(testEvent.getConfirmed()).isEqualTo(UPDATED_CONFIRMED);
        assertThat(testEvent.getCancelled()).isEqualTo(UPDATED_CANCELLED);
        assertThat(testEvent.getMinimum()).isEqualTo(UPDATED_MINIMUM);
        assertThat(testEvent.getMaximum()).isEqualTo(UPDATED_MAXIMUM);
        assertThat(testEvent.getIdeal()).isEqualTo(UPDATED_IDEAL);
        assertThat(testEvent.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testEvent.getShare()).isEqualTo(UPDATED_SHARE);
    }

    @Test
    void patchNonExistingEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, event.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(event))
            )
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(event))
            )
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(event)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteEvent() throws Exception {
        // Initialize the database
        eventRepository.save(event);

        int databaseSizeBeforeDelete = eventRepository.findAll().size();

        // Delete the event
        restEventMockMvc
            .perform(delete(ENTITY_API_URL_ID, event.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
