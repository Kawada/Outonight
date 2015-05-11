package edu.fst.m2.ipii.outonight.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dimitri on 09/05/2015.
 */
public abstract class Establishment extends Model {

    @Column(name = "name", notNull = true, index = true)
    protected String name;

    @Column(name = "description")
    protected String description;

    @Column(name = "featured")
    protected boolean featured;

    @Column(name = "contact")
    protected Contact contact;

    @Column(name = "address")
    protected Address address;

    @Column(name = "photos")
    protected List<Photo> photos;

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
}
