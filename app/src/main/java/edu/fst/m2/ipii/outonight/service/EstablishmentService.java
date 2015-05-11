package edu.fst.m2.ipii.outonight.service;

import java.util.List;

import edu.fst.m2.ipii.outonight.dto.type.EstablishmentType;
import edu.fst.m2.ipii.outonight.model.Establishment;

/**
 * Created by Dimitri on 10/05/2015.
 */
public interface EstablishmentService {

    List<Establishment> getAll();

    Establishment get(int id);

    List<Establishment> getByName(String name);

    List<Establishment> getByType(String type);
}
