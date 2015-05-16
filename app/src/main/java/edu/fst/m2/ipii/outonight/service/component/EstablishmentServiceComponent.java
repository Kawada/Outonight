package edu.fst.m2.ipii.outonight.service.component;

import edu.fst.m2.ipii.outonight.service.EstablishmentCacheService;

/**
 * Created by Dimitri on 10/05/2015.
 */
//@Singleton
//@Component(modules = {EstablishmentServiceModule.class})
public interface EstablishmentServiceComponent {
    EstablishmentCacheService provideEstablishmentService();
}
