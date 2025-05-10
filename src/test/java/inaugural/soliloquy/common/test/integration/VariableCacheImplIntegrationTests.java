package inaugural.soliloquy.common.test.integration;

import com.google.inject.Guice;
import inaugural.soliloquy.common.CommonModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import soliloquy.specs.common.valueobjects.Coordinate2d;
import soliloquy.specs.common.valueobjects.Pair;

import java.util.UUID;

import static inaugural.soliloquy.tools.random.Random.randomString;
import static inaugural.soliloquy.tools.valueobjects.Pair.pairOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VariableCacheImplIntegrationTests {
    private final String HAS_ID_TYPE = HasId.class.getCanonicalName();

    private VariableCache variableCache;

    private ListFactory listFactory;
    private MapFactory mapFactory;
    private RegistryFactory registryFactory;

    private TypeHandler<VariableCache> variableCacheHandler;

    @Mock private HasId mockHasId;
    /** @noinspection rawtypes */
    @Mock private TypeHandler mockHasIdHandler;

    private final String VALUES_STRING =
            "[{\"name\":\"stringVariableName\",\"type\":\"java.lang.String\"," +
                    "\"value\":\"stringVariableValue\"},{\"name\":\"registry\"," +
                    "\"type\":\"soliloquy.specs.common.infrastructure.Registry\\u003csoliloquy" +
                    ".specs.common.shared.HasId\\u003e\",\"value\":\"{\\\"type\\\":\\\"soliloquy" +
                    ".specs.common.shared.HasId\\\",\\\"values\\\":[\\\"hasIdValue\\\"]}\"}," +
                    "{\"name\":\"coordinate\",\"type\":\"soliloquy.specs.common.valueobjects" +
                    ".Coordinate2d\",\"value\":\"{\\\"x\\\":123,\\\"y\\\":456}\"}," +
                    "{\"name\":\"mapOfIntsToMapsOfIntsToBooleans\",\"type\":\"soliloquy.specs" +
                    ".common.infrastructure.Map\\u003cjava.lang.Integer,soliloquy.specs.common" +
                    ".infrastructure.Map\\u003cjava.lang.Integer,java.lang" +
                    ".Boolean\\u003e\\u003e\",\"value\":\"{\\\"keyType\\\":\\\"java.lang" +
                    ".Integer\\\",\\\"valueType\\\":\\\"soliloquy.specs.common.infrastructure" +
                    ".Map\\\\u003cjava.lang.Integer,java.lang.Boolean\\\\u003e\\\"," +
                    "\\\"keys\\\":[\\\"789\\\",\\\"456\\\",\\\"123\\\"]," +
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
                    "{\"name\":\"collectionOfMaps\",\"type\":\"soliloquy.specs.common" +
                    ".infrastructure.List\\u003csoliloquy.specs.common.infrastructure" +
                    ".Map\\u003cjava.lang.Integer,java.lang.Boolean\\u003e\\u003e\"," +
                    "\"value\":\"{\\\"type\\\":\\\"soliloquy.specs.common.infrastructure" +
                    ".Map\\\\u003cjava.lang.Integer,java.lang.Boolean\\\\u003e\\\"," +
                    "\\\"values\\\":[\\\"{\\\\\\\"keyType\\\\\\\":\\\\\\\"java.lang" +
                    ".Integer\\\\\\\",\\\\\\\"valueType\\\\\\\":\\\\\\\"java.lang" +
                    ".Boolean\\\\\\\",\\\\\\\"keys\\\\\\\":[\\\\\\\"1\\\\\\\",\\\\\\\"2\\\\\\\"," +
                    "\\\\\\\"3\\\\\\\"],\\\\\\\"values\\\\\\\":[\\\\\\\"false\\\\\\\"," +
                    "\\\\\\\"true\\\\\\\",\\\\\\\"true\\\\\\\"]}\\\"," +
                    "\\\"{\\\\\\\"keyType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\"," +
                    "\\\\\\\"valueType\\\\\\\":\\\\\\\"java.lang.Boolean\\\\\\\"," +
                    "\\\\\\\"keys\\\\\\\":[\\\\\\\"4\\\\\\\",\\\\\\\"5\\\\\\\"," +
                    "\\\\\\\"6\\\\\\\"],\\\\\\\"values\\\\\\\":[\\\\\\\"false\\\\\\\"," +
                    "\\\\\\\"true\\\\\\\",\\\\\\\"false\\\\\\\"]}\\\"]}\"}," +
                    "{\"name\":\"pairOfStrings\",\"type\":\"soliloquy.specs.common.valueobjects" +
                    ".Pair\",\"value\":\"{\\\"type1\\\":\\\"java.lang.String\\\"," +
                    "\\\"value1\\\":\\\"pairString1\\\",\\\"type2\\\":\\\"java.lang.String\\\"," +
                    "\\\"value2\\\":\\\"pairString2\\\"}\"},{\"name\":\"mapOfStringsToInts\"," +
                    "\"type\":\"soliloquy.specs.common.infrastructure.Map\\u003cjava.lang.String," +
                    "java.lang.Integer\\u003e\",\"value\":\"{\\\"keyType\\\":\\\"java.lang" +
                    ".String\\\",\\\"valueType\\\":\\\"java.lang.Integer\\\"," +
                    "\\\"keys\\\":[\\\"key1\\\",\\\"key2\\\",\\\"key3\\\"]," +
                    "\\\"values\\\":[\\\"123\\\",\\\"456\\\",\\\"789\\\"]}\"}," +
                    "{\"name\":\"booleanVariable\",\"type\":\"java.lang.Boolean\"," +
                    "\"value\":\"true\"},{\"name\":\"integer\",\"type\":\"java.lang.Integer\"," +
                    "\"value\":\"123456789\"},{\"name\":\"uuid\",\"type\":\"java.util.UUID\"," +
                    "\"value\":\"0115d3a5-383a-46f5-92db-6d9c23bbf9b8\"}," +
                    "{\"name\":\"collectionOfInts1\",\"type\":\"soliloquy.specs.common" +
                    ".infrastructure.List\\u003cjava.lang.Integer\\u003e\"," +
                    "\"value\":\"{\\\"type\\\":\\\"java.lang.Integer\\\"," +
                    "\\\"values\\\":[\\\"123\\\",\\\"456\\\",\\\"789\\\"]}\"}," +
                    "{\"name\":\"pairOfStringAndCollectionOfInts\",\"type\":\"soliloquy.specs" +
                    ".common.valueobjects.Pair\",\"value\":\"{\\\"type1\\\":\\\"java.lang" +
                    ".String\\\",\\\"value1\\\":\\\"item1\\\",\\\"type2\\\":\\\"soliloquy.specs" +
                    ".common.infrastructure.List\\\\u003cjava.lang.Integer\\\\u003e\\\"," +
                    "\\\"value2\\\":\\\"{\\\\\\\"type\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\"," +
                    "\\\\\\\"values\\\\\\\":[\\\\\\\"123\\\\\\\",\\\\\\\"456\\\\\\\"," +
                    "\\\\\\\"789\\\\\\\"]}\\\"}\"}]";

    @BeforeEach
    public void setUp() {
        var commonInjector = Guice.createInjector(new CommonModule());

        variableCache = commonInjector.getInstance(VariableCacheFactory.class).make();

        listFactory = commonInjector.getInstance(ListFactory.class);
        mapFactory = commonInjector.getInstance(MapFactory.class);
        registryFactory = commonInjector.getInstance(RegistryFactory.class);

        lenient().when(mockHasId.id()).thenReturn(randomString());

        lenient().when(mockHasIdHandler.getInterfaceName()).thenReturn("<" + HAS_ID_TYPE + ">");
        var mockHasIdArchetype = mock(HasId.class);
        lenient().when(mockHasIdHandler.archetype()).thenReturn(mockHasIdArchetype);
        var hasIdValue = "hasIdValue";
        //noinspection unchecked
        lenient().when(mockHasIdHandler.write(mockHasId)).thenReturn(hasIdValue);
        lenient().when(mockHasIdHandler.read(hasIdValue)).thenReturn(mockHasId);

        var persistentValuesHandler = commonInjector.getInstance(PersistentValuesHandler.class);
        persistentValuesHandler.addTypeHandler(mockHasIdHandler);
        variableCacheHandler =
                persistentValuesHandler.getTypeHandler(VariableCache.class.getCanonicalName());
    }

    @Test
    public void testWrite() {
        variableCache.setVariable("booleanVariable", true);

        var collectionOfInts1 = listFactory.make(0);
        collectionOfInts1.add(123);
        collectionOfInts1.add(456);
        collectionOfInts1.add(789);
        variableCache.setVariable("collectionOfInts1", collectionOfInts1);

        var collectionOfMaps = listFactory.make(mapFactory.make(0, false));
        var mapInCollection1 = mapFactory.make(0, false);
        mapInCollection1.put(1, false);
        mapInCollection1.put(2, true);
        mapInCollection1.put(3, true);
        var mapInCollection2 = mapFactory.make(0, false);
        mapInCollection2.put(4, false);
        mapInCollection2.put(5, true);
        mapInCollection2.put(6, false);
        collectionOfMaps.add(mapInCollection1);
        collectionOfMaps.add(mapInCollection2);
        variableCache.setVariable("collectionOfMaps", collectionOfMaps);

        Coordinate2d coordinate = Coordinate2d.of(123, 456);
        variableCache.setVariable("coordinate", coordinate);

        var uuid = UUID.fromString("0115d3a5-383a-46f5-92db-6d9c23bbf9b8");
        variableCache.setVariable("uuid", uuid);

        var integer = 123456789;
        variableCache.setVariable("integer", integer);

        var mapOfStringsToInts = mapFactory.make("", 0);
        mapOfStringsToInts.put("key1", 123);
        mapOfStringsToInts.put("key2", 456);
        mapOfStringsToInts.put("key3", 789);
        variableCache.setVariable("mapOfStringsToInts", mapOfStringsToInts);

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
        Map<Integer, Map<Integer, Boolean>> mapOfIntsToMapsOfIntsToBooleans =
                mapFactory.make(0, mapFactory.make(0, false));
        mapOfIntsToMapsOfIntsToBooleans.put(123, mapOfIntsToBooleans1);
        mapOfIntsToMapsOfIntsToBooleans.put(456, mapOfIntsToBooleans2);
        mapOfIntsToMapsOfIntsToBooleans.put(789, mapOfIntsToBooleans3);
        variableCache.setVariable("mapOfIntsToMapsOfIntsToBooleans",
                mapOfIntsToMapsOfIntsToBooleans);

        var pairOfStrings = pairOf("pairString1", "pairString2");
        variableCache.setVariable("pairOfStrings", pairOfStrings);

        var collectionOfInts2 = listFactory.make(0);
        collectionOfInts2.add(123);
        collectionOfInts2.add(456);
        collectionOfInts2.add(789);
        Pair<String, List<Integer>> pairOfStringAndCollectionOfInts =
                pairOf("item1", collectionOfInts2);
        variableCache.setVariable("pairOfStringAndCollectionOfInts",
                pairOfStringAndCollectionOfInts);

        var mockHasIdArchetype = mock(HasId.class);
        when(mockHasIdArchetype.getInterfaceName()).thenReturn(HAS_ID_TYPE);
        Registry<HasId> registry = registryFactory.make(mockHasIdArchetype);
        registry.add(mockHasId);
        variableCache.setVariable("registry", registry);

        variableCache.setVariable("stringVariableName", "stringVariableValue");

        assertEquals(VALUES_STRING, variableCacheHandler.write(variableCache));
    }

    @Test
    public void testRead() {
        variableCache = variableCacheHandler.read(VALUES_STRING);

        assertNotNull(variableCache);
        assertEquals(12, variableCache.size());

        assertTrue((boolean) variableCache.getVariable("booleanVariable"));

        List<Integer> collectionOfInts = variableCache.getVariable("collectionOfInts1");
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

        Coordinate2d coordinate2d = variableCache.getVariable("coordinate");
        assertNotNull(coordinate2d);
        assertEquals(123, coordinate2d.X);
        assertEquals(456, coordinate2d.Y);

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
        assertEquals("pairString1", pairOfStrings.item1());
        assertEquals("pairString2", pairOfStrings.item2());

        Pair<String, List<Integer>> pairOfStringAndCollectionOfInts =
                variableCache.getVariable("pairOfStringAndCollectionOfInts");
        assertNotNull(pairOfStringAndCollectionOfInts);
        assertEquals("item1", pairOfStringAndCollectionOfInts.item1());
        assertEquals(3, pairOfStringAndCollectionOfInts.item2().size());
        assertTrue(pairOfStringAndCollectionOfInts.item2().contains(123));
        assertTrue(pairOfStringAndCollectionOfInts.item2().contains(456));
        assertTrue(pairOfStringAndCollectionOfInts.item2().contains(789));

        Registry<HasId> registry = variableCache.getVariable("registry");
        assertNotNull(registry);
        assertEquals(1, registry.size());
        assertTrue(registry.contains(mockHasId));

        assertEquals("stringVariableValue",
                variableCache.getVariable("stringVariableName"));
    }
}
