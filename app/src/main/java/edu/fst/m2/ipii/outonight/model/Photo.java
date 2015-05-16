package edu.fst.m2.ipii.outonight.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


/**
 * Created by Dimitri on 09/05/2015.
 */
@Table(name = "photo")
public final class Photo extends Model {
    @Column(name = "path")
    private String path;
    @Column(name = "description")
    private String description;
    @Column(name = "establishment")
    private Establishment establishment;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Establishment getEstablishment() {
        return establishment;
    }

    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;
    }
}
