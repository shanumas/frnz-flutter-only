package frnz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Participant.
 */
@Document(collection = "participant")
public class Participant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("sure")
    private Boolean sure;

    @Field("host")
    private Boolean host;

    @Field("booker")
    private Boolean booker;

    @Field("waiting")
    private Boolean waiting;

    @Field("share")
    private Integer share;

    @DBRef
    @Field("member")
    @JsonIgnoreProperties(value = { "gang" }, allowSetters = true)
    private Member member;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Participant id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getSure() {
        return this.sure;
    }

    public Participant sure(Boolean sure) {
        this.setSure(sure);
        return this;
    }

    public void setSure(Boolean sure) {
        this.sure = sure;
    }

    public Boolean getHost() {
        return this.host;
    }

    public Participant host(Boolean host) {
        this.setHost(host);
        return this;
    }

    public void setHost(Boolean host) {
        this.host = host;
    }

    public Boolean getBooker() {
        return this.booker;
    }

    public Participant booker(Boolean booker) {
        this.setBooker(booker);
        return this;
    }

    public void setBooker(Boolean booker) {
        this.booker = booker;
    }

    public Boolean getWaiting() {
        return this.waiting;
    }

    public Participant waiting(Boolean waiting) {
        this.setWaiting(waiting);
        return this;
    }

    public void setWaiting(Boolean waiting) {
        this.waiting = waiting;
    }

    public Integer getShare() {
        return this.share;
    }

    public Participant share(Integer share) {
        this.setShare(share);
        return this;
    }

    public void setShare(Integer share) {
        this.share = share;
    }

    public Member getMember() {
        return this.member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Participant member(Member member) {
        this.setMember(member);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Participant)) {
            return false;
        }
        return id != null && id.equals(((Participant) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Participant{" +
            "id=" + getId() +
            ", sure='" + getSure() + "'" +
            ", host='" + getHost() + "'" +
            ", booker='" + getBooker() + "'" +
            ", waiting='" + getWaiting() + "'" +
            ", share=" + getShare() +
            "}";
    }
}
