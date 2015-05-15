package edu.fst.m2.ipii.outonight.service.impl;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

import edu.fst.m2.ipii.outonight.constants.WebserviceConstants;
import edu.fst.m2.ipii.outonight.dto.type.AmbienceType;
import edu.fst.m2.ipii.outonight.dto.type.CookingType;
import edu.fst.m2.ipii.outonight.dto.type.EstablishmentType;
import edu.fst.m2.ipii.outonight.dto.type.MusicType;
import edu.fst.m2.ipii.outonight.model.Bar;
import edu.fst.m2.ipii.outonight.model.Establishment;
import edu.fst.m2.ipii.outonight.model.Nightclub;
import edu.fst.m2.ipii.outonight.model.Restaurant;
import edu.fst.m2.ipii.outonight.service.EstablishmentService;
import edu.fst.m2.ipii.outonight.ws.EstablishmentApi;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Dimitri on 10/05/2015.
 */
public class EstablishmentServiceImpl implements EstablishmentService {

    private static EstablishmentService instance;

    public static EstablishmentService getInstance() {
        if (null == instance) {
            instance = new EstablishmentServiceImpl();
        }
        return instance;
    }

    private EstablishmentServiceImpl() {

    }

    @Override
    public List<Establishment> getAllCached() {
        return getAllEstablishments();
    }

    @Override
    public Establishment getCached(int id) {
        return new Select().from(Establishment.class).orderBy("name ASC").where("establishmentId = ?", id).executeSingle();
    }

    @Override
    public List<Establishment> getCachedByName(String name) {
        return new Select().from(Establishment.class).orderBy("name ASC").where("name = ?", name).execute();
    }

    @Override
    public List<Establishment> getCachedByType(String type) {

        if (type == null) {
            return getAllFeaturedEstablishments();
        }

        List<Establishment> establishments = new Select().from(Establishment.class).orderBy("name ASC").where("type = ?", type).execute();

        return  establishments;

    }


    private List<Establishment> getAllEstablishments() {
        return new Select().from(Establishment.class).orderBy("name ASC").execute();
    }

    private List<Establishment> getAllFeaturedEstablishments() {
        return new Select().from(Establishment.class).orderBy("name ASC").where("featured = ?", true).execute();
    }



}
