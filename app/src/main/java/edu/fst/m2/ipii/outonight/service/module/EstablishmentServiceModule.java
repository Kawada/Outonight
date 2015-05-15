package edu.fst.m2.ipii.outonight.service.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.fst.m2.ipii.outonight.service.EstablishmentService;
import edu.fst.m2.ipii.outonight.service.component.EstablishmentServiceComponent;
import edu.fst.m2.ipii.outonight.service.impl.EstablishmentServiceImpl;

/**
 * Created by Dimitri on 10/05/2015.
 */
//@Module
public class EstablishmentServiceModule {

    //@Provides
    //@Singleton
    public EstablishmentService provideEstablishmentService() {
        return EstablishmentServiceImpl.getInstance();
    }
}
