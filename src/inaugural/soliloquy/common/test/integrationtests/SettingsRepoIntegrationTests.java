package inaugural.soliloquy.common.test.integrationtests;

import inaugural.soliloquy.common.*;
import inaugural.soliloquy.common.archetypes.SettingArchetype;
import inaugural.soliloquy.common.persistentvaluetypehandlers.*;
import org.junit.jupiter.api.BeforeEach;
import soliloquy.common.specs.*;

class SettingsRepoIntegrationTests {
    private ISettingsRepo _settingsRepo;
    private ISetting _settingArchetype;

    private ICollectionFactory _collectionFactory;
    private ICoordinateFactory _coordinateFactory;
    private IEntityUuidFactory _entityUuidFactory;
    private IGenericParamsSetFactory _genericParamsSetFactory;
    private IMapFactory _mapFactory;
    private IPairFactory _pairFactory;

    private IPersistentValuesHandler _persistentValuesHandler;

    private IPersistentValueTypeHandler<Boolean> _booleanHandler;
    private IPersistentCollectionHandler _collectionHandler;
    private IPersistentValueTypeHandler<ICoordinate> _coordinateHandler;
    private IPersistentValueTypeHandler<IEntityUuid> _entityUuidHandler;
    private IPersistentValueTypeHandler<IGenericParamsSet> _genericParamsSetHandler;
    private IPersistentValueTypeHandler<Integer> _integerHandler;
    private IPersistentMapHandler _mapHandler;
    private IPersistentPairHandler _pairHandler;
    private IPersistentValueTypeHandler<ISettingsRepo> _settingsRepoHandler;
    private IPersistentValueTypeHandler<String> _stringHandler;

    private final String SUBGROUPING_1 = "subgrouping1";
    private final String SUBGROUPING_1_1 = "subgrouping1-1";
    private final String SUBGROUPING_1_2 = "subgrouping1-2";
    private final String SUBGROUPING_1_3 = "subgrouping1-3";
    private final String SUBGROUPING_2 = "subgrouping2";
    private final String SUBGROUPING_2_1 = "subgrouping2-1";
    private final String SUBGROUPING_2_2 = "subgrouping2-2";
    private final String SUBGROUPING_2_3 = "subgrouping2-3";
    private final String SUBGROUPING_3 = "subgrouping3";
    private final String SUBGROUPING_3_1 = "subgrouping3-1";
    private final String SUBGROUPING_3_2 = "subgrouping3-2";
    private final String SUBGROUPING_3_3 = "subgrouping3-3";

    @BeforeEach
    void setUp() {
        _collectionFactory = new CollectionFactory();
        _coordinateFactory = new CoordinateFactory();
        _entityUuidFactory = new EntityUuidFactory();
        _pairFactory = new PairFactory();

        _mapFactory = new MapFactory(_pairFactory);

        _persistentValuesHandler = new PersistentValuesHandler();

        _settingArchetype = new SettingArchetype();
        _settingsRepo = new SettingsRepo(_collectionFactory, _pairFactory,
                _persistentValuesHandler, _settingArchetype);

        _genericParamsSetFactory = new GenericParamsSetFactory(_persistentValuesHandler,
                _mapFactory);

        _booleanHandler = new PersistentBooleanHandler();
        _collectionHandler = new PersistentCollectionHandler(_persistentValuesHandler,
                _collectionFactory);
        _coordinateHandler = new PersistentCoordinateHandler(_coordinateFactory);
        _entityUuidHandler = new PersistentEntityUuidHandler(_entityUuidFactory);
        _genericParamsSetHandler = new PersistentGenericParamsSetHandler(_persistentValuesHandler,
                _genericParamsSetFactory);
        _integerHandler = new PersistentIntegerHandler();
        _mapHandler = new PersistentMapHandler(_persistentValuesHandler, _mapFactory);
        _pairHandler = new PersistentPairHandler(_persistentValuesHandler, _pairFactory);
        _settingsRepoHandler = new PersistentSettingsRepoHandler(_persistentValuesHandler,
                _settingsRepo);
        _stringHandler = new PersistentStringHandler();

        _persistentValuesHandler.addPersistentValueTypeHandler(_booleanHandler);
        _persistentValuesHandler.registerPersistentCollectionHandler(_collectionHandler);
        _persistentValuesHandler.addPersistentValueTypeHandler(_coordinateHandler);
        _persistentValuesHandler.addPersistentValueTypeHandler(_entityUuidHandler);
        _persistentValuesHandler.addPersistentValueTypeHandler(_genericParamsSetHandler);
        _persistentValuesHandler.addPersistentValueTypeHandler(_integerHandler);
        _persistentValuesHandler.registerPersistentMapHandler(_mapHandler);
        _persistentValuesHandler.registerPersistentPairHandler(_pairHandler);
        _persistentValuesHandler.addPersistentValueTypeHandler(_settingsRepoHandler);
        _persistentValuesHandler.addPersistentValueTypeHandler(_stringHandler);

        _settingsRepo.newSubgrouping(10, SUBGROUPING_1, null);
        _settingsRepo.newSubgrouping(10, SUBGROUPING_1_1, SUBGROUPING_1);
        _settingsRepo.newSubgrouping(10, SUBGROUPING_1_2, SUBGROUPING_1);
        _settingsRepo.newSubgrouping(10, SUBGROUPING_1_3, SUBGROUPING_1);
        _settingsRepo.newSubgrouping(10, SUBGROUPING_2, null);
        _settingsRepo.newSubgrouping(10, SUBGROUPING_2_1, SUBGROUPING_2);
        _settingsRepo.newSubgrouping(10, SUBGROUPING_2_2, SUBGROUPING_2);
        _settingsRepo.newSubgrouping(10, SUBGROUPING_2_3, SUBGROUPING_2);
        _settingsRepo.newSubgrouping(10, SUBGROUPING_3, null);
        _settingsRepo.newSubgrouping(10, SUBGROUPING_3_1, SUBGROUPING_3);
        _settingsRepo.newSubgrouping(10, SUBGROUPING_3_2, SUBGROUPING_3);
        _settingsRepo.newSubgrouping(10, SUBGROUPING_3_3, SUBGROUPING_3);

        _settingsRepo.
    }
}
