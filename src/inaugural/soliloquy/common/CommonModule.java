package inaugural.soliloquy.common;

import com.google.inject.AbstractModule;
import inaugural.soliloquy.common.archetypes.SettingArchetype;
import inaugural.soliloquy.common.persistentvaluetypehandlers.*;
import soliloquy.specs.common.infrastructure.*;
import soliloquy.specs.common.factories.*;

public class CommonModule extends AbstractModule {
	private ICollectionFactory _collectionFactory;
	private ICoordinateFactory _coordinateFactory;
	private IEntityUuidFactory _entityUuidFactory;
	private IGenericParamsSetFactory _genericParamsSetFactory;
	private IMapFactory _mapFactory;
	private IPairFactory _pairFactory;
	private IPersistentValuesHandler _persistentValuesHandler;
	private IPersistentVariableCacheFactory _persistentVariableCacheFactory;
	private ISettingFactory _settingFactory;
	private ISettingsRepo _settingsRepo;

	// TODO: Refactor away from service location pattern when JDK 13 is released
	// NB: JDK 12 results in "Illegal reflective access" errors thrown by Guice; a Google support
	//     technician has stated that this error will require workarounds until JDK 13 is released,
	//     but no workarounds (in that thread or elsewhere) have worked to resolve the issue.
	//     (The issue is that refactoring to true DI instead of service location requires the new
	//     injector to produce the PersistentValuesHandler, to manage the recursive dependencies
	//     with MapHandler, CollectionHandler, etc. This may also be a code smell.)
	//     [Source: https://github.com/google/guice/issues/1133]
	public CommonModule() {
		_collectionFactory = new CollectionFactory();
		_coordinateFactory = new CoordinateFactory();
		_entityUuidFactory = new EntityUuidFactory();
		_pairFactory = new PairFactory();
		_settingFactory = new SettingFactory();

		_mapFactory = new MapFactory(_pairFactory, _collectionFactory);

		_persistentVariableCacheFactory = new PersistentVariableCacheFactory(_collectionFactory,
				_mapFactory);

		_persistentValuesHandler = new PersistentValuesHandler();

		ISetting settingArchetype = new SettingArchetype();
		_settingsRepo = new SettingsRepo(_collectionFactory, _pairFactory,
				_persistentValuesHandler, settingArchetype);

		_genericParamsSetFactory = new GenericParamsSetFactory(_persistentValuesHandler,
				_mapFactory);

		IPersistentValueTypeHandler booleanHandler = new PersistentBooleanHandler();
		IPersistentValueTypeHandler coordinateHandler =
				new PersistentCoordinateHandler(_coordinateFactory);
		IPersistentValueTypeHandler entityUuidHandler =
				new PersistentEntityUuidHandler(_entityUuidFactory);
		IPersistentValueTypeHandler genericParamsSetHandler =
				new PersistentGenericParamsSetHandler(_persistentValuesHandler,
						_genericParamsSetFactory);
		IPersistentValueTypeHandler integerHandler = new PersistentIntegerHandler();
		IPersistentValueTypeHandler persistentVariableCachePersistenceHandler =
				new PersistentVariableCachePersistenceHandler(_persistentValuesHandler,
						_persistentVariableCacheFactory);
		IPersistentValueTypeHandler settingsRepoHandler =
				new PersistentSettingsRepoHandler(_persistentValuesHandler, _settingsRepo);
		IPersistentValueTypeHandler stringHandler = new PersistentStringHandler();
		IPersistentCollectionHandler collectionHandler =
				new PersistentCollectionHandler(_persistentValuesHandler, _collectionFactory);
		IPersistentMapHandler mapHandler = new PersistentMapHandler(_persistentValuesHandler,
				_mapFactory);
		IPersistentPairHandler pairHandler = new PersistentPairHandler(_persistentValuesHandler,
				_pairFactory);

		_persistentValuesHandler.addPersistentValueTypeHandler(booleanHandler);
		_persistentValuesHandler.addPersistentValueTypeHandler(coordinateHandler);
		_persistentValuesHandler.addPersistentValueTypeHandler(entityUuidHandler);
		_persistentValuesHandler.addPersistentValueTypeHandler(genericParamsSetHandler);
		_persistentValuesHandler.addPersistentValueTypeHandler(integerHandler);
		_persistentValuesHandler.addPersistentValueTypeHandler(persistentVariableCachePersistenceHandler);
		_persistentValuesHandler.addPersistentValueTypeHandler(settingsRepoHandler);
		_persistentValuesHandler.addPersistentValueTypeHandler(stringHandler);
		_persistentValuesHandler.registerPersistentCollectionHandler(collectionHandler);
		_persistentValuesHandler.registerPersistentMapHandler(mapHandler);
		_persistentValuesHandler.registerPersistentPairHandler(pairHandler);
	}
	
	@Override
	protected void configure() {
		bind(ICollectionFactory.class).toInstance(_collectionFactory);
		bind(ICoordinateFactory.class).toInstance(_coordinateFactory);
		bind(IEntityUuidFactory.class).toInstance(_entityUuidFactory);
		bind(IGenericParamsSetFactory.class).toInstance(_genericParamsSetFactory);
		bind(IMapFactory.class).toInstance(_mapFactory);
		bind(IPairFactory.class).toInstance(_pairFactory);
		bind(IPersistentValuesHandler.class).toInstance(_persistentValuesHandler);
		bind(IPersistentVariableCacheFactory.class).toInstance(_persistentVariableCacheFactory);
		bind(ISettingFactory.class).toInstance(_settingFactory);
		bind(ISettingsRepo.class).toInstance(_settingsRepo);
	}

}
