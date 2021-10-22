package frnz.domain;

import java.io.Serializable;
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
    @Field("user")
    private User user;

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

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Gang user(User user) {
        this.setUser(user);
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
