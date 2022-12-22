package inaugural.soliloquy.common.test.integration;

import com.google.inject.Guice;
import com.google.inject.Injector;
import inaugural.soliloquy.common.CommonModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.shared.HasId;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.common.valueobjects.Pair;

import java.util.UUID;

import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VariableCacheImplIntegrationTests {
    private final String HAS_ID_TYPE = HasId.class.getCanonicalName();

    private VariableCache variableCache;

    private ListFactory listFactory;
    private MapFactory mapFactory;
    private RegistryFactory registryFactory;

    private TypeHandler<VariableCache> variableCacheHandler;

    @Mock private HasId mockHasId;
    /** @noinspection rawtypes*/
    @Mock private TypeHandler mockHasIdHandler;

    private final String VALUES_STRING =
            "[{\"name\":\"stringVariableName\",\"typeName\":\"java.lang.String\"," +
                    "\"serializedValue\":\"stringVariableValue\"},{\"name\":\"registry\"," +
                    "\"typeName\":\"soliloquy.specs.common.infrastructure" +
                    ".Registry\\u003csoliloquy.specs.common.shared.HasId\\u003e\"," +
                    "\"serializedValue\":\"{\\\"typeName\\\":\\\"soliloquy.specs.common.shared" +
                    ".HasId\\\",\\\"serializedValues\\\":[\\\"hasIdValue\\\"]}\"}," +
                    "{\"name\":\"coordinate\",\"typeName\":\"soliloquy.specs.common.valueobjects" +
                    ".Coordinate\",\"serializedValue\":\"{\\\"x\\\":123,\\\"y\\\":456}\"}," +
                    "{\"name\":\"mapOfIntsToMapsOfIntsToBooleans\",\"typeName\":\"soliloquy.specs" +
                    ".common.infrastructure.Map\\u003cjava.lang.Integer,soliloquy.specs.common" +
                    ".infrastructure.Map\\u003cjava.lang.Integer,java.lang" +
                    ".Boolean\\u003e\\u003e\",\"serializedValue\":\"{\\\"keyValueType" +
                    "\\\":\\\"java.lang.Integer\\\",\\\"valueValueType\\\":\\\"soliloquy.specs" +
                    ".common.infrastructure.Map\\\\u003cjava.lang.Integer,java.lang" +
                    ".Boolean\\\\u003e\\\",\\\"keySerializedValues\\\":[\\\"789\\\",\\\"456\\\"," +
                    "\\\"123\\\"],\\\"valueSerializedValues\\\":[\\\"{\\\\\\\"keyValueType" +
                    "\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\"," +
                    "\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.Boolean\\\\\\\"," +
                    "\\\\\\\"keySerializedValues\\\\\\\":[\\\\\\\"7\\\\\\\",\\\\\\\"8\\\\\\\"," +
                    "\\\\\\\"9\\\\\\\"],\\\\\\\"valueSerializedValues\\\\\\\":[\\\\\\\"true" +
                    "\\\\\\\",\\\\\\\"false\\\\\\\",\\\\\\\"false\\\\\\\"]}\\\"," +
                    "\\\"{\\\\\\\"keyValueType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\"," +
                    "\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.Boolean\\\\\\\"," +
                    "\\\\\\\"keySerializedValues\\\\\\\":[\\\\\\\"4\\\\\\\",\\\\\\\"5\\\\\\\"," +
                    "\\\\\\\"6\\\\\\\"],\\\\\\\"valueSerializedValues\\\\\\\":[\\\\\\\"false" +
                    "\\\\\\\",\\\\\\\"true\\\\\\\",\\\\\\\"false\\\\\\\"]}\\\"," +
                    "\\\"{\\\\\\\"keyValueType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\"," +
                    "\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.Boolean\\\\\\\"," +
                    "\\\\\\\"keySerializedValues\\\\\\\":[\\\\\\\"1\\\\\\\",\\\\\\\"2\\\\\\\"," +
                    "\\\\\\\"3\\\\\\\"],\\\\\\\"valueSerializedValues\\\\\\\":[\\\\\\\"false" +
                    "\\\\\\\",\\\\\\\"true\\\\\\\",\\\\\\\"true\\\\\\\"]}\\\"]}\"}," +
                    "{\"name\":\"collectionOfMaps\",\"typeName\":\"soliloquy.specs.common" +
                    ".infrastructure.List\\u003csoliloquy.specs.common.infrastructure" +
                    ".Map\\u003cjava.lang.Integer,java.lang.Boolean\\u003e\\u003e\"," +
                    "\"serializedValue\":\"{\\\"typeName\\\":\\\"soliloquy.specs.common" +
                    ".infrastructure.Map\\\\u003cjava.lang.Integer,java.lang" +
                    ".Boolean\\\\u003e\\\",\\\"serializedValues\\\":[\\\"{\\\\\\\"keyValueType" +
                    "\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\"," +
                    "\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.Boolean\\\\\\\"," +
                    "\\\\\\\"keySerializedValues\\\\\\\":[\\\\\\\"1\\\\\\\",\\\\\\\"2\\\\\\\"," +
                    "\\\\\\\"3\\\\\\\"],\\\\\\\"valueSerializedValues\\\\\\\":[\\\\\\\"false" +
                    "\\\\\\\",\\\\\\\"true\\\\\\\",\\\\\\\"true\\\\\\\"]}\\\"," +
                    "\\\"{\\\\\\\"keyValueType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\"," +
                    "\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.Boolean\\\\\\\"," +
                    "\\\\\\\"keySerializedValues\\\\\\\":[\\\\\\\"4\\\\\\\",\\\\\\\"5\\\\\\\"," +
                    "\\\\\\\"6\\\\\\\"],\\\\\\\"valueSerializedValues\\\\\\\":[\\\\\\\"false" +
                    "\\\\\\\",\\\\\\\"true\\\\\\\",\\\\\\\"false\\\\\\\"]}\\\"]}\"}," +
                    "{\"name\":\"pairOfStrings\",\"typeName\":\"soliloquy.specs.common" +
                    ".valueobjects.Pair\",\"serializedValue\":\"{\\\"valueType1\\\":\\\"java.lang" +
                    ".String\\\",\\\"serializedValue1\\\":\\\"pairString1\\\"," +
                    "\\\"valueType2\\\":\\\"java.lang.String\\\"," +
                    "\\\"serializedValue2\\\":\\\"pairString2\\\"}\"}," +
                    "{\"name\":\"mapOfStringsToInts\",\"typeName\":\"soliloquy.specs.common" +
                    ".infrastructure.Map\\u003cjava.lang.String,java.lang.Integer\\u003e\"," +
                    "\"serializedValue\":\"{\\\"keyValueType\\\":\\\"java.lang.String\\\"," +
                    "\\\"valueValueType\\\":\\\"java.lang.Integer\\\"," +
                    "\\\"keySerializedValues\\\":[\\\"key1\\\",\\\"key2\\\",\\\"key3\\\"]," +
                    "\\\"valueSerializedValues\\\":[\\\"123\\\",\\\"456\\\",\\\"789\\\"]}\"}," +
                    "{\"name\":\"booleanVariable\",\"typeName\":\"java.lang.Boolean\"," +
                    "\"serializedValue\":\"true\"},{\"name\":\"integer\",\"typeName\":\"java.lang" +
                    ".Integer\",\"serializedValue\":\"123456789\"},{\"name\":\"uuid\"," +
                    "\"typeName\":\"java.util.UUID\"," +
                    "\"serializedValue\":\"0115d3a5-383a-46f5-92db-6d9c23bbf9b8\"}," +
                    "{\"name\":\"collectionOfInts1\",\"typeName\":\"soliloquy.specs.common" +
                    ".infrastructure.List\\u003cjava.lang.Integer\\u003e\"," +
                    "\"serializedValue\":\"{\\\"typeName\\\":\\\"java.lang.Integer\\\"," +
                    "\\\"serializedValues\\\":[\\\"123\\\",\\\"456\\\",\\\"789\\\"]}\"}," +
                    "{\"name\":\"pairOfStringAndCollectionOfInts\",\"typeName\":\"soliloquy.specs" +
                    ".common.valueobjects.Pair\"," +
                    "\"serializedValue\":\"{\\\"valueType1\\\":\\\"java.lang.String\\\"," +
                    "\\\"serializedValue1\\\":\\\"item1\\\",\\\"valueType2\\\":\\\"soliloquy" +
                    ".specs.common.infrastructure.List\\\\u003cjava.lang.Integer\\\\u003e\\\"," +
                    "\\\"serializedValue2\\\":\\\"{\\\\\\\"typeName\\\\\\\":\\\\\\\"java.lang" +
                    ".Integer\\\\\\\",\\\\\\\"serializedValues\\\\\\\":[\\\\\\\"123\\\\\\\"," +
                    "\\\\\\\"456\\\\\\\",\\\\\\\"789\\\\\\\"]}\\\"}\"}]";

    @BeforeEach
    void setUp() {
        Injector commonInjector = Guice.createInjector(new CommonModule());

        variableCache =
                commonInjector.getInstance(VariableCacheFactory.class).make();

        listFactory = commonInjector.getInstance(ListFactory.class);
        mapFactory = commonInjector.getInstance(MapFactory.class);
        registryFactory = commonInjector.getInstance(RegistryFactory.class);

        mockHasId = mock(HasId.class);
        when(mockHasId.id()).thenReturn(randomString());

        mockHasIdHandler = mock(TypeHandler.class);
        when(mockHasIdHandler.getInterfaceName()).thenReturn("<" + HAS_ID_TYPE + ">");
        HasId mockHasIdArchetype = mock(HasId.class);
        when(mockHasIdArchetype.getInterfaceName()).thenReturn(HAS_ID_TYPE);
        when(mockHasIdHandler.getArchetype()).thenReturn(mockHasIdArchetype);
        String hasIdValue = "hasIdValue";
        //noinspection unchecked
        when(mockHasIdHandler.write(mockHasId)).thenReturn(hasIdValue);
        when(mockHasIdHandler.read(hasIdValue)).thenReturn(mockHasId);

        PersistentValuesHandler persistentValuesHandler =
                commonInjector.getInstance(PersistentValuesHandler.class);
        persistentValuesHandler.addTypeHandler(mockHasIdHandler);
        variableCacheHandler = persistentValuesHandler
                .getTypeHandler(VariableCache.class.getCanonicalName());
    }

    @Test
    void testWrite() {
        variableCache.setVariable("booleanVariable", true);

        List<Integer> collectionOfInts1 = listFactory.make(0);
        collectionOfInts1.add(123);
        collectionOfInts1.add(456);
        collectionOfInts1.add(789);
        variableCache.setVariable("collectionOfInts1", collectionOfInts1);

        List<Map<Integer, Boolean>> collectionOfMaps =
                listFactory.make(mapFactory.make(0, false));
        Map<Integer, Boolean> mapInCollection1 = mapFactory.make(0, false);
        mapInCollection1.put(1, false);
        mapInCollection1.put(2, true);
        mapInCollection1.put(3, true);
        Map<Integer, Boolean> mapInCollection2 = mapFactory.make(0, false);
        mapInCollection2.put(4, false);
        mapInCollection2.put(5, true);
        mapInCollection2.put(6, false);
        collectionOfMaps.add(mapInCollection1);
        collectionOfMaps.add(mapInCollection2);
        variableCache.setVariable("collectionOfMaps", collectionOfMaps);

        Coordinate coordinate = Coordinate.of(123, 456);
        variableCache.setVariable("coordinate", coordinate);

        UUID uuid = UUID.fromString("0115d3a5-383a-46f5-92db-6d9c23bbf9b8");
        variableCache.setVariable("uuid", uuid);

        Integer integer = 123456789;
        variableCache.setVariable("integer", integer);

        Map<String, Integer> mapOfStringsToInts = mapFactory.make("", 0);
        mapOfStringsToInts.put("key1", 123);
        mapOfStringsToInts.put("key2", 456);
        mapOfStringsToInts.put("key3", 789);
        variableCache.setVariable("mapOfStringsToInts", mapOfStringsToInts);

        Map<Integer, Boolean> mapOfIntsToBooleans1 = mapFactory.make(0, true);
        mapOfIntsToBooleans1.put(1, false);
        mapOfIntsToBooleans1.put(2, true);
        mapOfIntsToBooleans1.put(3, true);
        Map<Integer, Boolean> mapOfIntsToBooleans2 = mapFactory.make(0, true);
        mapOfIntsToBooleans2.put(4, false);
        mapOfIntsToBooleans2.put(5, true);
        mapOfIntsToBooleans2.put(6, false);
        Map<Integer, Boolean> mapOfIntsToBooleans3 = mapFactory.make(0, true);
        mapOfIntsToBooleans3.put(7, true);
        mapOfIntsToBooleans3.put(8, false);
        mapOfIntsToBooleans3.put(9, false);
        Map<Integer, Map<Integer, Boolean>> mapOfIntsToMapsOfIntsToBooleans =
                mapFactory.make(0, mapFactory.make(0, false));
        mapOfIntsToMapsOfIntsToBooleans.put(123, mapOfIntsToBooleans1);
        mapOfIntsToMapsOfIntsToBooleans.put(456, mapOfIntsToBooleans2);
        mapOfIntsToMapsOfIntsToBooleans.put(789, mapOfIntsToBooleans3);
        variableCache.setVariable("mapOfIntsToMapsOfIntsToBooleans",
                mapOfIntsToMapsOfIntsToBooleans);

        Pair<String, String> pairOfStrings = new Pair<>("pairString1", "pairString2");
        variableCache.setVariable("pairOfStrings", pairOfStrings);

        List<Integer> collectionOfInts2 = listFactory.make(0);
        collectionOfInts2.add(123);
        collectionOfInts2.add(456);
        collectionOfInts2.add(789);
        Pair<String, List<Integer>> pairOfStringAndCollectionOfInts =
                new Pair<>("item1", collectionOfInts2);
        variableCache.setVariable("pairOfStringAndCollectionOfInts",
                pairOfStringAndCollectionOfInts);

        HasId mockHasIdArchetype = mock(HasId.class);
        when(mockHasIdArchetype.getInterfaceName()).thenReturn(HAS_ID_TYPE);
        Registry<HasId> registry = registryFactory.make(mockHasIdArchetype);
        registry.add(mockHasId);
        variableCache.setVariable("registry", registry);

        variableCache.setVariable("stringVariableName", "stringVariableValue");

        assertEquals(VALUES_STRING, variableCacheHandler.write(variableCache));
    }

    @Test
    void testRead() {
        variableCache = variableCacheHandler.read(VALUES_STRING);

        assertNotNull(variableCache);
        assertEquals(12, variableCache.size());

        assertTrue((boolean) variableCache.getVariable("booleanVariable"));

        List<Integer> collectionOfInts =
                variableCache.getVariable("collectionOfInts1");
        assertNotNull(collectionOfInts);
        assertEquals(3, collectionOfInts.size());
        assertTrue(collectionOfInts.contains(123));
        assertTrue(collectionOfInts.contains(456));
        assertTrue(collectionOfInts.contains(789));

        List<Map<Integer, Boolean>> collectionOfMaps =
                variableCache.getVariable("collectionOfMaps");
        assertNotNull(collectionOfMaps);
        assertEquals(2, collectionOfMaps.size());
        Map<Integer, Boolean> mapOfIntegersToBooleans1 = collectionOfMaps.get(0);
        assertFalse(mapOfIntegersToBooleans1.get(1));
        assertTrue(mapOfIntegersToBooleans1.get(2));
        assertTrue(mapOfIntegersToBooleans1.get(3));
        Map<Integer, Boolean> mapOfIntegersToBooleans2 = collectionOfMaps.get(1);
        assertFalse(mapOfIntegersToBooleans2.get(4));
        assertTrue(mapOfIntegersToBooleans2.get(5));
        assertFalse(mapOfIntegersToBooleans2.get(6));

        Coordinate coordinate = variableCache.getVariable("coordinate");
        assertNotNull(coordinate);
        assertEquals(123, coordinate.x());
        assertEquals(456, coordinate.y());

        UUID uuid = variableCache.getVariable("uuid");
        assertNotNull(uuid);
        assertEquals("0115d3a5-383a-46f5-92db-6d9c23bbf9b8", uuid.toString());

        Integer integer = variableCache.getVariable("integer");
        assertNotNull(integer);
        assertEquals((Integer) 123456789, integer);

        Map<Integer, Map<Integer, Boolean>> mapOfIntsToMapsOfIntsToBooleans =
                variableCache.getVariable("mapOfIntsToMapsOfIntsToBooleans");
        assertNotNull(mapOfIntsToMapsOfIntsToBooleans);
        assertEquals(3, mapOfIntsToMapsOfIntsToBooleans.size());
        Map<Integer, Boolean> map1 = mapOfIntsToMapsOfIntsToBooleans.get(123);
        assertEquals(3, map1.size());
        assertFalse(map1.get(1));
        assertTrue(map1.get(2));
        assertTrue(map1.get(3));
        Map<Integer, Boolean> map2 = mapOfIntsToMapsOfIntsToBooleans.get(456);
        assertEquals(3, map2.size());
        assertFalse(map2.get(4));
        assertTrue(map2.get(5));
        assertFalse(map2.get(6));
        Map<Integer, Boolean> map3 = mapOfIntsToMapsOfIntsToBooleans.get(789);
        assertEquals(3, map3.size());
        assertTrue(map3.get(7));
        assertFalse(map3.get(8));
        assertFalse(map3.get(9));

        Pair<String, String> pairOfStrings = variableCache.getVariable("pairOfStrings");
        assertNotNull(pairOfStrings);
        assertEquals("pairString1", pairOfStrings.getItem1());
        assertEquals("pairString2", pairOfStrings.getItem2());

        Pair<String, List<Integer>> pairOfStringAndCollectionOfInts =
                variableCache.getVariable("pairOfStringAndCollectionOfInts");
        assertNotNull(pairOfStringAndCollectionOfInts);
        assertEquals("item1", pairOfStringAndCollectionOfInts.getItem1());
        assertEquals(3, pairOfStringAndCollectionOfInts.getItem2().size());
        assertTrue(pairOfStringAndCollectionOfInts.getItem2().contains(123));
        assertTrue(pairOfStringAndCollectionOfInts.getItem2().contains(456));
        assertTrue(pairOfStringAndCollectionOfInts.getItem2().contains(789));

        Registry<HasId> registry = variableCache.getVariable("registry");
        assertNotNull(registry);
        assertEquals(1, registry.size());
        assertTrue(registry.contains(mockHasId));

        assertEquals("stringVariableValue",
                variableCache.getVariable("stringVariableName"));
    }
}
