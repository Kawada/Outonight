package edu.fst.m2.ipii.outonight.model.ui;

import java.util.ArrayList;
import java.util.List;

import edu.fst.m2.ipii.outonight.model.Establishment;

/**
 * Created by Dimitri on 15/05/2015.
 */
public class Response<T> {
    private List<T> results = new ArrayList<>();

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
