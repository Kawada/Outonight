package edu.fst.m2.ipii.outonight.ws;

import java.util.List;

import edu.fst.m2.ipii.outonight.model.Establishment;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Dimitri on 14/05/2015.
 */
public interface EstablishmentApi {

    @GET("/establishments")
    void fetchAll(Callback<List<Establishment>> establishments);

    @GET("/establishment/{id}")
    Establishment fetchOne(@Path("id") int id);

    @GET("/establishments/{type}")
    void fetchByType(@Path("type") String type, Callback<List<Establishment>> establishments);
}
