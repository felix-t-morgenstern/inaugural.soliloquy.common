package inaugural.soliloquy.common.test.integration;

import com.google.inject.Guice;
import inaugural.soliloquy.common.CommonModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.SettingFactory;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.Setting;
import soliloquy.specs.common.infrastructure.SettingsRepo;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Coordinate2d;
import soliloquy.specs.common.valueobjects.Pair;

import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

import static inaugural.soliloquy.tools.valueobjects.Pair.pairOf;
import static org.junit.jupiter.api.Assertions.*;

public class SettingsRepoImplIntegrationTests {
    private final int COORDINATE_SETTING_X = 123;
    private final int COORDINATE_SETTING_Y = 456;

    private SettingsRepo settingsRepo;

    private MapFactory mapFactory;

    private TypeHandler<SettingsRepo> settingsRepoHandler;

    private final Supplier<UUID> RANDOM_UUID_FACTORY = UUID::randomUUID;
    private final Function<String, UUID> UUID_FACTORY_FROM_STRING = UUID::fromString;

    private final String VALUES_STRING =
            "[{\"id\":\"booleanSetting\",\"value\":\"true\"},{\"id\":\"collectionOfIntsSetting\"," +
                    "\"value\":\"{\\\"type\\\":\\\"java.lang.Integer\\\"," +
                    "\\\"values\\\":[\\\"123\\\",\\\"456\\\",\\\"789\\\"]}\"}," +
                    "{\"id\":\"collectionOfMapsSetting\",\"value\":\"{\\\"type\\\":\\\"soliloquy" +
                    ".specs.common.infrastructure.Map\\\\u003cjava.lang.Integer,java.lang" +
                    ".Boolean\\\\u003e\\\",\\\"values\\\":[\\\"{\\\\\\\"keyType" +
                    "\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\"," +
                    "\\\\\\\"valueType\\\\\\\":\\\\\\\"java.lang.Boolean\\\\\\\"," +
                    "\\\\\\\"keys\\\\\\\":[\\\\\\\"789\\\\\\\",\\\\\\\"456\\\\\\\"," +
                    "\\\\\\\"123\\\\\\\"],\\\\\\\"values\\\\\\\":[\\\\\\\"true\\\\\\\"," +
                    "\\\\\\\"false\\\\\\\",\\\\\\\"true\\\\\\\"]}\\\"," +
                    "\\\"{\\\\\\\"keyType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\"," +
                    "\\\\\\\"valueType\\\\\\\":\\\\\\\"java.lang.Boolean\\\\\\\"," +
                    "\\\\\\\"keys\\\\\\\":[\\\\\\\"789\\\\\\\",\\\\\\\"456\\\\\\\"," +
                    "\\\\\\\"123\\\\\\\"],\\\\\\\"values\\\\\\\":[\\\\\\\"false\\\\\\\"," +
                    "\\\\\\\"true\\\\\\\",\\\\\\\"false\\\\\\\"]}\\\"]}\"}," +
                    "{\"id\":\"coordinateSetting\",\"value\":\"{\\\"x\\\":123,\\\"y\\\":456}\"}," +
                    "{\"id\":\"uuidSetting\",\"value\":\"0115d3a5-383a-46f5-92db-6d9c23bbf9b8\"}," +
                    "{\"id\":\"integerSetting\",\"value\":\"123456789\"}," +
                    "{\"id\":\"mapOfStringsToIntsSetting\",\"value\":\"{\\\"keyType\\\":\\\"java" +
                    ".lang.String\\\",\\\"valueType\\\":\\\"java.lang.Integer\\\"," +
                    "\\\"keys\\\":[\\\"key1\\\",\\\"key2\\\",\\\"key3\\\"]," +
                    "\\\"values\\\":[\\\"123\\\",\\\"456\\\",\\\"789\\\"]}\"}," +
                    "{\"id\":\"mapOfIntsToMapsOfIntsToBooleansSetting\"," +
                    "\"value\":\"{\\\"keyType\\\":\\\"java.lang.Integer\\\"," +
                    "\\\"valueType\\\":\\\"soliloquy.specs.common.infrastructure.Map\\\\u003cjava" +
                    ".lang.Integer,java.lang.Boolean\\\\u003e\\\",\\\"keys\\\":[\\\"789\\\"," +
                    "\\\"456\\\",\\\"123\\\"]," +
                    "\\\"values\\\":[\\\"{\\\\\\\"keyType\\\\\\\":\\\\\\\"java.lang" +
                    ".Integer\\\\\\\",\\\\\\\"valueType\\\\\\\":\\\\\\\"java.lang" +
                    ".Boolean\\\\\\\",\\\\\\\"keys\\\\\\\":[\\\\\\\"7\\\\\\\",\\\\\\\"8\\\\\\\"," +
                    "\\\\\\\"9\\\\\\\"],\\\\\\\"values\\\\\\\":[\\\\\\\"true\\\\\\\"," +
                    "\\\\\\\"false\\\\\\\",\\\\\\\"false\\\\\\\"]}\\\"," +
                    "\\\"{\\\\\\\"keyType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\"," +
                    "\\\\\\\"valueType\\\\\\\":\\\\\\\"java.lang.Boolean\\\\\\\"," +
                    "\\\\\\\"keys\\\\\\\":[\\\\\\\"4\\\\\\\",\\\\\\\"5\\\\\\\"," +
                    "\\\\\\\"6\\\\\\\"],\\\\\\\"values\\\\\\\":[\\\\\\\"false\\\\\\\"," +
                    "\\\\\\\"true\\\\\\\",\\\\\\\"false\\\\\\\"]}\\\"," +
                    "\\\"{\\\\\\\"keyType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\"," +
                    "\\\\\\\"valueType\\\\\\\":\\\\\\\"java.lang.Boolean\\\\\\\"," +
                    "\\\\\\\"keys\\\\\\\":[\\\\\\\"1\\\\\\\",\\\\\\\"2\\\\\\\"," +
                    "\\\\\\\"3\\\\\\\"],\\\\\\\"values\\\\\\\":[\\\\\\\"false\\\\\\\"," +
                    "\\\\\\\"true\\\\\\\",\\\\\\\"true\\\\\\\"]}\\\"]}\"}," +
                    "{\"id\":\"pairOfStringsSetting\",\"value\":\"{\\\"type1\\\":\\\"java.lang" +
                    ".String\\\",\\\"value1\\\":\\\"pairString1\\\",\\\"type2\\\":\\\"java.lang" +
                    ".String\\\",\\\"value2\\\":\\\"pairString2\\\"}\"}," +
                    "{\"id\":\"pairOfStringAndCollectionOfInts\"," +
                    "\"value\":\"{\\\"type1\\\":\\\"java.lang.String\\\"," +
                    "\\\"value1\\\":\\\"stringValue\\\",\\\"type2\\\":\\\"soliloquy.specs.common" +
                    ".infrastructure.List\\\\u003cjava.lang.Integer\\\\u003e\\\"," +
                    "\\\"value2\\\":\\\"{\\\\\\\"type\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\"," +
                    "\\\\\\\"values\\\\\\\":[\\\\\\\"123\\\\\\\",\\\\\\\"456\\\\\\\"," +
                    "\\\\\\\"789\\\\\\\"]}\\\"}\"},{\"id\":\"stringSetting\"," +
                    "\"value\":\"stringSettingValue\"}]";

