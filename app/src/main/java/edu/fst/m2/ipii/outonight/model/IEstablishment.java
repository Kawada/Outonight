package edu.fst.m2.ipii.outonight.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dimitri on 15/05/2015.
 */
public interface IEstablishment {

    int getEstablishmentId();

    void setEstablishmentId(int establishmentId);

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    boolean isFeatured();

    void setFeatured(boolean featured);

    Contact getContact();

    void setContact(Contact contact);

    Address getAddress();

    void setAddress(Address address);

    List<Photo> getPhotos();
}
