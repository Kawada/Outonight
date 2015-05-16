package edu.fst.m2.ipii.outonight.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import edu.fst.m2.ipii.outonight.dto.type.AmbienceType;
import edu.fst.m2.ipii.outonight.dto.type.CookingType;
import edu.fst.m2.ipii.outonight.dto.type.MusicType;

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

    // @Column(name = "photos")
    // protected List<Photo> photos;


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

    /*
    public List<Photo> getPhotos() {
        if (null == photos) {
            photos = new ArrayList<>();
        }
        return photos;
    }
    */

    public Nightclub toNightClub(List<MusicType> musicTypes) {
        Nightclub nightclub = new Nightclub();

        nightclub.setEstablishmentId(this.establishmentId);
        nightclub.setName(this.getName());
        nightclub.setDescription(this.getDescription());
        nightclub.setAddress(this.getAddress());
        nightclub.setContact(this.getContact());
        nightclub.setFeatured(this.isFeatured());
        nightclub.getMusicTypes().addAll(musicTypes);

        return nightclub;
    }

    public Bar toBar(List<AmbienceType> ambienceTypes) {
        Bar bar = new Bar();

        bar.setEstablishmentId(this.establishmentId);
        bar.setName(this.getName());
        bar.setDescription(this.getDescription());
        bar.setAddress(this.getAddress());
        bar.setContact(this.getContact());
        bar.setFeatured(this.isFeatured());
        bar.getAmbienceTypes().addAll(ambienceTypes);

        return bar;
    }

    public Restaurant toRestaurant(List<CookingType> cookingTypes) {
        Restaurant restaurant = new Restaurant();

        restaurant.setEstablishmentId(this.establishmentId);
        restaurant.setName(this.getName());
        restaurant.setDescription(this.getDescription());
        restaurant.setAddress(this.getAddress());
        restaurant.setContact(this.getContact());
        restaurant.setFeatured(this.isFeatured());
        restaurant.getCookingTypes().addAll(cookingTypes);

        return restaurant;
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

    public static class Builder<T> {
        private int establishmentId;
        private String name;
        private String description;
        private boolean featured;
        private Contact contact;
        private Address address;
        private List<Photo> photos;

        public Builder establishmentId(int establishmentId) {
            this.establishmentId = establishmentId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder featured(boolean featured) {
            this.featured = featured;
            return this;
        }

        public Builder contact(Contact contact) {
            this.contact = contact;
            return this;
        }

        public Builder address(Address address) {
            this.address = address;
            return this;
        }

        public Builder photos(List<Photo> photos) {
            this.photos = photos;
            return this;
        }

        public Establishment build(Class<? extends Establishment> clazz) throws IllegalAccessException, InstantiationException {

            Establishment establishment = clazz.newInstance();

            establishment.setName(name);
            establishment.setDescription(description);
            establishment.setFeatured(featured);
            establishment.setContact(contact);
            establishment.setAddress(address);
            establishment.setEstablishmentId(establishmentId);
            //establishment.getPhotos().addAll(photos);

            return establishment;
        }
    }
}
