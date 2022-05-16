package DI;

import dagger.Module;
import dagger.Provides;
import es.diadam.diadam.services.Storage;

import javax.inject.Singleton;

@Module
public class StorageModule {
    @Singleton
    @Provides
    public Storage provideStorage() {
        return new Storage();
    }
}
