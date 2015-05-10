package edu.fst.m2.ipii.outonight.service.component;

import javax.inject.Singleton;

import dagger.Component;
import edu.fst.m2.ipii.outonight.service.EstablishmentService;
import edu.fst.m2.ipii.outonight.service.impl.EstablishmentServiceImpl;
import edu.fst.m2.ipii.outonight.service.module.EstablishmentServiceModule;

/**
 * Created by Dimitri on 10/05/2015.
 */
//@Singleton
//@Component(modules = {EstablishmentServiceModule.class})
public interface EstablishmentServiceComponent {
    EstablishmentService provideEstablishmentService();
}
