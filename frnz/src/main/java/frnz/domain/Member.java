package frnz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Member.
 */
@Document(collection = "member")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(min = 2)
    @Field("name")
    private String name;

    @Field("email")
    private String email;

    @Field("phone")
    private String phone;

    @Field("guest")
    private Boolean guest;

    @DBRef
    @Field("events")
    @JsonIgnoreProperties(value = { "place", "gangs", "members" }, allowSetters = true)
    private Set<Event> events = new HashSet<>();

    @DBRef
    @Field("gangs")
    @JsonIgnoreProperties(value = { "users", "members", "events" }, allowSetters = true)
    private Set<Gang> gangs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Member id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Member name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public Member email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public Member phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getGuest() {
        return this.guest;
    }

    public Member guest(Boolean guest) {
        this.setGuest(guest);
        return this;
    }

    public void setGuest(Boolean guest) {
        this.guest = guest;
    }

    public Set<Event> getEvents() {
        return this.events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public Member events(Set<Event> events) {
        this.setEvents(events);
        return this;
    }

    public Member addEvent(Event event) {
        this.events.add(event);
        event.getMembers().add(this);
        return this;
    }

    public Member removeEvent(Event event) {
        this.events.remove(event);
        event.getMembers().remove(this);
        return this;
    }

    public Set<Gang> getGangs() {
        return this.gangs;
    }

    public void setGangs(Set<Gang> gangs) {
        if (this.gangs != null) {
            this.gangs.forEach(i -> i.removeMember(this));
        }
        if (gangs != null) {
            gangs.forEach(i -> i.addMember(this));
        }
        this.gangs = gangs;
    }

    public Member gangs(Set<Gang> gangs) {
        this.setGangs(gangs);
        return this;
    }

    public Member addGang(Gang gang) {
        this.gangs.add(gang);
        gang.getMembers().add(this);
        return this;
    }

    public Member removeGang(Gang gang) {
        this.gangs.remove(gang);
        gang.getMembers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Member)) {
            return false;
        }
        return id != null && id.equals(((Member) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Member{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", guest='" + getGuest() + "'" +
            "}";
    }
}
