package frnz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import frnz.domain.enumeration.EventType;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Event.
 */
@Document(collection = "event")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("type")
    private EventType type;

    @Field("date")
    private Instant date;

    @Field("name")
    private String name;

    @Field("start_time")
    private String startTime;

    @Field("end_time")
    private String endTime;

    @Field("nonmembers")
    private String nonmembers = ";;;;";

    @Field("confirmed")
    private Boolean confirmed;

    @Field("cancelled")
    private Boolean cancelled;

    @Field("recurring")
    private Boolean recurring;

    @Field("minimum")
    private Integer minimum;

    @Field("maximum")
    private Integer maximum;

    @Field("book_limit")
    private Integer bookLimit;

    @Field("cost")
    private Float cost;

    @Field("share")
    private Float share;

    @DBRef
    @Field("place")
    private Place place;

    @DBRef
    @Field("participant")
    @JsonIgnoreProperties(value = { "member" }, allowSetters = true)
    private Participant participant;

    @DBRef
    @Field("gang")
    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    private Gang gang;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Event id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EventType getType() {
        return this.type;
    }

    public Event type(EventType type) {
        this.setType(type);
        return this;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public Instant getDate() {
        return this.date;
    }

    public Event date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getName() {
        return this.name;
    }

    public Event name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public Event startTime(String startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public Event endTime(String endTime) {
        this.setEndTime(endTime);
        return this;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getNonmembers() {
        return this.nonmembers;
    }

    public Event nonmembers(String nonmembers) {
        this.setNonmembers(nonmembers);
        return this;
    }

    public void setNonmembers(String nonmembers) {
        this.nonmembers = nonmembers;
    }

    public Boolean getConfirmed() {
        return this.confirmed;
    }

    public Event confirmed(Boolean confirmed) {
        this.setConfirmed(confirmed);
        return this;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Boolean getCancelled() {
        return this.cancelled;
    }

    public Event cancelled(Boolean cancelled) {
        this.setCancelled(cancelled);
        return this;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Boolean getRecurring() {
        return this.recurring;
    }

    public Event recurring(Boolean recurring) {
        this.setRecurring(recurring);
        return this;
    }

    public void setRecurring(Boolean recurring) {
        this.recurring = recurring;
    }

    public Integer getMinimum() {
        return this.minimum;
    }

    public Event minimum(Integer minimum) {
        this.setMinimum(minimum);
        return this;
    }

    public void setMinimum(Integer minimum) {
        this.minimum = minimum;
    }

    public Integer getMaximum() {
        return this.maximum;
    }

    public Event maximum(Integer maximum) {
        this.setMaximum(maximum);
        return this;
    }

    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }

    public Integer getBookLimit() {
        return this.bookLimit;
    }

    public Event bookLimit(Integer bookLimit) {
        this.setBookLimit(bookLimit);
        return this;
    }

    public void setBookLimit(Integer bookLimit) {
        this.bookLimit = bookLimit;
    }

    public Float getCost() {
        return this.cost;
    }

    public Event cost(Float cost) {
        this.setCost(cost);
        return this;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Float getShare() {
        return this.share;
    }

    public Event share(Float share) {
        this.setShare(share);
        return this;
    }

    public void setShare(Float share) {
        this.share = share;
    }

    public Place getPlace() {
        return this.place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Event place(Place place) {
        this.setPlace(place);
        return this;
    }

    public Participant getParticipant() {
        return this.participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Event participant(Participant participant) {
        this.setParticipant(participant);
        return this;
    }

    public Gang getGang() {
        return this.gang;
    }

    public void setGang(Gang gang) {
        this.gang = gang;
    }

    public Event gang(Gang gang) {
        this.setGang(gang);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        return id != null && id.equals(((Event) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Event{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", date='" + getDate() + "'" +
            ", name='" + getName() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", nonmembers='" + getNonmembers() + "'" +
            ", confirmed='" + getConfirmed() + "'" +
            ", cancelled='" + getCancelled() + "'" +
            ", recurring='" + getRecurring() + "'" +
            ", minimum=" + getMinimum() +
            ", maximum=" + getMaximum() +
            ", bookLimit=" + getBookLimit() +
            ", cost=" + getCost() +
            ", share=" + getShare() +
            "}";
    }
}
