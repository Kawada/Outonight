package edu.fst.m2.ipii.outonight.service;

import java.util.List;

import edu.fst.m2.ipii.outonight.model.Establishment;

/**
 * Created by Dimitri on 10/05/2015.
 */
public interface EstablishmentCacheService {

    List<Establishment> getAllCached();

    Establishment getCached(int id);

    List<Establishment> getCachedByName(String name);

    List<Establishment> getCachedByType(String type);

}
