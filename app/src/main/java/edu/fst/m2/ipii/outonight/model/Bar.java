package edu.fst.m2.ipii.outonight.model;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.ArrayList;
import java.util.List;

import edu.fst.m2.ipii.outonight.dto.type.AmbienceType;

/**
 * Created by Dimitri on 09/05/2015.
 */
@Table(name = "bar")
public final class Bar extends Establishment {

    @Column(name = "ambienceTypes")
    private List<AmbienceType> ambienceTypes;

    public Bar(String name) {
        super();
        this.name = name;
    }

    public Bar(String name, String description) {
        super();
        this.name = name;
        this.description = description;
    }

    public List<AmbienceType> getAmbienceTypes() {
        if (null == ambienceTypes) {
            ambienceTypes = new ArrayList<>();
        }
        return ambienceTypes;
    }
}
