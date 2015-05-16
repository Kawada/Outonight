package edu.fst.m2.ipii.outonight.service.module;

import edu.fst.m2.ipii.outonight.service.EstablishmentCacheService;
import edu.fst.m2.ipii.outonight.service.impl.EstablishmentCacheServiceImpl;

/**
 * Created by Dimitri on 10/05/2015.
 */
//@Module
public class EstablishmentServiceModule {

    //@Provides
    //@Singleton
    public EstablishmentCacheService provideEstablishmentService() {
        return EstablishmentCacheServiceImpl.getInstance();
    }
}
