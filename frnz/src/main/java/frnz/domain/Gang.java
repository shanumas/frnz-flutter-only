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
 * A Gang.
 */
@Document(collection = "gang")
public class Gang implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(min = 2)
    @Field("name")
    private String name;

    @NotNull
    @Size(min = 2)
    @Field("handle")
    private String handle;

    @Field("html_content")
    private String htmlContent;

    @Field("description")
    private String description;

    @Field("announcement")
    private String announcement;

    @Field("logo")
    private String logo;

    @DBRef
    @Field("users")
    private Set<User> users = new HashSet<>();

    @DBRef
    @Field("members")
    @JsonIgnoreProperties(value = { "events", "gangs" }, allowSetters = true)
    private Set<Member> members = new HashSet<>();

    @DBRef
    @Field("events")
    @JsonIgnoreProperties(value = { "place", "gangs", "members" }, allowSetters = true)
    private Set<Event> events = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Gang id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Gang name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHandle() {
        return this.handle;
    }

    public Gang handle(String handle) {
        this.setHandle(handle);
        return this;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getHtmlContent() {
        return this.htmlContent;
    }

    public Gang htmlContent(String htmlContent) {
        this.setHtmlContent(htmlContent);
        return this;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getDescription() {
        return this.description;
    }

    public Gang description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnnouncement() {
        return this.announcement;
    }

    public Gang announcement(String announcement) {
        this.setAnnouncement(announcement);
        return this;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public String getLogo() {
        return this.logo;
    }

    public Gang logo(String logo) {
        this.setLogo(logo);
        return this;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Gang users(Set<User> users) {
        this.setUsers(users);
        return this;
    }

    public Gang addUser(User user) {
        this.users.add(user);
        return this;
    }

    public Gang removeUser(User user) {
        this.users.remove(user);
        return this;
    }

    public Set<Member> getMembers() {
        return this.members;
    }

    public void setMembers(Set<Member> members) {
        this.members = members;
    }

    public Gang members(Set<Member> members) {
        this.setMembers(members);
        return this;
    }

    public Gang addMember(Member member) {
        this.members.add(member);
        member.getGangs().add(this);
        return this;
    }

    public Gang removeMember(Member member) {
        this.members.remove(member);
        member.getGangs().remove(this);
        return this;
    }

    public Set<Event> getEvents() {
        return this.events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public Gang events(Set<Event> events) {
        this.setEvents(events);
        return this;
    }

    public Gang addEvent(Event event) {
        this.events.add(event);
        event.getGangs().add(this);
        return this;
    }

    public Gang removeEvent(Event event) {
        this.events.remove(event);
        event.getGangs().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Gang)) {
            return false;
        }
        return id != null && id.equals(((Gang) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Gang{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", handle='" + getHandle() + "'" +
            ", htmlContent='" + getHtmlContent() + "'" +
            ", description='" + getDescription() + "'" +
            ", announcement='" + getAnnouncement() + "'" +
            ", logo='" + getLogo() + "'" +
            "}";
    }
}