    @BeforeEach
    public void setUp() {
        var commonInjector = Guice.createInjector(new CommonModule());

        settingsRepo = commonInjector.getInstance(SettingsRepo.class);
        var listFactory = commonInjector.getInstance(ListFactory.class);
        var variableCacheFactory = commonInjector.getInstance(VariableCacheFactory.class);
        mapFactory = commonInjector.getInstance(MapFactory.class);
        SettingFactory settingFactory = commonInjector.getInstance(SettingFactory.class);

        settingsRepoHandler = commonInjector.getInstance(PersistentValuesHandler.class)
                .getTypeHandler(SettingsRepo.class.getCanonicalName());

        var subgrouping1 = "subgrouping1";
        settingsRepo.newSubgrouping(100, subgrouping1, null);
        var subgrouping1_1 = "subgrouping1-1";
        settingsRepo.newSubgrouping(101, subgrouping1_1, subgrouping1);
        var subgrouping1_2 = "subgrouping1-2";
        settingsRepo.newSubgrouping(102, subgrouping1_2, subgrouping1);
        var subgrouping1_3 = "subgrouping1-3";
        settingsRepo.newSubgrouping(103, subgrouping1_3, subgrouping1);
        var subgrouping2 = "subgrouping2";
        settingsRepo.newSubgrouping(104, subgrouping2, null);
        var subgrouping2_1 = "subgrouping2-1";
        settingsRepo.newSubgrouping(105, subgrouping2_1, subgrouping2);
        var subgrouping2_2 = "subgrouping2-2";
        settingsRepo.newSubgrouping(106, subgrouping2_2, subgrouping2);
        var subgrouping2_3 = "subgrouping2-3";
        settingsRepo.newSubgrouping(107, subgrouping2_3, subgrouping2);
        var subgrouping3 = "subgrouping3";
        settingsRepo.newSubgrouping(108, subgrouping3, null);
        var subgrouping3_1 = "subgrouping3-1";
        settingsRepo.newSubgrouping(109, subgrouping3_1, subgrouping3);
        var subgrouping3_2 = "subgrouping3-2";
        settingsRepo.newSubgrouping(110, subgrouping3_2, subgrouping3);
        var subgrouping3_3 = "subgrouping3-3";
        settingsRepo.newSubgrouping(111, subgrouping3_3, subgrouping3);

        var booleanSetting = settingFactory.make("booleanSetting", "BooleanSetting",
                false, variableCacheFactory.make());
        settingsRepo.addEntity(booleanSetting, 1, null);

        var collectionOfIntsSetting = settingFactory.make("collectionOfIntsSetting",
                "CollectionOfIntsSetting", listFactory.make(0),
                variableCacheFactory.make());
        settingsRepo.addEntity(collectionOfIntsSetting, 2, subgrouping1);

        var collectionOfMapsSetting = settingFactory.make("collectionOfMapsSetting",
                "CollectionOfMapsSetting", listFactory.make(mapFactory.make(0, false)),
                variableCacheFactory.make());
        settingsRepo.addEntity(collectionOfMapsSetting, 3, subgrouping1_1);

        var coordinateSetting = settingFactory.make("coordinateSetting", "CoordinateSetting",
                Coordinate2d.of(COORDINATE_SETTING_X, COORDINATE_SETTING_Y),
                variableCacheFactory.make());
        settingsRepo.addEntity(coordinateSetting, 4, subgrouping1_2);

        var uuidSetting = settingFactory.make("uuidSetting", "uuidSetting",
                RANDOM_UUID_FACTORY.get(), variableCacheFactory.make());
        settingsRepo.addEntity(uuidSetting, 5, subgrouping1_3);

        var integerSetting = settingFactory.make("integerSetting", "IntegerSetting", 0,
                variableCacheFactory.make());
        settingsRepo.addEntity(integerSetting, 6, subgrouping2);

        var mapOfStringsToIntsSetting = settingFactory.make("mapOfStringsToIntsSetting",
                "MapOfStringsToIntsSetting", mapFactory.make("", 0),
                variableCacheFactory.make());
        settingsRepo.addEntity(mapOfStringsToIntsSetting, 7, subgrouping2_1);

        var mapOfIntsToMapsOfIntsToBooleansSetting =
                settingFactory.make("mapOfIntsToMapsOfIntsToBooleansSetting",
                        "MapOfIntsToMapsOfIntsToBooleansSetting",
                        mapFactory.make(0, mapFactory.make(0, true)),
                        variableCacheFactory.make());
        settingsRepo.addEntity(mapOfIntsToMapsOfIntsToBooleansSetting, 8, subgrouping2_2);

        var pairOfStringsSetting = settingFactory.make("pairOfStringsSetting",
                "PairOfStringsSetting", pairOf("", ""),
                variableCacheFactory.make());
        settingsRepo.addEntity(pairOfStringsSetting, 9, subgrouping2_3);

        var pairOfStringAndCollectionOfInts =
                settingFactory.make("pairOfStringAndCollectionOfInts",
                        "PairOfStringAndCollectionOfInts",
                        pairOf("", listFactory.make(0)),
                        variableCacheFactory.make());
        settingsRepo.addEntity(pairOfStringAndCollectionOfInts, 10, subgrouping3);

        var stringSetting = settingFactory.make("stringSetting", "StringSetting", "",
                variableCacheFactory.make());
        settingsRepo.addEntity(stringSetting, 11, subgrouping3_1);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    public void testWrite() {
        var booleanSetting = settingsRepo.getSetting("booleanSetting");
        booleanSetting.setValue(true);

        var collectionOfIntsSetting = settingsRepo.getSetting("collectionOfIntsSetting");
        ((List<Integer>) collectionOfIntsSetting.getValue()).add(123);
        ((List<Integer>) collectionOfIntsSetting.getValue()).add(456);
        ((List<Integer>) collectionOfIntsSetting.getValue()).add(789);

        var collectionOfMapsSetting = settingsRepo.getSetting("collectionOfMapsSetting");
        var collectionOfMapsMap1 = mapFactory.make(0, true);
        collectionOfMapsMap1.put(123, true);
        collectionOfMapsMap1.put(456, false);
        collectionOfMapsMap1.put(789, true);
        var collectionOfMapsMap2 = mapFactory.make(0, true);
        collectionOfMapsMap2.put(123, false);
        collectionOfMapsMap2.put(456, true);
        collectionOfMapsMap2.put(789, false);
        ((List<Map>) collectionOfMapsSetting.getValue()).add(collectionOfMapsMap1);
        ((List<Map>) collectionOfMapsSetting.getValue()).add(collectionOfMapsMap2);

        var uuidSetting = settingsRepo.getSetting("uuidSetting");
        uuidSetting.setValue(UUID_FACTORY_FROM_STRING
                .apply("0115d3a5-383a-46f5-92db-6d9c23bbf9b8"));

        var integerSetting = settingsRepo.getSetting("integerSetting");
        integerSetting.setValue(123456789);

        var mapOfStringsToIntsSetting = settingsRepo.getSetting("mapOfStringsToIntsSetting");
        ((Map<String, Integer>) mapOfStringsToIntsSetting.getValue()).put("key1", 123);
        ((Map<String, Integer>) mapOfStringsToIntsSetting.getValue()).put("key2", 456);
        ((Map<String, Integer>) mapOfStringsToIntsSetting.getValue()).put("key3", 789);

        var mapOfIntsToMapsOfIntsToBooleansSetting =
                settingsRepo.getSetting("mapOfIntsToMapsOfIntsToBooleansSetting");
        var mapOfIntsToBooleans1 = mapFactory.make(0, true);
        mapOfIntsToBooleans1.put(1, false);
        mapOfIntsToBooleans1.put(2, true);
        mapOfIntsToBooleans1.put(3, true);
        var mapOfIntsToBooleans2 = mapFactory.make(0, true);
        mapOfIntsToBooleans2.put(4, false);
        mapOfIntsToBooleans2.put(5, true);
        mapOfIntsToBooleans2.put(6, false);
        var mapOfIntsToBooleans3 = mapFactory.make(0, true);
        mapOfIntsToBooleans3.put(7, true);
        mapOfIntsToBooleans3.put(8, false);
        mapOfIntsToBooleans3.put(9, false);
        var mapOfIntsToMapsOfIntsToBooleans =
                (Map<Integer, Map<Integer, Boolean>>)
                        mapOfIntsToMapsOfIntsToBooleansSetting.getValue();
        mapOfIntsToMapsOfIntsToBooleans.put(123, mapOfIntsToBooleans1);
        mapOfIntsToMapsOfIntsToBooleans.put(456, mapOfIntsToBooleans2);
        mapOfIntsToMapsOfIntsToBooleans.put(789, mapOfIntsToBooleans3);
        mapOfIntsToMapsOfIntsToBooleansSetting.setValue(mapOfIntsToMapsOfIntsToBooleans);

        var pairOfStringsSetting = settingsRepo.getSetting("pairOfStringsSetting");
        pairOfStringsSetting.setValue(pairOf("pairString1", "pairString2"));

        var pairOfStringAndCollectionOfIntsSetting =
                settingsRepo.getSetting("pairOfStringAndCollectionOfInts");
        var collectionOfInts =
                ((Pair<String, List<Integer>>) pairOfStringAndCollectionOfIntsSetting.getValue()).item2();
        collectionOfInts.add(123);
        collectionOfInts.add(456);
        collectionOfInts.add(789);
        pairOfStringAndCollectionOfIntsSetting.setValue(pairOf("stringValue", collectionOfInts));

        var stringSetting = settingsRepo.getSetting("stringSetting");
        stringSetting.setValue("stringSettingValue");

        assertEquals(VALUES_STRING, settingsRepoHandler.write(settingsRepo));
    }

    @Test
    public void testRead() {
        settingsRepoHandler.read(VALUES_STRING);

        Setting<Boolean> booleanSetting = settingsRepo.getSetting("booleanSetting");
        assertTrue(booleanSetting.getValue());

        Setting<List<Integer>> collectionOfIntsSetting =
                settingsRepo.getSetting("collectionOfIntsSetting");
        List<Integer> collectionOfInts = collectionOfIntsSetting.getValue();
        assertNotNull(collectionOfInts);
        assertEquals(3, collectionOfInts.size());
        assertTrue(collectionOfInts.contains(123));
        assertTrue(collectionOfInts.contains(456));
        assertTrue(collectionOfInts.contains(789));

        Setting<List<Map<Integer, Boolean>>> collectionOfMapsSetting =
                settingsRepo.getSetting("collectionOfMapsSetting");
        List<Map<Integer, Boolean>> collectionOfMaps = collectionOfMapsSetting.getValue();
        assertNotNull(collectionOfMaps);
        assertEquals(2, collectionOfMaps.size());
        var mapFromCollection1 = collectionOfMaps.get(0);
        assertNotNull(mapFromCollection1);
        assertEquals(3, mapFromCollection1.size());
        assertTrue(mapFromCollection1.get(123));
        assertFalse(mapFromCollection1.get(456));
        assertTrue(mapFromCollection1.get(789));
        var mapFromCollection2 = collectionOfMaps.get(1);
        assertNotNull(mapFromCollection2);
        assertEquals(3, mapFromCollection2.size());
        assertFalse(mapFromCollection2.get(123));
        assertTrue(mapFromCollection2.get(456));
        assertFalse(mapFromCollection2.get(789));

        Setting<Coordinate2d> coordinateSetting = settingsRepo.getSetting("coordinateSetting");
        var coordinate = coordinateSetting.getValue();
        assertNotNull(coordinate);
        assertEquals(COORDINATE_SETTING_X, coordinate.X);
        assertEquals(COORDINATE_SETTING_Y, coordinate.Y);

        Setting<UUID> uuidSetting = settingsRepo.getSetting("uuidSetting");
        var uuid = uuidSetting.getValue();
        assertNotNull(uuid);
        assertEquals("0115d3a5-383a-46f5-92db-6d9c23bbf9b8", uuid.toString());

        Setting<Integer> integerSetting = settingsRepo.getSetting("integerSetting");
        var integer = integerSetting.getValue();
        assertNotNull(integer);
        assertEquals((Integer) 123456789, integer);

        Setting<Map<String, Integer>> mapOfStringsToIntsSetting =
                settingsRepo.getSetting("mapOfStringsToIntsSetting");
        var mapOfStringsToInts = mapOfStringsToIntsSetting.getValue();
        assertNotNull(mapOfStringsToInts);
        assertEquals(3, mapOfStringsToInts.size());
        assertEquals((Integer) 123, mapOfStringsToInts.get("key1"));
        assertEquals((Integer) 456, mapOfStringsToInts.get("key2"));
        assertEquals((Integer) 789, mapOfStringsToInts.get("key3"));

        Setting<Map<Integer, Map<Integer, Boolean>>> mapOfIntsToMapsOfIntsToBooleansSetting =
                settingsRepo.getSetting("mapOfIntsToMapsOfIntsToBooleansSetting");
        var mapOfIntsToMapsOfIntsToBooleans =
                mapOfIntsToMapsOfIntsToBooleansSetting.getValue();
        assertNotNull(mapOfIntsToMapsOfIntsToBooleans);
        assertEquals(3, mapOfIntsToMapsOfIntsToBooleans.size());
        var mapOfIntsToBooleans1 = mapOfIntsToMapsOfIntsToBooleans.get(123);
        assertNotNull(mapOfIntsToBooleans1);
        assertEquals(3, mapOfIntsToBooleans1.size());
        assertFalse(mapOfIntsToBooleans1.get(1));
        assertTrue(mapOfIntsToBooleans1.get(2));
        assertTrue(mapOfIntsToBooleans1.get(3));
        var mapOfIntsToBooleans2 = mapOfIntsToMapsOfIntsToBooleans.get(456);
        assertNotNull(mapOfIntsToBooleans2);
        assertEquals(3, mapOfIntsToBooleans2.size());
        assertFalse(mapOfIntsToBooleans2.get(4));
        assertTrue(mapOfIntsToBooleans2.get(5));
        assertFalse(mapOfIntsToBooleans2.get(6));
        var mapOfIntsToBooleans3 = mapOfIntsToMapsOfIntsToBooleans.get(789);
        assertNotNull(mapOfIntsToBooleans3);
        assertEquals(3, mapOfIntsToBooleans3.size());
        assertTrue(mapOfIntsToBooleans3.get(7));
        assertFalse(mapOfIntsToBooleans3.get(8));
        assertFalse(mapOfIntsToBooleans3.get(9));

        Setting<Pair<String, String>> pairOfStringsSetting =
                settingsRepo.getSetting("pairOfStringsSetting");
        var pairOfStrings = pairOfStringsSetting.getValue();
        assertNotNull(pairOfStrings);
        assertEquals("pairString1", pairOfStrings.item1());
        assertEquals("pairString2", pairOfStrings.item2());

        Setting<Pair<String, List<Integer>>> pairOfStringAndCollectionOfIntsSetting =
                settingsRepo.getSetting("pairOfStringAndCollectionOfInts");
        var pairOfStringAndCollectionOfInts = pairOfStringAndCollectionOfIntsSetting.getValue();
        assertNotNull(pairOfStringAndCollectionOfInts);
        assertEquals("stringValue", pairOfStringAndCollectionOfInts.item1());
        var collectionOfIntsFromPair = pairOfStringAndCollectionOfInts.item2();
        assertNotNull(collectionOfIntsFromPair);
        assertEquals(3, collectionOfIntsFromPair.size());
        assertTrue(collectionOfIntsFromPair.contains(123));
        assertTrue(collectionOfIntsFromPair.contains(456));
        assertTrue(collectionOfIntsFromPair.contains(789));

        Setting<String> stringSetting = settingsRepo.getSetting("stringSetting");
        var stringFromSetting = stringSetting.getValue();
        assertNotNull(stringFromSetting);
        assertEquals("stringSettingValue", stringFromSetting);
    }
}
