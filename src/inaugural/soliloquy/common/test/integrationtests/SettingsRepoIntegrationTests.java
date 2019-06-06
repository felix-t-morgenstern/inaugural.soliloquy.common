package inaugural.soliloquy.common.test.integrationtests;

import inaugural.soliloquy.common.*;
import inaugural.soliloquy.common.archetypes.SettingArchetype;
import inaugural.soliloquy.common.persistentvaluetypehandlers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.common.specs.*;

import static org.junit.jupiter.api.Assertions.*;

class SettingsRepoIntegrationTests {
    private ISettingsRepo _settingsRepo;
    private ISetting _settingArchetype;

    private ICollectionFactory _collectionFactory;
    private ICoordinateFactory _coordinateFactory;
    private IEntityUuidFactory _entityUuidFactory;
    private IGenericParamsSetFactory _genericParamsSetFactory;
    private IMapFactory _mapFactory;
    private IPairFactory _pairFactory;
    private ISettingFactory _settingFactory;

    private IPersistentValuesHandler _persistentValuesHandler;

    private IPersistentValueTypeHandler<Boolean> _booleanHandler;
    private IPersistentCollectionHandler _collectionHandler;
    private IPersistentValueTypeHandler<ICoordinate> _coordinateHandler;
    private IPersistentValueTypeHandler<IEntityUuid> _entityUuidHandler;
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

