package es.diadam.diadam.DI;

import dagger.Module;
import dagger.Provides;
import es.diadam.diadam.managers.ManagerBBDD;

import javax.inject.Singleton;

@Module
public class DataBaseModule {

    @Singleton
    @Provides
    public ManagerBBDD provideDataBase() {
        return ManagerBBDD.getInstance();
    }
}
