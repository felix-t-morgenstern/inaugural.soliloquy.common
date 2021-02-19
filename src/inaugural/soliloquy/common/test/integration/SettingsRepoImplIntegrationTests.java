package inaugural.soliloquy.common.test.integration;

import com.google.inject.Guice;
import com.google.inject.Injector;
import inaugural.soliloquy.common.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.*;
import soliloquy.specs.common.infrastructure.*;
import soliloquy.specs.common.persistence.PersistentValueTypeHandler;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.valueobjects.*;

import static org.junit.jupiter.api.Assertions.*;

class SettingsRepoImplIntegrationTests {
    private SettingsRepo _settingsRepo;

    private EntityUuidFactory _entityUuidFactory;
    private MapFactory _mapFactory;

    private PersistentValueTypeHandler<SettingsRepo> _settingsRepoHandler;

    private final String VALUES_STRING = "[{\"id\":\"booleanSetting\",\"serializedValue\":\"true\"},{\"id\":\"collectionOfIntsSetting\",\"serializedValue\":\"{\\\"typeName\\\":\\\"java.lang.Integer\\\",\\\"serializedValues\\\":[\\\"123\\\",\\\"456\\\",\\\"789\\\"]}\"},{\"id\":\"collectionOfMapsSetting\",\"serializedValue\":\"{\\\"typeName\\\":\\\"soliloquy.specs.common.infrastructure.Map\\\\u003cjava.lang.Integer,java.lang.Boolean\\\\u003e\\\",\\\"serializedValues\\\":[\\\"{\\\\\\\"keyValueType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\",\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.Boolean\\\\\\\",\\\\\\\"keySerializedValues\\\\\\\":[\\\\\\\"789\\\\\\\",\\\\\\\"456\\\\\\\",\\\\\\\"123\\\\\\\"],\\\\\\\"valueSerializedValues\\\\\\\":[\\\\\\\"true\\\\\\\",\\\\\\\"false\\\\\\\",\\\\\\\"true\\\\\\\"]}\\\",\\\"{\\\\\\\"keyValueType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\",\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.Boolean\\\\\\\",\\\\\\\"keySerializedValues\\\\\\\":[\\\\\\\"789\\\\\\\",\\\\\\\"456\\\\\\\",\\\\\\\"123\\\\\\\"],\\\\\\\"valueSerializedValues\\\\\\\":[\\\\\\\"false\\\\\\\",\\\\\\\"true\\\\\\\",\\\\\\\"false\\\\\\\"]}\\\"]}\"},{\"id\":\"coordinateSetting\",\"serializedValue\":\"{\\\"x\\\":123,\\\"y\\\":456}\"},{\"id\":\"entityUuidSetting\",\"serializedValue\":\"0115d3a5-383a-46f5-92db-6d9c23bbf9b8\"},{\"id\":\"integerSetting\",\"serializedValue\":\"123456789\"},{\"id\":\"mapOfStringsToIntsSetting\",\"serializedValue\":\"{\\\"keyValueType\\\":\\\"java.lang.String\\\",\\\"valueValueType\\\":\\\"java.lang.Integer\\\",\\\"keySerializedValues\\\":[\\\"key1\\\",\\\"key2\\\",\\\"key3\\\"],\\\"valueSerializedValues\\\":[\\\"123\\\",\\\"456\\\",\\\"789\\\"]}\"},{\"id\":\"mapOfIntsToMapsOfIntsToBooleansSetting\",\"serializedValue\":\"{\\\"keyValueType\\\":\\\"java.lang.Integer\\\",\\\"valueValueType\\\":\\\"soliloquy.specs.common.infrastructure.Map\\\\u003cjava.lang.Integer,java.lang.Boolean\\\\u003e\\\",\\\"keySerializedValues\\\":[\\\"789\\\",\\\"456\\\",\\\"123\\\"],\\\"valueSerializedValues\\\":[\\\"{\\\\\\\"keyValueType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\",\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.Boolean\\\\\\\",\\\\\\\"keySerializedValues\\\\\\\":[\\\\\\\"7\\\\\\\",\\\\\\\"8\\\\\\\",\\\\\\\"9\\\\\\\"],\\\\\\\"valueSerializedValues\\\\\\\":[\\\\\\\"true\\\\\\\",\\\\\\\"false\\\\\\\",\\\\\\\"false\\\\\\\"]}\\\",\\\"{\\\\\\\"keyValueType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\",\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.Boolean\\\\\\\",\\\\\\\"keySerializedValues\\\\\\\":[\\\\\\\"4\\\\\\\",\\\\\\\"5\\\\\\\",\\\\\\\"6\\\\\\\"],\\\\\\\"valueSerializedValues\\\\\\\":[\\\\\\\"false\\\\\\\",\\\\\\\"true\\\\\\\",\\\\\\\"false\\\\\\\"]}\\\",\\\"{\\\\\\\"keyValueType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\",\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.Boolean\\\\\\\",\\\\\\\"keySerializedValues\\\\\\\":[\\\\\\\"1\\\\\\\",\\\\\\\"2\\\\\\\",\\\\\\\"3\\\\\\\"],\\\\\\\"valueSerializedValues\\\\\\\":[\\\\\\\"false\\\\\\\",\\\\\\\"true\\\\\\\",\\\\\\\"true\\\\\\\"]}\\\"]}\"},{\"id\":\"pairOfStringsSetting\",\"serializedValue\":\"{\\\"valueType1\\\":\\\"java.lang.String\\\",\\\"serializedValue1\\\":\\\"pairString1\\\",\\\"valueType2\\\":\\\"java.lang.String\\\",\\\"serializedValue2\\\":\\\"pairString2\\\"}\"},{\"id\":\"pairOfStringAndCollectionOfInts\",\"serializedValue\":\"{\\\"valueType1\\\":\\\"java.lang.String\\\",\\\"serializedValue1\\\":\\\"stringValue\\\",\\\"valueType2\\\":\\\"soliloquy.specs.common.infrastructure.List\\\\u003cjava.lang.Integer\\\\u003e\\\",\\\"serializedValue2\\\":\\\"{\\\\\\\"typeName\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\",\\\\\\\"serializedValues\\\\\\\":[\\\\\\\"123\\\\\\\",\\\\\\\"456\\\\\\\",\\\\\\\"789\\\\\\\"]}\\\"}\"},{\"id\":\"stringSetting\",\"serializedValue\":\"stringSettingValue\"}]";

