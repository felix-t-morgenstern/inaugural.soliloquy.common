package inaugural.soliloquy.common;

import com.google.inject.AbstractModule;
import inaugural.soliloquy.common.persistentvaluetypehandlers.*;
import soliloquy.common.specs.*;

public class CommonModule extends AbstractModule {
	private ICollectionFactory _collectionFactory;
	private ICoordinateFactory _coordinateFactory;
	private IEntityUuidFactory _entityUuidFactory;
	private IGenericParamsSetFactory _genericParamsSetFactory;
	private IMapFactory _mapFactory;
	private IPairFactory _pairFactory;
	private IPersistentValuesHandler _persistentValuesHandler;
	private IPersistentVariableFactory _persistentVariableFactory;
	private ISettingFactory _settingFactory;
	private ISettingsRepo _settingsRepo;
	
	public CommonModule() {
		_collectionFactory = new CollectionFactory();
		_coordinateFactory = new CoordinateFactory();
		_entityUuidFactory = new EntityUuidFactory();
		_pairFactory = new PairFactory();
		_persistentValuesHandler = new PersistentValuesHandler();
		_persistentVariableFactory = new PersistentVariableFactory();
		_settingFactory = new SettingFactory();
		
		_mapFactory = new MapFactory(_pairFactory);
		
		_genericParamsSetFactory = new GenericParamsSetFactory(_persistentValuesHandler, _mapFactory);
		
		ISetting<String> settingArchetype =
				_settingFactory.make("archetypeId", "archetypeName", "archetypeValue", _genericParamsSetFactory.make());
		_settingsRepo = new SettingsRepo(_collectionFactory, _pairFactory, _persistentValuesHandler, settingArchetype);

		_persistentValuesHandler.addPersistentValueTypeHandler(new PersistentBooleanHandler());
		_persistentValuesHandler.addPersistentValueTypeHandler(new PersistentIntegerHandler());
		_persistentValuesHandler.addPersistentValueTypeHandler(new PersistentStringHandler());

		IPersistentCollectionHandler persistentCollectionHandler = new PersistentCollectionHandler(_persistentValuesHandler, _collectionFactory);
		_persistentValuesHandler.registerPersistentCollectionHandler(persistentCollectionHandler);
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
		bind(IPersistentVariableFactory.class).toInstance(_persistentVariableFactory);
		bind(ISettingFactory.class).toInstance(_settingFactory);
		bind(ISettingsRepo.class).toInstance(_settingsRepo);
	}

}