    private final String VALUES_STRING = "[{\"id\":\"booleanSetting\",\"valueString\":\"true\"},{\"id\":\"collectionOfIntsSetting\",\"valueString\":\"{\\\"typeName\\\":\\\"java.lang.Integer\\\",\\\"valueStrings\\\":[\\\"123\\\",\\\"456\\\",\\\"789\\\"]}\"},{\"id\":\"collectionOfMapsSetting\",\"valueString\":\"{\\\"typeName\\\":\\\"soliloquy.common.specs.IMap\\\\u003cjava.lang.Integer,java.lang.Boolean\\\\u003e\\\",\\\"valueStrings\\\":[\\\"{\\\\\\\"keyValueType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\",\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.Boolean\\\\\\\",\\\\\\\"keyValueStrings\\\\\\\":[\\\\\\\"789\\\\\\\",\\\\\\\"456\\\\\\\",\\\\\\\"123\\\\\\\"],\\\\\\\"valueValueStrings\\\\\\\":[\\\\\\\"true\\\\\\\",\\\\\\\"false\\\\\\\",\\\\\\\"true\\\\\\\"]}\\\",\\\"{\\\\\\\"keyValueType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\",\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.Boolean\\\\\\\",\\\\\\\"keyValueStrings\\\\\\\":[\\\\\\\"789\\\\\\\",\\\\\\\"456\\\\\\\",\\\\\\\"123\\\\\\\"],\\\\\\\"valueValueStrings\\\\\\\":[\\\\\\\"false\\\\\\\",\\\\\\\"true\\\\\\\",\\\\\\\"false\\\\\\\"]}\\\"]}\"},{\"id\":\"coordinateSetting\",\"valueString\":\"{\\\"x\\\":123,\\\"y\\\":456}\"},{\"id\":\"entityUuidSetting\",\"valueString\":\"0115d3a5-383a-46f5-92db-6d9c23bbf9b8\"},{\"id\":\"integerSetting\",\"valueString\":\"123456789\"},{\"id\":\"mapOfStringsToIntsSetting\",\"valueString\":\"{\\\"keyValueType\\\":\\\"java.lang.String\\\",\\\"valueValueType\\\":\\\"java.lang.Integer\\\",\\\"keyValueStrings\\\":[\\\"key1\\\",\\\"key2\\\",\\\"key3\\\"],\\\"valueValueStrings\\\":[\\\"123\\\",\\\"456\\\",\\\"789\\\"]}\"},{\"id\":\"mapOfIntsToMapsOfIntsToBooleansSetting\",\"valueString\":\"{\\\"keyValueType\\\":\\\"java.lang.Integer\\\",\\\"valueValueType\\\":\\\"soliloquy.common.specs.IMap\\\\u003cjava.lang.Integer,java.lang.Boolean\\\\u003e\\\",\\\"keyValueStrings\\\":[\\\"789\\\",\\\"456\\\",\\\"123\\\"],\\\"valueValueStrings\\\":[\\\"{\\\\\\\"keyValueType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\",\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.Boolean\\\\\\\",\\\\\\\"keyValueStrings\\\\\\\":[\\\\\\\"7\\\\\\\",\\\\\\\"8\\\\\\\",\\\\\\\"9\\\\\\\"],\\\\\\\"valueValueStrings\\\\\\\":[\\\\\\\"true\\\\\\\",\\\\\\\"false\\\\\\\",\\\\\\\"false\\\\\\\"]}\\\",\\\"{\\\\\\\"keyValueType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\",\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.Boolean\\\\\\\",\\\\\\\"keyValueStrings\\\\\\\":[\\\\\\\"4\\\\\\\",\\\\\\\"5\\\\\\\",\\\\\\\"6\\\\\\\"],\\\\\\\"valueValueStrings\\\\\\\":[\\\\\\\"false\\\\\\\",\\\\\\\"true\\\\\\\",\\\\\\\"false\\\\\\\"]}\\\",\\\"{\\\\\\\"keyValueType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\",\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.Boolean\\\\\\\",\\\\\\\"keyValueStrings\\\\\\\":[\\\\\\\"1\\\\\\\",\\\\\\\"2\\\\\\\",\\\\\\\"3\\\\\\\"],\\\\\\\"valueValueStrings\\\\\\\":[\\\\\\\"false\\\\\\\",\\\\\\\"true\\\\\\\",\\\\\\\"true\\\\\\\"]}\\\"]}\"},{\"id\":\"pairOfStringsSetting\",\"valueString\":\"{\\\"valueType1\\\":\\\"java.lang.String\\\",\\\"valueString1\\\":\\\"pairString1\\\",\\\"valueType2\\\":\\\"java.lang.String\\\",\\\"valueString2\\\":\\\"pairString2\\\"}\"},{\"id\":\"pairOfStringAndCollectionOfInts\",\"valueString\":\"{\\\"valueType1\\\":\\\"java.lang.String\\\",\\\"valueString1\\\":\\\"stringValue\\\",\\\"valueType2\\\":\\\"soliloquy.common.specs.ICollection\\\\u003cjava.lang.Integer\\\\u003e\\\",\\\"valueString2\\\":\\\"{\\\\\\\"typeName\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\",\\\\\\\"valueStrings\\\\\\\":[\\\\\\\"123\\\\\\\",\\\\\\\"456\\\\\\\",\\\\\\\"789\\\\\\\"]}\\\"}\"},{\"id\":\"stringSetting\",\"valueString\":\"stringSettingValue\"}]";

