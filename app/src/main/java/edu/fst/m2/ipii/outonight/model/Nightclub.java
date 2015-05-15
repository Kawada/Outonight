package edu.fst.m2.ipii.outonight.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.ArrayList;
import java.util.List;

import edu.fst.m2.ipii.outonight.dto.type.MusicType;

/**
 * Created by Dimitri on 09/05/2015.
 */
@Table(name = "nightclub")
public final class Nightclub {

    @Column(name = "establishmentId", index = true)
    private int establishmentId;

    @Column(name = "name", notNull = true, index = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "featured")
    private boolean featured;

    @Column(name = "contact")
    private Contact contact;

    @Column(name = "address")
    private Address address;

    @Column(name = "photos")
    private List<Photo> photos;

    @Column(name = "musicTypes")
    private List<MusicType> musicTypes;

    public List<MusicType> getMusicTypes() {
        if (null == musicTypes) {
            musicTypes = new ArrayList<>();
        }
        return musicTypes;
    }

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

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
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


    public List<Photo> getPhotos() {
        if (null == photos) {
            photos = new ArrayList<>();
        }
        return photos;
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Nightclub nightclub = (Nightclub) o;

        return establishmentId == nightclub.establishmentId;

    }


    public int hashCode() {
        return establishmentId;
    }
}
