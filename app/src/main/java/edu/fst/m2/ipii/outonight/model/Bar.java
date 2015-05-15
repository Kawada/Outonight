package edu.fst.m2.ipii.outonight.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.ArrayList;
import java.util.List;

import edu.fst.m2.ipii.outonight.dto.type.AmbienceType;

/**
 * Created by Dimitri on 09/05/2015.
 */
@Table(name = "bar")
public final class Bar {

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

    @Column(name = "ambienceTypes")
    private List<AmbienceType> ambienceTypes;



    
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

    public List<AmbienceType> getAmbienceTypes() {
        if (null == ambienceTypes) {
            ambienceTypes = new ArrayList<>();
        }
        return ambienceTypes;
    }

    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar bar = (Bar) o;

        return establishmentId == bar.establishmentId;

    }

    
    public int hashCode() {
        return establishmentId;
    }
}
