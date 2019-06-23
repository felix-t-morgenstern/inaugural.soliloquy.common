package inaugural.soliloquy.common.test.integrationtests;

import com.google.inject.Guice;
import com.google.inject.Injector;
import inaugural.soliloquy.common.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.IPersistentValueTypeHandler;
import soliloquy.specs.common.entities.IPersistentValuesHandler;
import soliloquy.specs.common.entities.ISetting;
import soliloquy.specs.common.entities.ISettingsRepo;
import soliloquy.specs.common.factories.*;
import soliloquy.specs.common.valueobjects.*;

import static org.junit.jupiter.api.Assertions.*;

class SettingsRepoIntegrationTests {
    private ISettingsRepo _settingsRepo;

    private IEntityUuidFactory _entityUuidFactory;
    private IMapFactory _mapFactory;

    private IPersistentValueTypeHandler<ISettingsRepo> _settingsRepoHandler;

    private final String VALUES_STRING = "[{\"id\":\"booleanSetting\",\"serializedValue\":\"true\"},{\"id\":\"collectionOfIntsSetting\",\"serializedValue\":\"{\\\"typeName\\\":\\\"java.lang.Integer\\\",\\\"serializedValues\\\":[\\\"123\\\",\\\"456\\\",\\\"789\\\"]}\"},{\"id\":\"collectionOfMapsSetting\",\"serializedValue\":\"{\\\"typeName\\\":\\\"soliloquy.specs.common.valueobjects.IMap\\\\u003cjava.lang.Integer,java.lang.Boolean\\\\u003e\\\",\\\"serializedValues\\\":[\\\"{\\\\\\\"keyValueType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\",\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.Boolean\\\\\\\",\\\\\\\"keySerializedValues\\\\\\\":[\\\\\\\"789\\\\\\\",\\\\\\\"456\\\\\\\",\\\\\\\"123\\\\\\\"],\\\\\\\"valueSerializedValues\\\\\\\":[\\\\\\\"true\\\\\\\",\\\\\\\"false\\\\\\\",\\\\\\\"true\\\\\\\"]}\\\",\\\"{\\\\\\\"keyValueType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\",\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.Boolean\\\\\\\",\\\\\\\"keySerializedValues\\\\\\\":[\\\\\\\"789\\\\\\\",\\\\\\\"456\\\\\\\",\\\\\\\"123\\\\\\\"],\\\\\\\"valueSerializedValues\\\\\\\":[\\\\\\\"false\\\\\\\",\\\\\\\"true\\\\\\\",\\\\\\\"false\\\\\\\"]}\\\"]}\"},{\"id\":\"coordinateSetting\",\"serializedValue\":\"{\\\"x\\\":123,\\\"y\\\":456}\"},{\"id\":\"entityUuidSetting\",\"serializedValue\":\"0115d3a5-383a-46f5-92db-6d9c23bbf9b8\"},{\"id\":\"integerSetting\",\"serializedValue\":\"123456789\"},{\"id\":\"mapOfStringsToIntsSetting\",\"serializedValue\":\"{\\\"keyValueType\\\":\\\"java.lang.String\\\",\\\"valueValueType\\\":\\\"java.lang.Integer\\\",\\\"keySerializedValues\\\":[\\\"key1\\\",\\\"key2\\\",\\\"key3\\\"],\\\"valueSerializedValues\\\":[\\\"123\\\",\\\"456\\\",\\\"789\\\"]}\"},{\"id\":\"mapOfIntsToMapsOfIntsToBooleansSetting\",\"serializedValue\":\"{\\\"keyValueType\\\":\\\"java.lang.Integer\\\",\\\"valueValueType\\\":\\\"soliloquy.specs.common.valueobjects.IMap\\\\u003cjava.lang.Integer,java.lang.Boolean\\\\u003e\\\",\\\"keySerializedValues\\\":[\\\"789\\\",\\\"456\\\",\\\"123\\\"],\\\"valueSerializedValues\\\":[\\\"{\\\\\\\"keyValueType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\",\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.Boolean\\\\\\\",\\\\\\\"keySerializedValues\\\\\\\":[\\\\\\\"7\\\\\\\",\\\\\\\"8\\\\\\\",\\\\\\\"9\\\\\\\"],\\\\\\\"valueSerializedValues\\\\\\\":[\\\\\\\"true\\\\\\\",\\\\\\\"false\\\\\\\",\\\\\\\"false\\\\\\\"]}\\\",\\\"{\\\\\\\"keyValueType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\",\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.Boolean\\\\\\\",\\\\\\\"keySerializedValues\\\\\\\":[\\\\\\\"4\\\\\\\",\\\\\\\"5\\\\\\\",\\\\\\\"6\\\\\\\"],\\\\\\\"valueSerializedValues\\\\\\\":[\\\\\\\"false\\\\\\\",\\\\\\\"true\\\\\\\",\\\\\\\"false\\\\\\\"]}\\\",\\\"{\\\\\\\"keyValueType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\",\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.Boolean\\\\\\\",\\\\\\\"keySerializedValues\\\\\\\":[\\\\\\\"1\\\\\\\",\\\\\\\"2\\\\\\\",\\\\\\\"3\\\\\\\"],\\\\\\\"valueSerializedValues\\\\\\\":[\\\\\\\"false\\\\\\\",\\\\\\\"true\\\\\\\",\\\\\\\"true\\\\\\\"]}\\\"]}\"},{\"id\":\"pairOfStringsSetting\",\"serializedValue\":\"{\\\"valueType1\\\":\\\"java.lang.String\\\",\\\"serializedValue1\\\":\\\"pairString1\\\",\\\"valueType2\\\":\\\"java.lang.String\\\",\\\"serializedValue2\\\":\\\"pairString2\\\"}\"},{\"id\":\"pairOfStringAndCollectionOfInts\",\"serializedValue\":\"{\\\"valueType1\\\":\\\"java.lang.String\\\",\\\"serializedValue1\\\":\\\"stringValue\\\",\\\"valueType2\\\":\\\"soliloquy.specs.common.valueobjects.ICollection\\\\u003cjava.lang.Integer\\\\u003e\\\",\\\"serializedValue2\\\":\\\"{\\\\\\\"typeName\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\",\\\\\\\"serializedValues\\\\\\\":[\\\\\\\"123\\\\\\\",\\\\\\\"456\\\\\\\",\\\\\\\"789\\\\\\\"]}\\\"}\"},{\"id\":\"stringSetting\",\"serializedValue\":\"stringSettingValue\"}]";

