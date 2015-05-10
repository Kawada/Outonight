package edu.fst.m2.ipii.outonight.model;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.ArrayList;
import java.util.List;

import edu.fst.m2.ipii.outonight.dto.type.MusicType;

/**
 * Created by Dimitri on 09/05/2015.
 */
@Table(name = "nightclub")
public final class Nightclub extends Establishment {

    @Column(name = "musicTypes")
    private List<MusicType> musicTypes;

    public Nightclub() {
        super();
    }

    public Nightclub(String name) {
        super();
        this.name = name;
    }

    public List<MusicType> getMusicTypes() {
        if (null == musicTypes) {
            musicTypes = new ArrayList<>();
        }
        return musicTypes;
    }

}
