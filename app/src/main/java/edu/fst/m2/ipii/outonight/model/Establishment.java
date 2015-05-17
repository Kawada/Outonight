package edu.fst.m2.ipii.outonight.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Dimitri on 09/05/2015.
 */
@Table(name = "establishment")
public class Establishment extends Model {

    @Column(name = "establishmentId", index = true, unique = true)
    protected int establishmentId;

    @Column(name = "name", notNull = true, index = true)
    protected String name;

    @Column(name = "description")
    protected String description;

    @Column(name = "type")
    protected String type;

    @Column(name = "featured")
    protected boolean featured;

    @Column(name = "stared")
    protected boolean stared;

    @Column(name = "photo")
    protected String photo;

    @Column(name = "contact")
    protected Contact contact;

    @Column(name = "address", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    protected Address address;

    public int getEstablishmentId() {
        return establishmentId;
    }

    public void setEstablishmentId(int establishmentId) {
        this.establishmentId = establishmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public boolean isStared() {
        return stared;
    }

    public void setStared(boolean stared) {
        this.stared = stared;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Establishment that = (Establishment) o;

        return establishmentId == that.establishmentId;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + establishmentId;
        return result;
    }

    @Override
    public String toString() {
        return "Establishment{" +
                "establishmentId=" + establishmentId +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", address=" + address +
                ", featured=" + featured +
                '}';
    }

}
