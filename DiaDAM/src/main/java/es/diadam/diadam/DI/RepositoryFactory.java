package es.diadam.diadam.DI;

import dagger.Component;
import es.diadam.diadam.controllers.InterfazClienteController;
import es.diadam.diadam.repositories.ProductoRepository;

import javax.inject.Singleton;

/**
 * @author Iván Azagra
 */
@Singleton
@Component(modules = {
        DataBaseModule.class,
        StorageModule.class
})

public interface RepositoryFactory {
    ProductoRepository build();

    void inject(InterfazClienteController interfazClienteController);
}