    @BeforeEach
    void setUp() {
        Injector commonInjector = Guice.createInjector(new CommonModule());

        _settingsRepo = commonInjector.getInstance(ISettingsRepo.class);
        ICollectionFactory _collectionFactory = commonInjector.getInstance(ICollectionFactory.class);
        ICoordinateFactory _coordinateFactory = commonInjector.getInstance(ICoordinateFactory.class);
        _entityUuidFactory = commonInjector.getInstance(IEntityUuidFactory.class);
        IGenericParamsSetFactory _genericParamsSetFactory = commonInjector.getInstance(IGenericParamsSetFactory.class);
        _mapFactory = commonInjector.getInstance(IMapFactory.class);
        IPairFactory _pairFactory = commonInjector.getInstance(IPairFactory.class);
        ISettingFactory _settingFactory = commonInjector.getInstance(ISettingFactory.class);

        _settingsRepoHandler = commonInjector.getInstance(IPersistentValuesHandler.class)
                .getPersistentValueTypeHandler(ISettingsRepo.class.getCanonicalName());

        String subgrouping1 = "subgrouping1";
        _settingsRepo.newSubgrouping(100, subgrouping1, null);
        String subgrouping1_1 = "subgrouping1-1";
        _settingsRepo.newSubgrouping(101, subgrouping1_1, subgrouping1);
        String subgrouping1_2 = "subgrouping1-2";
        _settingsRepo.newSubgrouping(102, subgrouping1_2, subgrouping1);
        String subgrouping1_3 = "subgrouping1-3";
        _settingsRepo.newSubgrouping(103, subgrouping1_3, subgrouping1);
        String subgrouping2 = "subgrouping2";
        _settingsRepo.newSubgrouping(104, subgrouping2, null);
        String subgrouping2_1 = "subgrouping2-1";
        _settingsRepo.newSubgrouping(105, subgrouping2_1, subgrouping2);
        String subgrouping2_2 = "subgrouping2-2";
        _settingsRepo.newSubgrouping(106, subgrouping2_2, subgrouping2);
        String subgrouping2_3 = "subgrouping2-3";
        _settingsRepo.newSubgrouping(107, subgrouping2_3, subgrouping2);
        String subgrouping3 = "subgrouping3";
        _settingsRepo.newSubgrouping(108, subgrouping3, null);
        String subgrouping3_1 = "subgrouping3-1";
        _settingsRepo.newSubgrouping(109, subgrouping3_1, subgrouping3);
        String subgrouping3_2 = "subgrouping3-2";
        _settingsRepo.newSubgrouping(110, subgrouping3_2, subgrouping3);
        String subgrouping3_3 = "subgrouping3-3";
        _settingsRepo.newSubgrouping(111, subgrouping3_3, subgrouping3);

        ISetting booleanSetting = _settingFactory.make("booleanSetting", "BooleanSetting",
                false, _genericParamsSetFactory.make());
        _settingsRepo.addEntity(booleanSetting, 1, null);

        ISetting collectionOfIntsSetting = _settingFactory.make("collectionOfIntsSetting",
                "CollectionOfIntsSetting", _collectionFactory.make(0),
                _genericParamsSetFactory.make());
        _settingsRepo.addEntity(collectionOfIntsSetting, 2, subgrouping1);

        ISetting collectionOfMapsSetting = _settingFactory.make("collectionOfMapsSetting",
                "CollectionOfMapsSetting", _collectionFactory.make(_mapFactory.make(0, false)),
                _genericParamsSetFactory.make());
        _settingsRepo.addEntity(collectionOfMapsSetting, 3, subgrouping1_1);

        ISetting coordinateSetting = _settingFactory.make("coordinateSetting", "CoordinateSetting",
                _coordinateFactory.make(0,0), _genericParamsSetFactory.make());
        _settingsRepo.addEntity(coordinateSetting, 4, subgrouping1_2);

        ISetting entityUuidSetting = _settingFactory.make("entityUuidSetting", "EntityUuidSetting",
                _entityUuidFactory.createRandomEntityUuid(), _genericParamsSetFactory.make());
        _settingsRepo.addEntity(entityUuidSetting, 5, subgrouping1_3);

        ISetting integerSetting = _settingFactory.make("integerSetting", "IntegerSetting", 0,
                _genericParamsSetFactory.make());
        _settingsRepo.addEntity(integerSetting, 6, subgrouping2);

        ISetting mapOfStringsToIntsSetting = _settingFactory.make("mapOfStringsToIntsSetting",
                "MapOfStringsToIntsSetting", _mapFactory.make("", 0),
                _genericParamsSetFactory.make());
        _settingsRepo.addEntity(mapOfStringsToIntsSetting, 7, subgrouping2_1);

        ISetting mapOfIntsToMapsOfIntsToBooleansSetting =
                _settingFactory.make("mapOfIntsToMapsOfIntsToBooleansSetting",
                        "MapOfIntsToMapsOfIntsToBooleansSetting",
                        _mapFactory.make(0, _mapFactory.make(0, true)),
                        _genericParamsSetFactory.make());
        _settingsRepo.addEntity(mapOfIntsToMapsOfIntsToBooleansSetting, 8, subgrouping2_2);

        ISetting pairOfStringsSetting = _settingFactory.make("pairOfStringsSetting",
                "PairOfStringsSetting", _pairFactory.make("", ""),
                _genericParamsSetFactory.make());
        _settingsRepo.addEntity(pairOfStringsSetting, 9, subgrouping2_3);

        ISetting pairOfStringAndCollectionOfInts =
                _settingFactory.make("pairOfStringAndCollectionOfInts",
                        "PairOfStringAndCollectionOfInts",
                        _pairFactory.make("", _collectionFactory.make(0)),
                        _genericParamsSetFactory.make());
        _settingsRepo.addEntity(pairOfStringAndCollectionOfInts, 10, subgrouping3);

        ISetting stringSetting = _settingFactory.make("stringSetting", "StringSetting", "",
                _genericParamsSetFactory.make());
        _settingsRepo.addEntity(stringSetting, 11, subgrouping3_1);
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
