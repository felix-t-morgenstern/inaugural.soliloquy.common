package inaugural.soliloquy.common;

import com.google.inject.AbstractModule;
import inaugural.soliloquy.common.archetypes.SettingArchetype;
import inaugural.soliloquy.common.factories.*;
import inaugural.soliloquy.common.infrastructure.SettingsRepoImpl;
import inaugural.soliloquy.common.persistence.*;
import inaugural.soliloquy.common.persistence.ListHandler;
import inaugural.soliloquy.common.persistence.MapHandler;
import inaugural.soliloquy.common.persistence.PairHandler;
import soliloquy.specs.common.infrastructure.*;
import soliloquy.specs.common.factories.*;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.common.valueobjects.EntityUuid;

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
        _registryFactory = new RegistryFactoryImpl();


        _variableCacheFactory = new VariableCacheFactoryImpl();

        _persistentValuesHandler = new PersistentValuesHandlerImpl();

        //noinspection rawtypes
        Setting settingArchetype = new SettingArchetype();
        _settingsRepo = new SettingsRepoImpl(_listFactory, _pairFactory,
                _persistentValuesHandler, settingArchetype);

        TypeHandler<Boolean> booleanHandler = new BooleanHandler();
        TypeHandler<Coordinate> coordinateHandler = new CoordinateHandler(_coordinateFactory);
        TypeHandler<EntityUuid> entityUuidHandler = new EntityUuidHandler(_entityUuidFactory);
        TypeHandler<Integer> integerHandler = new IntegerHandler();
        TypeHandler<VariableCache> variableCacheHandler = new VariableCacheHandler(
                _persistentValuesHandler, _variableCacheFactory);
        TypeHandler<SettingsRepo> settingsRepoHandler = new SettingsRepoHandler(
                _persistentValuesHandler, _settingsRepo);
        TypeHandler<String> stringHandler = new StringHandler();
        //noinspection rawtypes
        TypeHandler<List> listHandler = new ListHandler(_persistentValuesHandler, _listFactory);
        //noinspection rawtypes
        TypeHandler<Map> mapHandler = new MapHandler(_persistentValuesHandler, _mapFactory);
        //noinspection rawtypes
        TypeHandler<Pair> pairHandler = new PairHandler(_persistentValuesHandler, _pairFactory);
        //noinspection rawtypes
        TypeHandler<Registry> registryHandler = new RegistryHandler(
                _persistentValuesHandler, _registryFactory);

        _persistentValuesHandler.addTypeHandler(booleanHandler);
        _persistentValuesHandler.addTypeHandler(coordinateHandler);
        _persistentValuesHandler.addTypeHandler(entityUuidHandler);
        _persistentValuesHandler.addTypeHandler(integerHandler);
        _persistentValuesHandler.addTypeHandler(variableCacheHandler);
        _persistentValuesHandler.addTypeHandler(settingsRepoHandler);
        _persistentValuesHandler.addTypeHandler(stringHandler);
        _persistentValuesHandler.addTypeHandler(listHandler);
        _persistentValuesHandler.addTypeHandler(mapHandler);
        _persistentValuesHandler.addTypeHandler(pairHandler);
        _persistentValuesHandler.addTypeHandler(registryHandler);
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
