package edu.fst.m2.ipii.outonight.service.impl;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.fst.m2.ipii.outonight.dto.type.EstablishmentType;
import edu.fst.m2.ipii.outonight.model.Bar;
import edu.fst.m2.ipii.outonight.model.Establishment;
import edu.fst.m2.ipii.outonight.model.Nightclub;
import edu.fst.m2.ipii.outonight.model.Restaurant;
import edu.fst.m2.ipii.outonight.service.EstablishmentService;

/**
 * Created by Dimitri on 10/05/2015.
 */
public class EstablishmentServiceImpl implements EstablishmentService {

    List<Establishment> establishments = new ArrayList<Establishment>() {{

        boolean featured;

        for (int i = 0; i < 25; i++) {
            try {

                featured = i % 10 == 0;

            add(new Nightclub.Builder<Nightclub>()
                    .name("Nightclub factice " + i)
                    .description("Un nightclub factice. Position numéro " + i)
                    .featured(featured)
                    .build(Nightclub.class));

            add(new Restaurant.Builder<Restaurant>()
                    .name("Restaurant factice " + i)
                    .description("Un restaurant factice. Position numéro " + i)
                    .featured(featured)
                    .build(Restaurant.class));

            add(new Bar.Builder<Bar>()
                    .name("Bar factice " + i)
                    .description("Un bar factice. Position numéro " + i)
                    .featured(featured)
                    .build(Bar.class));

            } catch (IllegalAccessException | InstantiationException e) {
                Log.e("EstablishmentService", "erreur lors de l'ajout d'établissement", e);
            }

        }

    }};

    @Override
    public List<Establishment> getAll() {
        return establishments;
    }

    @Override
    public Establishment get(int id) {
        return establishments.get(id);
    }

    @Override
    public List<Establishment> getByName(String name) {

        List<Establishment> returnedEstablishments = new ArrayList<>();

        for(Establishment cursor : establishments) {
            if (cursor.getName().equals(name)) {
                returnedEstablishments.add(cursor);
            }
        }

        return returnedEstablishments;
    }

    @Override
    public List<Establishment> getByType(String type) {

        Class targetedClass = Establishment.class;

        if (EstablishmentType.BARS.equals(type)) {
            targetedClass = Bar.class;
        }
        else if (EstablishmentType.RESTAURANTS.equals(type)) {
            targetedClass = Restaurant.class;
        }
        else if (EstablishmentType.NIGHTCLUBS.equals(type)) {
            targetedClass = Nightclub.class;
        }

        List<Establishment> returnedEstablishments = new ArrayList<>();

        for(Establishment cursor : establishments) {
            if ((cursor.getClass().equals(targetedClass)) || (Establishment.class.equals(targetedClass) && cursor.isFeatured())) {
                returnedEstablishments.add(cursor);
            }
        }

        return returnedEstablishments;
    }
}