    @SuppressWarnings("rawtypes")
    @BeforeEach
    void setUp() {
        Injector commonInjector = Guice.createInjector(new CommonModule());

        _settingsRepo = commonInjector.getInstance(SettingsRepo.class);
        ListFactory listFactory = commonInjector.getInstance(ListFactory.class);
        CoordinateFactory _coordinateFactory = commonInjector.getInstance(CoordinateFactory.class);
        _entityUuidFactory = commonInjector.getInstance(EntityUuidFactory.class);
        VariableCacheFactory _variableCacheFactory =
                commonInjector.getInstance(VariableCacheFactory.class);
        _mapFactory = commonInjector.getInstance(MapFactory.class);
        PairFactory _pairFactory = commonInjector.getInstance(PairFactory.class);
        SettingFactory _settingFactory = commonInjector.getInstance(SettingFactory.class);

        _settingsRepoHandler = commonInjector.getInstance(PersistentValuesHandler.class)
                .getPersistentValueTypeHandler(SettingsRepo.class.getCanonicalName());

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

        Setting booleanSetting = _settingFactory.make("booleanSetting", "BooleanSetting",
                false, _variableCacheFactory.make());
        _settingsRepo.addEntity(booleanSetting, 1, null);

        Setting collectionOfIntsSetting = _settingFactory.make("collectionOfIntsSetting",
                "CollectionOfIntsSetting", listFactory.make(0),
                _variableCacheFactory.make());
        _settingsRepo.addEntity(collectionOfIntsSetting, 2, subgrouping1);

        Setting collectionOfMapsSetting = _settingFactory.make("collectionOfMapsSetting",
                "CollectionOfMapsSetting", listFactory.make(_mapFactory.make(0, false)),
                _variableCacheFactory.make());
        _settingsRepo.addEntity(collectionOfMapsSetting, 3, subgrouping1_1);

        Setting coordinateSetting = _settingFactory.make("coordinateSetting", "CoordinateSetting",
                _coordinateFactory.make(0,0), _variableCacheFactory.make());
        _settingsRepo.addEntity(coordinateSetting, 4, subgrouping1_2);

        Setting entityUuidSetting = _settingFactory.make("entityUuidSetting", "EntityUuidSetting",
                _entityUuidFactory.createRandomEntityUuid(), _variableCacheFactory.make());
        _settingsRepo.addEntity(entityUuidSetting, 5, subgrouping1_3);

        Setting integerSetting = _settingFactory.make("integerSetting", "IntegerSetting", 0,
                _variableCacheFactory.make());
        _settingsRepo.addEntity(integerSetting, 6, subgrouping2);

        Setting mapOfStringsToIntsSetting = _settingFactory.make("mapOfStringsToIntsSetting",
                "MapOfStringsToIntsSetting", _mapFactory.make("", 0),
                _variableCacheFactory.make());
        _settingsRepo.addEntity(mapOfStringsToIntsSetting, 7, subgrouping2_1);

        Setting mapOfIntsToMapsOfIntsToBooleansSetting =
                _settingFactory.make("mapOfIntsToMapsOfIntsToBooleansSetting",
                        "MapOfIntsToMapsOfIntsToBooleansSetting",
                        _mapFactory.make(0, _mapFactory.make(0, true)),
                        _variableCacheFactory.make());
        _settingsRepo.addEntity(mapOfIntsToMapsOfIntsToBooleansSetting, 8, subgrouping2_2);

        Setting pairOfStringsSetting = _settingFactory.make("pairOfStringsSetting",
                "PairOfStringsSetting", _pairFactory.make("", ""),
                _variableCacheFactory.make());
        _settingsRepo.addEntity(pairOfStringsSetting, 9, subgrouping2_3);

        Setting pairOfStringAndCollectionOfInts =
                _settingFactory.make("pairOfStringAndCollectionOfInts",
                        "PairOfStringAndCollectionOfInts",
                        _pairFactory.make("", listFactory.make(0)),
                        _variableCacheFactory.make());
        _settingsRepo.addEntity(pairOfStringAndCollectionOfInts, 10, subgrouping3);

        Setting stringSetting = _settingFactory.make("stringSetting", "StringSetting", "",
                _variableCacheFactory.make());
        _settingsRepo.addEntity(stringSetting, 11, subgrouping3_1);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    void testWrite() {
        Setting booleanSetting = _settingsRepo.getSetting("booleanSetting");
        booleanSetting.setValue(true);

        Setting collectionOfIntsSetting = _settingsRepo.getSetting("collectionOfIntsSetting");
        ((List<Integer>) collectionOfIntsSetting.getValue()).add(123);
        ((List<Integer>) collectionOfIntsSetting.getValue()).add(456);
        ((List<Integer>) collectionOfIntsSetting.getValue()).add(789);

        Setting collectionOfMapsSetting = _settingsRepo.getSetting("collectionOfMapsSetting");
        Map collectionOfMapsMap1 = _mapFactory.make(0, true);
        collectionOfMapsMap1.put(123, true);
        collectionOfMapsMap1.put(456, false);
        collectionOfMapsMap1.put(789, true);
        Map collectionOfMapsMap2 = _mapFactory.make(0, true);
        collectionOfMapsMap2.put(123, false);
        collectionOfMapsMap2.put(456, true);
        collectionOfMapsMap2.put(789, false);
        ((List<Map>) collectionOfMapsSetting.getValue()).add(collectionOfMapsMap1);
        ((List<Map>) collectionOfMapsSetting.getValue()).add(collectionOfMapsMap2);

        Setting coordinateSetting = _settingsRepo.getSetting("coordinateSetting");
        ((Coordinate) coordinateSetting.getValue()).setX(123);
        ((Coordinate) coordinateSetting.getValue()).setY(456);

        Setting entityUuidSetting = _settingsRepo.getSetting("entityUuidSetting");
        entityUuidSetting.setValue(_entityUuidFactory
                .createFromString("0115d3a5-383a-46f5-92db-6d9c23bbf9b8"));

        Setting integerSetting = _settingsRepo.getSetting("integerSetting");
        integerSetting.setValue(123456789);

        Setting mapOfStringsToIntsSetting = _settingsRepo.getSetting("mapOfStringsToIntsSetting");
        ((Map<String,Integer>) mapOfStringsToIntsSetting.getValue()).put("key1", 123);
        ((Map<String,Integer>) mapOfStringsToIntsSetting.getValue()).put("key2", 456);
        ((Map<String,Integer>) mapOfStringsToIntsSetting.getValue()).put("key3", 789);

        Setting mapOfIntsToMapsOfIntsToBooleansSetting =
                _settingsRepo.getSetting("mapOfIntsToMapsOfIntsToBooleansSetting");
        Map<Integer,Boolean> mapOfIntsToBooleans1 = _mapFactory.make(0, true);
        mapOfIntsToBooleans1.put(1, false);
        mapOfIntsToBooleans1.put(2, true);
        mapOfIntsToBooleans1.put(3, true);
        Map<Integer,Boolean> mapOfIntsToBooleans2 = _mapFactory.make(0, true);
        mapOfIntsToBooleans2.put(4, false);
        mapOfIntsToBooleans2.put(5, true);
        mapOfIntsToBooleans2.put(6, false);
        Map<Integer,Boolean> mapOfIntsToBooleans3 = _mapFactory.make(0, true);
        mapOfIntsToBooleans3.put(7, true);
        mapOfIntsToBooleans3.put(8, false);
        mapOfIntsToBooleans3.put(9, false);
        Map<Integer,Map<Integer,Boolean>> mapOfIntsToMapsOfIntsToBooleans =
                (Map<Integer, Map<Integer, Boolean>>)
                        mapOfIntsToMapsOfIntsToBooleansSetting.getValue();
        mapOfIntsToMapsOfIntsToBooleans.put(123, mapOfIntsToBooleans1);
        mapOfIntsToMapsOfIntsToBooleans.put(456, mapOfIntsToBooleans2);
        mapOfIntsToMapsOfIntsToBooleans.put(789, mapOfIntsToBooleans3);
        mapOfIntsToMapsOfIntsToBooleansSetting.setValue(mapOfIntsToMapsOfIntsToBooleans);

        Setting pairOfStringsSetting = _settingsRepo.getSetting("pairOfStringsSetting");
        ((Pair<String,String>) pairOfStringsSetting.getValue()).setItem1("pairString1");
        ((Pair<String,String>) pairOfStringsSetting.getValue()).setItem2("pairString2");

        Setting pairOfStringAndCollectionOfIntsSetting =
                _settingsRepo.getSetting("pairOfStringAndCollectionOfInts");
        ((Pair<String,List<Integer>>) pairOfStringAndCollectionOfIntsSetting.getValue())
                .setItem1("stringValue");
        List<Integer> collectionOfInts =
                ((Pair<String,List<Integer>>) pairOfStringAndCollectionOfIntsSetting.getValue())
                        .getItem2();
        collectionOfInts.add(123);
        collectionOfInts.add(456);
        collectionOfInts.add(789);

        Setting stringSetting = _settingsRepo.getSetting("stringSetting");
        stringSetting.setValue("stringSettingValue");

        assertEquals(VALUES_STRING, _settingsRepoHandler.write(_settingsRepo));
    }

    @Test
    void testRead() {
        _settingsRepoHandler.read(VALUES_STRING);

        Setting<Boolean> booleanSetting = _settingsRepo.getSetting("booleanSetting");
        assertTrue(booleanSetting.getValue());

        Setting<List<Integer>> collectionOfIntsSetting =
                _settingsRepo.getSetting("collectionOfIntsSetting");
        List<Integer> collectionOfInts = collectionOfIntsSetting.getValue();
        assertNotNull(collectionOfInts);
        assertEquals(3, collectionOfInts.size());
        assertTrue(collectionOfInts.contains(123));
        assertTrue(collectionOfInts.contains(456));
        assertTrue(collectionOfInts.contains(789));

        Setting<List<Map<Integer,Boolean>>> collectionOfMapsSetting =
                _settingsRepo.getSetting("collectionOfMapsSetting");
        List<Map<Integer,Boolean>> collectionOfMaps = collectionOfMapsSetting.getValue();
        assertNotNull(collectionOfMaps);
        assertEquals(2, collectionOfMaps.size());
        Map<Integer,Boolean> mapFromCollection1 = collectionOfMaps.get(0);
        assertNotNull(mapFromCollection1);
        assertEquals(3, mapFromCollection1.size());
        assertTrue(mapFromCollection1.get(123));
        assertFalse(mapFromCollection1.get(456));
        assertTrue(mapFromCollection1.get(789));
        Map<Integer,Boolean> mapFromCollection2 = collectionOfMaps.get(1);
        assertNotNull(mapFromCollection2);
        assertEquals(3, mapFromCollection2.size());
        assertFalse(mapFromCollection2.get(123));
        assertTrue(mapFromCollection2.get(456));
        assertFalse(mapFromCollection2.get(789));

        Setting<Coordinate> coordinateSetting = _settingsRepo.getSetting("coordinateSetting");
        Coordinate coordinate = coordinateSetting.getValue();
        assertNotNull(coordinate);
        assertEquals(123, coordinate.getX());
        assertEquals(456, coordinate.getY());

        Setting<EntityUuid> entityUuidSetting = _settingsRepo.getSetting("entityUuidSetting");
        EntityUuid entityUuid = entityUuidSetting.getValue();
        assertNotNull(entityUuid);
        assertEquals("0115d3a5-383a-46f5-92db-6d9c23bbf9b8", entityUuid.toString());

        Setting<Integer> integerSetting = _settingsRepo.getSetting("integerSetting");
        Integer integer = integerSetting.getValue();
        assertNotNull(integer);
        assertEquals((Integer) 123456789, integer);

        Setting<Map<String,Integer>> mapOfStringsToIntsSetting =
                _settingsRepo.getSetting("mapOfStringsToIntsSetting");
        Map<String,Integer> mapOfStringsToInts = mapOfStringsToIntsSetting.getValue();
        assertNotNull(mapOfStringsToInts);
        assertEquals(3, mapOfStringsToInts.size());
        assertEquals((Integer) 123, mapOfStringsToInts.get("key1"));
        assertEquals((Integer) 456, mapOfStringsToInts.get("key2"));
        assertEquals((Integer) 789, mapOfStringsToInts.get("key3"));

        Setting<Map<Integer,Map<Integer,Boolean>>> mapOfIntsToMapsOfIntsToBooleansSetting =
                _settingsRepo.getSetting("mapOfIntsToMapsOfIntsToBooleansSetting");
        Map<Integer,Map<Integer,Boolean>> mapOfIntsToMapsOfIntsToBooleans =
                mapOfIntsToMapsOfIntsToBooleansSetting.getValue();
        assertNotNull(mapOfIntsToMapsOfIntsToBooleans);
        assertEquals(3, mapOfIntsToMapsOfIntsToBooleans.size());
        Map<Integer,Boolean> mapOfIntsToBooleans1 = mapOfIntsToMapsOfIntsToBooleans.get(123);
        assertNotNull(mapOfIntsToBooleans1);
        assertEquals(3, mapOfIntsToBooleans1.size());
        assertFalse(mapOfIntsToBooleans1.get(1));
        assertTrue(mapOfIntsToBooleans1.get(2));
        assertTrue(mapOfIntsToBooleans1.get(3));
        Map<Integer,Boolean> mapOfIntsToBooleans2 = mapOfIntsToMapsOfIntsToBooleans.get(456);
        assertNotNull(mapOfIntsToBooleans2);
        assertEquals(3, mapOfIntsToBooleans2.size());
        assertFalse(mapOfIntsToBooleans2.get(4));
        assertTrue(mapOfIntsToBooleans2.get(5));
        assertFalse(mapOfIntsToBooleans2.get(6));
        Map<Integer,Boolean> mapOfIntsToBooleans3 = mapOfIntsToMapsOfIntsToBooleans.get(789);
        assertNotNull(mapOfIntsToBooleans3);
        assertEquals(3, mapOfIntsToBooleans3.size());
        assertTrue(mapOfIntsToBooleans3.get(7));
        assertFalse(mapOfIntsToBooleans3.get(8));
        assertFalse(mapOfIntsToBooleans3.get(9));

        Setting<Pair<String,String>> pairOfStringsSetting =
                _settingsRepo.getSetting("pairOfStringsSetting");
        Pair<String,String> pairOfStrings = pairOfStringsSetting.getValue();
        assertNotNull(pairOfStrings);
        assertEquals("pairString1", pairOfStrings.getItem1());
        assertEquals("pairString2", pairOfStrings.getItem2());

        Setting<Pair<String,List<Integer>>> pairOfStringAndCollectionOfIntsSetting =
                _settingsRepo.getSetting("pairOfStringAndCollectionOfInts");
        Pair<String,List<Integer>> pairOfStringAndCollectionOfInts
                = pairOfStringAndCollectionOfIntsSetting.getValue();
        assertNotNull(pairOfStringAndCollectionOfInts);
        assertEquals("stringValue", pairOfStringAndCollectionOfInts.getItem1());
        List<Integer> collectionOfIntsFromPair = pairOfStringAndCollectionOfInts.getItem2();
        assertNotNull(collectionOfIntsFromPair);
        assertEquals(3, collectionOfIntsFromPair.size());
        assertTrue(collectionOfIntsFromPair.contains(123));
        assertTrue(collectionOfIntsFromPair.contains(456));
        assertTrue(collectionOfIntsFromPair.contains(789));

        Setting<String> stringSetting = _settingsRepo.getSetting("stringSetting");
        String stringFromSetting = stringSetting.getValue();
        assertNotNull(stringFromSetting);
        assertEquals("stringSettingValue", stringFromSetting);
    }
}
