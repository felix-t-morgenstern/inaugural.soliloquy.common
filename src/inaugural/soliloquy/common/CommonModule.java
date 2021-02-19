package inaugural.soliloquy.common;

import com.google.inject.AbstractModule;
import inaugural.soliloquy.common.archetypes.SettingArchetype;
import inaugural.soliloquy.common.factories.*;
import inaugural.soliloquy.common.infrastructure.SettingsRepoImpl;
import inaugural.soliloquy.common.persistence.*;
import inaugural.soliloquy.common.persistence.PersistentListHandlerImpl;
import inaugural.soliloquy.common.persistence.PersistentMapHandlerImpl;
import inaugural.soliloquy.common.persistence.PersistentPairHandlerImpl;
import soliloquy.specs.common.infrastructure.*;
import soliloquy.specs.common.factories.*;
import soliloquy.specs.common.persistence.PersistentRegistryHandler;
import soliloquy.specs.common.persistence.PersistentValueTypeHandler;
import soliloquy.specs.common.persistence.PersistentValuesHandler;

public class CommonModule extends AbstractModule {
    private ListFactory _listFactory;
    private CoordinateFactory _coordinateFactory;
    private EntityUuidFactory _entityUuidFactory;
    private MapFactory _mapFactory;
    private PairFactory _pairFactory;
    private PersistentValuesHandler _persistentValuesHandler;
    private RegistryFactory _registryFactory;
    private SettingFactory _settingFactory;
    private SettingsRepo _settingsRepo;
    private VariableCacheFactory _variableCacheFactory;

    // TODO: Refactor away from service location pattern when JDK 13 is released
    // NB: JDK 12 results in "Illegal reflective access" errors thrown by Guice; a Google support
    //     technician has stated that this error will require workarounds until JDK 13 is released,
    //     but no workarounds (in that thread or elsewhere) have worked to resolve the issue.
    //     (The issue is that refactoring to true DI instead of service location requires the new
    //     injector to produce the PersistentValuesHandler, to manage the recursive dependencies
    //     with MapHandler, CollectionHandler, etc. This may also be a code smell.)
    //     [Source: https://github.com/google/guice/issues/1133]
    public CommonModule() {
        _listFactory = new ListFactoryImpl();
        _coordinateFactory = new CoordinateFactoryImpl();
        _entityUuidFactory = new EntityUuidFactoryImpl();
        _pairFactory = new PairFactoryImpl();
        _settingFactory = new SettingFactoryImpl();

        _mapFactory = new MapFactoryImpl(_listFactory);
        _registryFactory = new RegistryFactoryImpl(_listFactory);


        _variableCacheFactory = new VariableCacheFactoryImpl(_listFactory,
                _mapFactory);

        _persistentValuesHandler = new PersistentValuesHandlerImpl();

        Setting settingArchetype = new SettingArchetype();
        _settingsRepo = new SettingsRepoImpl(_listFactory, _pairFactory,
                _persistentValuesHandler, settingArchetype);

        PersistentValueTypeHandler booleanHandler = new PersistentBooleanHandler();
        PersistentValueTypeHandler coordinateHandler =
                new PersistentCoordinateHandler(_coordinateFactory);
        PersistentValueTypeHandler entityUuidHandler =
                new PersistentEntityUuidHandler(_entityUuidFactory);
        PersistentValueTypeHandler integerHandler = new PersistentIntegerHandler();
        PersistentValueTypeHandler variableCachePersistenceHandler =
                new PersistentVariableCacheHandler(_persistentValuesHandler,
                        _variableCacheFactory);
        PersistentValueTypeHandler settingsRepoHandler =
                new PersistentSettingsRepoHandler(_persistentValuesHandler, _settingsRepo);
        PersistentValueTypeHandler stringHandler = new PersistentStringHandler();
        PersistentListHandlerImpl listHandler =
                new PersistentListHandlerImpl(_persistentValuesHandler, _listFactory);
        PersistentMapHandlerImpl mapHandler = new PersistentMapHandlerImpl(_persistentValuesHandler,
                _mapFactory);
        PersistentPairHandlerImpl pairHandler = new PersistentPairHandlerImpl(_persistentValuesHandler,
                _pairFactory);
        PersistentRegistryHandler registryHandler = new PersistentRegistryHandlerImpl(
                _persistentValuesHandler, _registryFactory);

        _persistentValuesHandler.addPersistentValueTypeHandler(booleanHandler);
        _persistentValuesHandler.addPersistentValueTypeHandler(coordinateHandler);
        _persistentValuesHandler.addPersistentValueTypeHandler(entityUuidHandler);
        _persistentValuesHandler.addPersistentValueTypeHandler(integerHandler);
        _persistentValuesHandler.addPersistentValueTypeHandler(variableCachePersistenceHandler);
        _persistentValuesHandler.addPersistentValueTypeHandler(settingsRepoHandler);
        _persistentValuesHandler.addPersistentValueTypeHandler(stringHandler);
        _persistentValuesHandler.registerPersistentListHandler(listHandler);
        _persistentValuesHandler.registerPersistentMapHandler(mapHandler);
        _persistentValuesHandler.registerPersistentPairHandler(pairHandler);
        _persistentValuesHandler.registerPersistentRegistryHandler(registryHandler);
    }

    @Override
    protected void configure() {
        bind(CoordinateFactory.class).toInstance(_coordinateFactory);
        bind(EntityUuidFactory.class).toInstance(_entityUuidFactory);
        bind(ListFactory.class).toInstance(_listFactory);
        bind(MapFactory.class).toInstance(_mapFactory);
        bind(PairFactory.class).toInstance(_pairFactory);
        bind(PersistentValuesHandler.class).toInstance(_persistentValuesHandler);
        bind(VariableCacheFactory.class).toInstance(_variableCacheFactory);
        bind(RegistryFactory.class).toInstance(_registryFactory);
        bind(SettingFactory.class).toInstance(_settingFactory);
        bind(SettingsRepo.class).toInstance(_settingsRepo);
    }
}
