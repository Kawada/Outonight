package edu.fst.m2.ipii.outonight.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.ArrayList;
import java.util.List;

import edu.fst.m2.ipii.outonight.dto.type.CookingType;
import edu.fst.m2.ipii.outonight.dto.type.MusicType;

/**
 * Created by Dimitri on 09/05/2015.
 */
@Table(name = "restaurant")
public final class Restaurant extends Establishment {

    @Column(name = "cookingTypes")
    private List<CookingType> cookingTypes;

    public List<CookingType> getCookingTypes() {
        if (null == cookingTypes) {
            cookingTypes = new ArrayList<>();
        }
        return cookingTypes;
    }
}
