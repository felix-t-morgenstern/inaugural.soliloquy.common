package inaugural.soliloquy.common;

import com.google.inject.AbstractModule;
import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentBooleanHandler;
import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentIntegerHandler;
import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentIntegersHandler;
import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentStringHandler;
import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentStringsHandler;
import soliloquy.common.specs.ICollectionFactory;
import soliloquy.common.specs.ICoordinateFactory;
import soliloquy.common.specs.IEntityUuidFactory;
import soliloquy.common.specs.IGenericParamsSetFactory;
import soliloquy.common.specs.IMapFactory;
import soliloquy.common.specs.IPairFactory;
import soliloquy.common.specs.IPersistentValuesHandler;
import soliloquy.common.specs.IPersistentVariableFactory;
import soliloquy.common.specs.ISetting;
import soliloquy.common.specs.ISettingFactory;
import soliloquy.common.specs.ISettingsRepo;

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
		_persistentValuesHandler.addPersistentValueTypeHandler(new PersistentIntegersHandler(_collectionFactory));
		_persistentValuesHandler.addPersistentValueTypeHandler(new PersistentStringHandler());
		_persistentValuesHandler.addPersistentValueTypeHandler(new PersistentStringsHandler(_collectionFactory));
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
