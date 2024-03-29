package inaugural.soliloquy.common;

import com.google.inject.AbstractModule;
import inaugural.soliloquy.common.factories.*;
import inaugural.soliloquy.common.infrastructure.SettingsRepoImpl;
import inaugural.soliloquy.common.persistence.*;
import soliloquy.specs.common.factories.*;
import soliloquy.specs.common.infrastructure.SettingsRepo;
import soliloquy.specs.common.persistence.PersistentValuesHandler;

public class CommonModule extends AbstractModule {
    private final ListFactory LIST_FACTORY;
    private final MapFactory MAP_FACTORY;
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER;
    private final RegistryFactory REGISTRY_FACTORY;
    private final SettingFactory SETTING_FACTORY;
    private final SettingsRepo SETTINGS_REPO;
    private final VariableCacheFactory VARIABLE_CACHE_FACTORY;

    // TODO: Refactor away from service location pattern when JDK 13 is released
    // NB: JDK 12 results in "Illegal reflective access" errors thrown by Guice; a Google support
    //     technician has stated that this error will require workarounds until JDK 13 is released,
    //     but no workarounds (in that thread or elsewhere) have worked to resolve the issue.
    //     (The issue is that refactoring to true DI instead of service location requires the new
    //     injector to produce the PersistentValuesHandler, to manage the recursive dependencies
    //     with MapHandler, CollectionHandler, etc. This may also be a code smell.)
    //     [Source: https://github.com/google/guice/issues/1133]
    public CommonModule() {
        LIST_FACTORY = new ListFactoryImpl();
        SETTING_FACTORY = new SettingFactoryImpl();

        MAP_FACTORY = new MapFactoryImpl();
        REGISTRY_FACTORY = new RegistryFactoryImpl();


        VARIABLE_CACHE_FACTORY = new VariableCacheFactoryImpl();

        PERSISTENT_VALUES_HANDLER = new PersistentValuesHandlerImpl();

        SETTINGS_REPO = new SettingsRepoImpl(PERSISTENT_VALUES_HANDLER);

        var booleanHandler = new BooleanHandler();
        var coordinate2dHandler = new Coordinate2dHandler();
        var coordinate3dHandler = new Coordinate3dHandler();
        var uuidHandler = new UuidHandler();
        var integerHandler = new IntegerHandler();
        var variableCacheHandler =
                new VariableCacheHandler(PERSISTENT_VALUES_HANDLER, VARIABLE_CACHE_FACTORY);
        var settingsRepoHandler = new SettingsRepoHandler(PERSISTENT_VALUES_HANDLER, SETTINGS_REPO);
        var stringHandler = new StringHandler();
        var listHandler = new ListHandler(PERSISTENT_VALUES_HANDLER, LIST_FACTORY);
        var mapHandler = new MapHandler(PERSISTENT_VALUES_HANDLER, MAP_FACTORY);
        var pairHandler = new PairHandler(PERSISTENT_VALUES_HANDLER);
        var registryHandler = new RegistryHandler(PERSISTENT_VALUES_HANDLER, REGISTRY_FACTORY);

        PERSISTENT_VALUES_HANDLER.addTypeHandler(booleanHandler);
        PERSISTENT_VALUES_HANDLER.addTypeHandler(coordinate2dHandler);
        PERSISTENT_VALUES_HANDLER.addTypeHandler(coordinate3dHandler);
        PERSISTENT_VALUES_HANDLER.addTypeHandler(uuidHandler);
        PERSISTENT_VALUES_HANDLER.addTypeHandler(integerHandler);
        PERSISTENT_VALUES_HANDLER.addTypeHandler(variableCacheHandler);
        PERSISTENT_VALUES_HANDLER.addTypeHandler(settingsRepoHandler);
        PERSISTENT_VALUES_HANDLER.addTypeHandler(stringHandler);
        PERSISTENT_VALUES_HANDLER.addTypeHandler(listHandler);
        PERSISTENT_VALUES_HANDLER.addTypeHandler(mapHandler);
        PERSISTENT_VALUES_HANDLER.addTypeHandler(pairHandler);
        PERSISTENT_VALUES_HANDLER.addTypeHandler(registryHandler);
    }

    @Override
    protected void configure() {
        bind(ListFactory.class).toInstance(LIST_FACTORY);
        bind(MapFactory.class).toInstance(MAP_FACTORY);
        bind(PersistentValuesHandler.class).toInstance(PERSISTENT_VALUES_HANDLER);
        bind(VariableCacheFactory.class).toInstance(VARIABLE_CACHE_FACTORY);
        bind(RegistryFactory.class).toInstance(REGISTRY_FACTORY);
        bind(SettingFactory.class).toInstance(SETTING_FACTORY);
        bind(SettingsRepo.class).toInstance(SETTINGS_REPO);
    }
}
