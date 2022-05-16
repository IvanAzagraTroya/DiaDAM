package DI;

import dagger.Component;
import es.diadam.diadam.controllers.InterfazClienteController;
import es.diadam.diadam.repositories.ProductosRepository;

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
    ProductosRepository build();

    void inject(InterfazClienteController interfazClienteController);
}
