package frnz.domain;

import frnz.domain.enumeration.PlaceType;
import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Place.
 */
@Document(collection = "place")
public class Place implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(min = 2)
    @Field("name")
    private String name;

    @Field("address")
    private String address;

    @Field("type")
    private PlaceType type;

    @Field("phone")
    private String phone;

    @Field("private_place")
    private Boolean privatePlace;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Place id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Place name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public Place address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PlaceType getType() {
        return this.type;
    }

    public Place type(PlaceType type) {
        this.setType(type);
        return this;
    }

    public void setType(PlaceType type) {
        this.type = type;
    }

    public String getPhone() {
        return this.phone;
    }

    public Place phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getPrivatePlace() {
        return this.privatePlace;
    }

    public Place privatePlace(Boolean privatePlace) {
        this.setPrivatePlace(privatePlace);
        return this;
    }

    public void setPrivatePlace(Boolean privatePlace) {
        this.privatePlace = privatePlace;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Place)) {
            return false;
        }
        return id != null && id.equals(((Place) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Place{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", type='" + getType() + "'" +
            ", phone='" + getPhone() + "'" +
            ", privatePlace='" + getPrivatePlace() + "'" +
            "}";
    }
}