    @SuppressWarnings("unchecked")
    @BeforeEach
    void setUp() {
        _collectionFactory = new CollectionFactory();
        _coordinateFactory = new CoordinateFactory();
        _entityUuidFactory = new EntityUuidFactory();
        _pairFactory = new PairFactory();
        _settingFactory = new SettingFactory();

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
        _persistentValuesHandler.addPersistentValueTypeHandler(_integerHandler);
        _persistentValuesHandler.registerPersistentMapHandler(_mapHandler);
        _persistentValuesHandler.registerPersistentPairHandler(_pairHandler);
        _persistentValuesHandler.addPersistentValueTypeHandler(_settingsRepoHandler);
        _persistentValuesHandler.addPersistentValueTypeHandler(_stringHandler);

        _settingsRepo.newSubgrouping(100, SUBGROUPING_1, null);
        _settingsRepo.newSubgrouping(101, SUBGROUPING_1_1, SUBGROUPING_1);
        _settingsRepo.newSubgrouping(102, SUBGROUPING_1_2, SUBGROUPING_1);
        _settingsRepo.newSubgrouping(103, SUBGROUPING_1_3, SUBGROUPING_1);
        _settingsRepo.newSubgrouping(104, SUBGROUPING_2, null);
        _settingsRepo.newSubgrouping(105, SUBGROUPING_2_1, SUBGROUPING_2);
        _settingsRepo.newSubgrouping(106, SUBGROUPING_2_2, SUBGROUPING_2);
        _settingsRepo.newSubgrouping(107, SUBGROUPING_2_3, SUBGROUPING_2);
        _settingsRepo.newSubgrouping(108, SUBGROUPING_3, null);
        _settingsRepo.newSubgrouping(109, SUBGROUPING_3_1, SUBGROUPING_3);
        _settingsRepo.newSubgrouping(110, SUBGROUPING_3_2, SUBGROUPING_3);
        _settingsRepo.newSubgrouping(111, SUBGROUPING_3_3, SUBGROUPING_3);

        ISetting booleanSetting = _settingFactory.make("booleanSetting", "BooleanSetting",
                false, _genericParamsSetFactory.make());
        _settingsRepo.addEntity(booleanSetting, 1, null);

        ISetting collectionOfIntsSetting = _settingFactory.make("collectionOfIntsSetting",
                "CollectionOfIntsSetting", _collectionFactory.make(0),
                _genericParamsSetFactory.make());
        _settingsRepo.addEntity(collectionOfIntsSetting, 2, SUBGROUPING_1);

        ISetting collectionOfMapsSetting = _settingFactory.make("collectionOfMapsSetting",
                "CollectionOfMapsSetting", _collectionFactory.make(_mapFactory.make(0, false)),
                _genericParamsSetFactory.make());
        _settingsRepo.addEntity(collectionOfMapsSetting, 3, SUBGROUPING_1_1);

        ISetting coordinateSetting = _settingFactory.make("coordinateSetting", "CoordinateSetting",
                _coordinateFactory.make(0,0), _genericParamsSetFactory.make());
        _settingsRepo.addEntity(coordinateSetting, 4, SUBGROUPING_1_2);

        ISetting entityUuidSetting = _settingFactory.make("entityUuidSetting", "EntityUuidSetting",
                _entityUuidFactory.createRandomEntityUuid(), _genericParamsSetFactory.make());
        _settingsRepo.addEntity(entityUuidSetting, 5, SUBGROUPING_1_3);

        ISetting integerSetting = _settingFactory.make("integerSetting", "IntegerSetting", 0,
                _genericParamsSetFactory.make());
        _settingsRepo.addEntity(integerSetting, 6, SUBGROUPING_2);

        ISetting mapOfStringsToIntsSetting = _settingFactory.make("mapOfStringsToIntsSetting",
                "MapOfStringsToIntsSetting", _mapFactory.make("", 0),
                _genericParamsSetFactory.make());
        _settingsRepo.addEntity(mapOfStringsToIntsSetting, 7, SUBGROUPING_2_1);

        ISetting mapOfIntsToMapsOfIntsToBooleansSetting =
                _settingFactory.make("mapOfIntsToMapsOfIntsToBooleansSetting",
                        "MapOfIntsToMapsOfIntsToBooleansSetting",
                        _mapFactory.make(0, _mapFactory.make(0, true)),
                        _genericParamsSetFactory.make());
        _settingsRepo.addEntity(mapOfIntsToMapsOfIntsToBooleansSetting, 8, SUBGROUPING_2_2);

        ISetting pairOfStringsSetting = _settingFactory.make("pairOfStringsSetting",
                "PairOfStringsSetting", _pairFactory.make("", ""),
                _genericParamsSetFactory.make());
        _settingsRepo.addEntity(pairOfStringsSetting, 9, SUBGROUPING_2_3);

        ISetting pairOfStringAndCollectionOfInts =
                _settingFactory.make("pairOfStringAndCollectionOfInts",
                        "PairOfStringAndCollectionOfInts",
                        _pairFactory.make("", _collectionFactory.make(0)),
                        _genericParamsSetFactory.make());
        _settingsRepo.addEntity(pairOfStringAndCollectionOfInts, 10, SUBGROUPING_3);

        ISetting stringSetting = _settingFactory.make("stringSetting", "StringSetting", "",
                _genericParamsSetFactory.make());
        _settingsRepo.addEntity(stringSetting, 11, SUBGROUPING_3_1);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testWrite() {
        ISetting booleanSetting = _settingsRepo.getSetting("booleanSetting");
        booleanSetting.setValue(true);

        ISetting collectionOfIntsSetting = _settingsRepo.getSetting("collectionOfIntsSetting");
        ((ICollection<Integer>) collectionOfIntsSetting.getValue()).add(123);
        ((ICollection<Integer>) collectionOfIntsSetting.getValue()).add(456);
        ((ICollection<Integer>) collectionOfIntsSetting.getValue()).add(789);

        ISetting collectionOfMapsSetting = _settingsRepo.getSetting("collectionOfMapsSetting");
        IMap collectionOfMapsMap1 = _mapFactory.make(0, true);
        collectionOfMapsMap1.put(123, true);
        collectionOfMapsMap1.put(456, false);
        collectionOfMapsMap1.put(789, true);
        IMap collectionOfMapsMap2 = _mapFactory.make(0, true);
        collectionOfMapsMap2.put(123, false);
        collectionOfMapsMap2.put(456, true);
        collectionOfMapsMap2.put(789, false);
        ((ICollection<IMap>) collectionOfMapsSetting.getValue()).add(collectionOfMapsMap1);
        ((ICollection<IMap>) collectionOfMapsSetting.getValue()).add(collectionOfMapsMap2);

        ISetting coordinateSetting = _settingsRepo.getSetting("coordinateSetting");
        ((ICoordinate) coordinateSetting.getValue()).setX(123);
        ((ICoordinate) coordinateSetting.getValue()).setY(456);

        ISetting entityUuidSetting = _settingsRepo.getSetting("entityUuidSetting");
        entityUuidSetting.setValue(_entityUuidFactory
                .createFromString("0115d3a5-383a-46f5-92db-6d9c23bbf9b8"));

        ISetting integerSetting = _settingsRepo.getSetting("integerSetting");
        integerSetting.setValue(123456789);

        ISetting mapOfStringsToIntsSetting = _settingsRepo.getSetting("mapOfStringsToIntsSetting");
        ((IMap<String,Integer>) mapOfStringsToIntsSetting.getValue()).put("key1", 123);
        ((IMap<String,Integer>) mapOfStringsToIntsSetting.getValue()).put("key2", 456);
        ((IMap<String,Integer>) mapOfStringsToIntsSetting.getValue()).put("key3", 789);

        ISetting mapOfIntsToMapsOfIntsToBooleansSetting =
                _settingsRepo.getSetting("mapOfIntsToMapsOfIntsToBooleansSetting");
        IMap<Integer,Boolean> mapOfIntsToBooleans1 = _mapFactory.make(0, true);
        mapOfIntsToBooleans1.put(1, false);
        mapOfIntsToBooleans1.put(2, true);
        mapOfIntsToBooleans1.put(3, true);
        IMap<Integer,Boolean> mapOfIntsToBooleans2 = _mapFactory.make(0, true);
        mapOfIntsToBooleans2.put(4, false);
        mapOfIntsToBooleans2.put(5, true);
        mapOfIntsToBooleans2.put(6, false);
        IMap<Integer,Boolean> mapOfIntsToBooleans3 = _mapFactory.make(0, true);
        mapOfIntsToBooleans3.put(7, true);
        mapOfIntsToBooleans3.put(8, false);
        mapOfIntsToBooleans3.put(9, false);
        IMap<Integer,IMap<Integer,Boolean>> mapOfIntsToMapsOfIntsToBooleans =
                (IMap<Integer, IMap<Integer, Boolean>>)
                        mapOfIntsToMapsOfIntsToBooleansSetting.getValue();
        mapOfIntsToMapsOfIntsToBooleans.put(123, mapOfIntsToBooleans1);
        mapOfIntsToMapsOfIntsToBooleans.put(456, mapOfIntsToBooleans2);
        mapOfIntsToMapsOfIntsToBooleans.put(789, mapOfIntsToBooleans3);
        mapOfIntsToMapsOfIntsToBooleansSetting.setValue(mapOfIntsToMapsOfIntsToBooleans);

        ISetting pairOfStringsSetting = _settingsRepo.getSetting("pairOfStringsSetting");
        ((IPair<String,String>) pairOfStringsSetting.getValue()).setItem1("pairString1");
        ((IPair<String,String>) pairOfStringsSetting.getValue()).setItem2("pairString2");

        ISetting pairOfStringAndCollectionOfIntsSetting =
                _settingsRepo.getSetting("pairOfStringAndCollectionOfInts");
        ((IPair<String,ICollection<Integer>>) pairOfStringAndCollectionOfIntsSetting.getValue())
                .setItem1("stringValue");
        ICollection<Integer> collectionOfInts =
                ((IPair<String,ICollection<Integer>>) pairOfStringAndCollectionOfIntsSetting.getValue())
                        .getItem2();
        collectionOfInts.add(123);
        collectionOfInts.add(456);
        collectionOfInts.add(789);

        ISetting stringSetting = _settingsRepo.getSetting("stringSetting");
        stringSetting.setValue("stringSettingValue");

        assertEquals(VALUES_STRING, _settingsRepoHandler.write(_settingsRepo));
    }

    @Test
    void testRead() {
        _settingsRepoHandler.read(VALUES_STRING);

        ISetting<Boolean> booleanSetting = _settingsRepo.getSetting("booleanSetting");
        assertTrue(booleanSetting.getValue());

        ISetting<ICollection<Integer>> collectionOfIntsSetting =
                _settingsRepo.getSetting("collectionOfIntsSetting");
        ICollection<Integer> collectionOfInts = collectionOfIntsSetting.getValue();
        assertNotNull(collectionOfInts);
        assertEquals(3, collectionOfInts.size());
        assertTrue(collectionOfInts.contains(123));
        assertTrue(collectionOfInts.contains(456));
        assertTrue(collectionOfInts.contains(789));

        ISetting<ICollection<IMap<Integer,Boolean>>> collectionOfMapsSetting =
                _settingsRepo.getSetting("collectionOfMapsSetting");
        ICollection<IMap<Integer,Boolean>> collectionOfMaps = collectionOfMapsSetting.getValue();
        assertNotNull(collectionOfMaps);
        assertEquals(2, collectionOfMaps.size());
        IMap<Integer,Boolean> mapFromCollection1 = collectionOfMaps.get(0);
        assertNotNull(mapFromCollection1);
        assertEquals(3, mapFromCollection1.size());
        assertTrue(mapFromCollection1.get(123));
        assertFalse(mapFromCollection1.get(456));
        assertTrue(mapFromCollection1.get(789));
        IMap<Integer,Boolean> mapFromCollection2 = collectionOfMaps.get(1);
        assertNotNull(mapFromCollection2);
        assertEquals(3, mapFromCollection2.size());
        assertFalse(mapFromCollection2.get(123));
        assertTrue(mapFromCollection2.get(456));
        assertFalse(mapFromCollection2.get(789));

        ISetting<ICoordinate> coordinateSetting = _settingsRepo.getSetting("coordinateSetting");
        ICoordinate coordinate = coordinateSetting.getValue();
        assertNotNull(coordinate);
        assertEquals(123, coordinate.getX());
        assertEquals(456, coordinate.getY());

        ISetting<IEntityUuid> entityUuidSetting = _settingsRepo.getSetting("entityUuidSetting");
        IEntityUuid entityUuid = entityUuidSetting.getValue();
        assertNotNull(entityUuid);
        assertEquals("0115d3a5-383a-46f5-92db-6d9c23bbf9b8", entityUuid.toString());

        ISetting<Integer> integerSetting = _settingsRepo.getSetting("integerSetting");
        Integer integer = integerSetting.getValue();
        assertNotNull(integer);
        assertEquals((Integer) 123456789, integer);

        ISetting<IMap<String,Integer>> mapOfStringsToIntsSetting =
                _settingsRepo.getSetting("mapOfStringsToIntsSetting");
        IMap<String,Integer> mapOfStringsToInts = mapOfStringsToIntsSetting.getValue();
        assertNotNull(mapOfStringsToInts);
        assertEquals(3, mapOfStringsToInts.size());
        assertEquals((Integer) 123, mapOfStringsToInts.get("key1"));
        assertEquals((Integer) 456, mapOfStringsToInts.get("key2"));
        assertEquals((Integer) 789, mapOfStringsToInts.get("key3"));

        ISetting<IMap<Integer,IMap<Integer,Boolean>>> mapOfIntsToMapsOfIntsToBooleansSetting =
                _settingsRepo.getSetting("mapOfIntsToMapsOfIntsToBooleansSetting");
        IMap<Integer,IMap<Integer,Boolean>> mapOfIntsToMapsOfIntsToBooleans =
                mapOfIntsToMapsOfIntsToBooleansSetting.getValue();
        assertNotNull(mapOfIntsToMapsOfIntsToBooleans);
        assertEquals(3, mapOfIntsToMapsOfIntsToBooleans.size());
        IMap<Integer,Boolean> mapOfIntsToBooleans1 = mapOfIntsToMapsOfIntsToBooleans.get(123);
        assertNotNull(mapOfIntsToBooleans1);
        assertEquals(3, mapOfIntsToBooleans1.size());
        assertFalse(mapOfIntsToBooleans1.get(1));
        assertTrue(mapOfIntsToBooleans1.get(2));
        assertTrue(mapOfIntsToBooleans1.get(3));
        IMap<Integer,Boolean> mapOfIntsToBooleans2 = mapOfIntsToMapsOfIntsToBooleans.get(456);
        assertNotNull(mapOfIntsToBooleans2);
        assertEquals(3, mapOfIntsToBooleans2.size());
        assertFalse(mapOfIntsToBooleans2.get(4));
        assertTrue(mapOfIntsToBooleans2.get(5));
        assertFalse(mapOfIntsToBooleans2.get(6));
        IMap<Integer,Boolean> mapOfIntsToBooleans3 = mapOfIntsToMapsOfIntsToBooleans.get(789);
        assertNotNull(mapOfIntsToBooleans3);
        assertEquals(3, mapOfIntsToBooleans3.size());
        assertTrue(mapOfIntsToBooleans3.get(7));
        assertFalse(mapOfIntsToBooleans3.get(8));
        assertFalse(mapOfIntsToBooleans3.get(9));

        ISetting<IPair<String,String>> pairOfStringsSetting =
                _settingsRepo.getSetting("pairOfStringsSetting");
        IPair<String,String> pairOfStrings = pairOfStringsSetting.getValue();
        assertNotNull(pairOfStrings);
        assertEquals("pairString1", pairOfStrings.getItem1());
        assertEquals("pairString2", pairOfStrings.getItem2());

        ISetting<IPair<String,ICollection<Integer>>> pairOfStringAndCollectionOfIntsSetting =
                _settingsRepo.getSetting("pairOfStringAndCollectionOfInts");
        IPair<String,ICollection<Integer>> pairOfStringAndCollectionOfInts
                = pairOfStringAndCollectionOfIntsSetting.getValue();
        assertNotNull(pairOfStringAndCollectionOfInts);
        assertEquals("stringValue", pairOfStringAndCollectionOfInts.getItem1());
        ICollection<Integer> collectionOfIntsFromPair = pairOfStringAndCollectionOfInts.getItem2();
        assertNotNull(collectionOfIntsFromPair);
        assertEquals(3, collectionOfIntsFromPair.size());
        assertTrue(collectionOfIntsFromPair.contains(123));
        assertTrue(collectionOfIntsFromPair.contains(456));
        assertTrue(collectionOfIntsFromPair.contains(789));

        ISetting<String> stringSetting = _settingsRepo.getSetting("stringSetting");
        String stringFromSetting = stringSetting.getValue();
        assertNotNull(stringFromSetting);
        assertEquals("stringSettingValue", stringFromSetting);
    }
}
