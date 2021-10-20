package com.kompi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A MemberStatus.
 */
@Entity
@Table(name = "member_status")
public class MemberStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "sure")
    private Boolean sure;

    @Column(name = "booker")
    private Boolean booker;

    @Column(name = "waiting")
    private Boolean waiting;

    @Column(name = "share")
    private Integer share;

    @ManyToOne
    @JsonIgnoreProperties(value = { "gang", "event" }, allowSetters = true)
    private Member member;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MemberStatus id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getSure() {
        return this.sure;
    }

    public MemberStatus sure(Boolean sure) {
        this.setSure(sure);
        return this;
    }

    public void setSure(Boolean sure) {
        this.sure = sure;
    }

    public Boolean getBooker() {
        return this.booker;
    }

    public MemberStatus booker(Boolean booker) {
        this.setBooker(booker);
        return this;
    }

    public void setBooker(Boolean booker) {
        this.booker = booker;
    }

    public Boolean getWaiting() {
        return this.waiting;
    }

    public MemberStatus waiting(Boolean waiting) {
        this.setWaiting(waiting);
        return this;
    }

    public void setWaiting(Boolean waiting) {
        this.waiting = waiting;
    }

    public Integer getShare() {
        return this.share;
    }

    public MemberStatus share(Integer share) {
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

    public MemberStatus member(Member member) {
        this.setMember(member);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MemberStatus)) {
            return false;
        }
        return id != null && id.equals(((MemberStatus) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemberStatus{" +
            "id=" + getId() +
            ", sure='" + getSure() + "'" +
            ", booker='" + getBooker() + "'" +
            ", waiting='" + getWaiting() + "'" +
            ", share=" + getShare() +
            "}";
    }
}
