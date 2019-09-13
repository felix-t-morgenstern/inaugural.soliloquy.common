package inaugural.soliloquy.common.test.integrationtests;

import com.google.inject.Guice;
import com.google.inject.Injector;
import inaugural.soliloquy.common.CommonModule;
import inaugural.soliloquy.common.test.stubs.HasIdAndNameStub;
import inaugural.soliloquy.common.test.stubs.PersistentHasIdAndNameHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.*;
import soliloquy.specs.common.infrastructure.*;
import soliloquy.specs.common.valueobjects.*;

import static org.junit.jupiter.api.Assertions.*;

class VariableCacheImplIntegrationTests {
    private VariableCache _variableCache;

    private CollectionFactory _collectionFactory;
    private CoordinateFactory _coordinateFactory;
    private EntityUuidFactory _entityUuidFactory;
    private MapFactory _mapFactory;
    private PairFactory _pairFactory;
    private RegistryFactory _registryFactory;

    private PersistentValueTypeHandler<VariableCache> _varCacheHandler;

    private final String VALUES_STRING = "[{\"name\":\"stringVariableName\",\"typeName\":\"java.lang.String\",\"serializedValue\":\"stringVariableValue\"},{\"name\":\"registry\",\"typeName\":\"soliloquy.specs.common.infrastructure.Registry\\u003cinaugural.soliloquy.common.test.stubs.HasIdAndNameStub\\u003e\",\"serializedValue\":\"{\\\"typeName\\\":\\\"inaugural.soliloquy.common.test.stubs.HasIdAndNameStub\\\",\\\"serializedValues\\\":[\\\"{\\\\\\\"id\\\\\\\":\\\\\\\"id2\\\\\\\",\\\\\\\"name\\\\\\\":\\\\\\\"name2\\\\\\\"}\\\",\\\"{\\\\\\\"id\\\\\\\":\\\\\\\"id1\\\\\\\",\\\\\\\"name\\\\\\\":\\\\\\\"name1\\\\\\\"}\\\",\\\"{\\\\\\\"id\\\\\\\":\\\\\\\"id3\\\\\\\",\\\\\\\"name\\\\\\\":\\\\\\\"name3\\\\\\\"}\\\"]}\"},{\"name\":\"coordinate\",\"typeName\":\"soliloquy.specs.common.valueobjects.Coordinate\",\"serializedValue\":\"{\\\"x\\\":123,\\\"y\\\":456}\"},{\"name\":\"entityUuid\",\"typeName\":\"soliloquy.specs.common.valueobjects.EntityUuid\",\"serializedValue\":\"0115d3a5-383a-46f5-92db-6d9c23bbf9b8\"},{\"name\":\"mapOfIntsToMapsOfIntsToBooleans\",\"typeName\":\"soliloquy.specs.common.infrastructure.Map\\u003cjava.lang.Integer,soliloquy.specs.common.infrastructure.Map\\u003cjava.lang.Integer,java.lang.Boolean\\u003e\\u003e\",\"serializedValue\":\"{\\\"keyValueType\\\":\\\"java.lang.Integer\\\",\\\"valueValueType\\\":\\\"soliloquy.specs.common.infrastructure.Map\\\\u003cjava.lang.Integer,java.lang.Boolean\\\\u003e\\\",\\\"keySerializedValues\\\":[\\\"789\\\",\\\"456\\\",\\\"123\\\"],\\\"valueSerializedValues\\\":[\\\"{\\\\\\\"keyValueType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\",\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.Boolean\\\\\\\",\\\\\\\"keySerializedValues\\\\\\\":[\\\\\\\"7\\\\\\\",\\\\\\\"8\\\\\\\",\\\\\\\"9\\\\\\\"],\\\\\\\"valueSerializedValues\\\\\\\":[\\\\\\\"true\\\\\\\",\\\\\\\"false\\\\\\\",\\\\\\\"false\\\\\\\"]}\\\",\\\"{\\\\\\\"keyValueType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\",\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.Boolean\\\\\\\",\\\\\\\"keySerializedValues\\\\\\\":[\\\\\\\"4\\\\\\\",\\\\\\\"5\\\\\\\",\\\\\\\"6\\\\\\\"],\\\\\\\"valueSerializedValues\\\\\\\":[\\\\\\\"false\\\\\\\",\\\\\\\"true\\\\\\\",\\\\\\\"false\\\\\\\"]}\\\",\\\"{\\\\\\\"keyValueType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\",\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.Boolean\\\\\\\",\\\\\\\"keySerializedValues\\\\\\\":[\\\\\\\"1\\\\\\\",\\\\\\\"2\\\\\\\",\\\\\\\"3\\\\\\\"],\\\\\\\"valueSerializedValues\\\\\\\":[\\\\\\\"false\\\\\\\",\\\\\\\"true\\\\\\\",\\\\\\\"true\\\\\\\"]}\\\"]}\"},{\"name\":\"collectionOfMaps\",\"typeName\":\"soliloquy.specs.common.infrastructure.Collection\\u003csoliloquy.specs.common.infrastructure.Map\\u003cjava.lang.Integer,java.lang.Boolean\\u003e\\u003e\",\"serializedValue\":\"{\\\"typeName\\\":\\\"soliloquy.specs.common.infrastructure.Map\\\\u003cjava.lang.Integer,java.lang.Boolean\\\\u003e\\\",\\\"serializedValues\\\":[\\\"{\\\\\\\"keyValueType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\",\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.Boolean\\\\\\\",\\\\\\\"keySerializedValues\\\\\\\":[\\\\\\\"1\\\\\\\",\\\\\\\"2\\\\\\\",\\\\\\\"3\\\\\\\"],\\\\\\\"valueSerializedValues\\\\\\\":[\\\\\\\"false\\\\\\\",\\\\\\\"true\\\\\\\",\\\\\\\"true\\\\\\\"]}\\\",\\\"{\\\\\\\"keyValueType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\",\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.Boolean\\\\\\\",\\\\\\\"keySerializedValues\\\\\\\":[\\\\\\\"4\\\\\\\",\\\\\\\"5\\\\\\\",\\\\\\\"6\\\\\\\"],\\\\\\\"valueSerializedValues\\\\\\\":[\\\\\\\"false\\\\\\\",\\\\\\\"true\\\\\\\",\\\\\\\"false\\\\\\\"]}\\\"]}\"},{\"name\":\"pairOfStrings\",\"typeName\":\"soliloquy.specs.common.infrastructure.Pair\\u003cjava.lang.String,java.lang.String\\u003e\",\"serializedValue\":\"{\\\"valueType1\\\":\\\"java.lang.String\\\",\\\"serializedValue1\\\":\\\"pairString1\\\",\\\"valueType2\\\":\\\"java.lang.String\\\",\\\"serializedValue2\\\":\\\"pairString2\\\"}\"},{\"name\":\"mapOfStringsToInts\",\"typeName\":\"soliloquy.specs.common.infrastructure.Map\\u003cjava.lang.String,java.lang.Integer\\u003e\",\"serializedValue\":\"{\\\"keyValueType\\\":\\\"java.lang.String\\\",\\\"valueValueType\\\":\\\"java.lang.Integer\\\",\\\"keySerializedValues\\\":[\\\"key1\\\",\\\"key2\\\",\\\"key3\\\"],\\\"valueSerializedValues\\\":[\\\"123\\\",\\\"456\\\",\\\"789\\\"]}\"},{\"name\":\"booleanVariable\",\"typeName\":\"java.lang.Boolean\",\"serializedValue\":\"true\"},{\"name\":\"collectionOfInts\",\"typeName\":\"soliloquy.specs.common.infrastructure.Collection\\u003cjava.lang.Integer\\u003e\",\"serializedValue\":\"{\\\"typeName\\\":\\\"java.lang.Integer\\\",\\\"serializedValues\\\":[\\\"123\\\",\\\"456\\\",\\\"789\\\"]}\"},{\"name\":\"integer\",\"typeName\":\"java.lang.Integer\",\"serializedValue\":\"123456789\"},{\"name\":\"pairOfStringAndCollectionOfInts\",\"typeName\":\"soliloquy.specs.common.infrastructure.Pair\\u003cjava.lang.String,soliloquy.specs.common.infrastructure.Collection\\u003cjava.lang.Integer\\u003e\\u003e\",\"serializedValue\":\"{\\\"valueType1\\\":\\\"java.lang.String\\\",\\\"serializedValue1\\\":\\\"item1\\\",\\\"valueType2\\\":\\\"soliloquy.specs.common.infrastructure.Collection\\\\u003cjava.lang.Integer\\\\u003e\\\",\\\"serializedValue2\\\":\\\"{\\\\\\\"typeName\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\",\\\\\\\"serializedValues\\\\\\\":[\\\\\\\"123\\\\\\\",\\\\\\\"456\\\\\\\",\\\\\\\"789\\\\\\\"]}\\\"}\"}]";

    @BeforeEach
    void setUp() {
        Injector commonInjector = Guice.createInjector(new CommonModule());

        _variableCache =
                commonInjector.getInstance(VariableCacheFactory.class).make();

        _collectionFactory = commonInjector.getInstance(CollectionFactory.class);
        _coordinateFactory = commonInjector.getInstance(CoordinateFactory.class);
        _entityUuidFactory = commonInjector.getInstance(EntityUuidFactory.class);
        _mapFactory = commonInjector.getInstance(MapFactory.class);
        _pairFactory = commonInjector.getInstance(PairFactory.class);
        _registryFactory = commonInjector.getInstance(RegistryFactory.class);

        PersistentValuesHandler persistentValuesHandler =
                commonInjector.getInstance(PersistentValuesHandler.class);
        persistentValuesHandler.addPersistentValueTypeHandler(new PersistentHasIdAndNameHandler());
        _varCacheHandler = persistentValuesHandler
                .getPersistentValueTypeHandler(VariableCache.class.getCanonicalName());
    }

    @Test
    void testWrite() {
        _variableCache.setVariable("booleanVariable", true);

        Collection<Integer> collectionOfInts = _collectionFactory.make(0);
        collectionOfInts.add(123);
        collectionOfInts.add(456);
        collectionOfInts.add(789);
        _variableCache.setVariable("collectionOfInts", collectionOfInts);

        Collection<Map<Integer,Boolean>> collectionOfMaps =
                _collectionFactory.make(_mapFactory.make(0, false));
        Map<Integer,Boolean> mapInCollection1 = _mapFactory.make(0, false);
        mapInCollection1.put(1, false);
        mapInCollection1.put(2, true);
        mapInCollection1.put(3, true);
        Map<Integer,Boolean> mapInCollection2 = _mapFactory.make(0, false);
        mapInCollection2.put(4, false);
        mapInCollection2.put(5, true);
        mapInCollection2.put(6, false);
        collectionOfMaps.add(mapInCollection1);
        collectionOfMaps.add(mapInCollection2);
        _variableCache.setVariable("collectionOfMaps", collectionOfMaps);

        Coordinate coordinate = _coordinateFactory.make(123, 456);
        _variableCache.setVariable("coordinate", coordinate);

        EntityUuid entityUuid =
                _entityUuidFactory.createFromString("0115d3a5-383a-46f5-92db-6d9c23bbf9b8");
        _variableCache.setVariable("entityUuid", entityUuid);

        Integer integer = 123456789;
        _variableCache.setVariable("integer", integer);

        Map<String,Integer> mapOfStringsToInts = _mapFactory.make("", 0);
        mapOfStringsToInts.put("key1", 123);
        mapOfStringsToInts.put("key2", 456);
        mapOfStringsToInts.put("key3", 789);
        _variableCache.setVariable("mapOfStringsToInts", mapOfStringsToInts);

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
                _mapFactory.make(0, _mapFactory.make(0, false));
        mapOfIntsToMapsOfIntsToBooleans.put(123, mapOfIntsToBooleans1);
        mapOfIntsToMapsOfIntsToBooleans.put(456, mapOfIntsToBooleans2);
        mapOfIntsToMapsOfIntsToBooleans.put(789, mapOfIntsToBooleans3);
        _variableCache.setVariable("mapOfIntsToMapsOfIntsToBooleans",
                mapOfIntsToMapsOfIntsToBooleans);

        Pair<String,String> pairOfStrings = _pairFactory.make("pairString1", "pairString2");
        _variableCache.setVariable("pairOfStrings", pairOfStrings);

        Pair<String,Collection<Integer>> pairOfStringAndCollectionOfInts =
                _pairFactory.make("", _collectionFactory.make(0));
        pairOfStringAndCollectionOfInts.setItem1("item1");
        pairOfStringAndCollectionOfInts.getItem2().add(123);
        pairOfStringAndCollectionOfInts.getItem2().add(456);
        pairOfStringAndCollectionOfInts.getItem2().add(789);
        _variableCache.setVariable("pairOfStringAndCollectionOfInts",
                pairOfStringAndCollectionOfInts);

        Registry<HasIdAndNameStub> registry = _registryFactory.make(
                PersistentHasIdAndNameHandler.ARCHETYPE);
        registry.register(new HasIdAndNameStub("id1", "name1"));
        registry.register(new HasIdAndNameStub("id2", "name2"));
        registry.register(new HasIdAndNameStub("id3", "name3"));
        _variableCache.setVariable("registry", registry);

        _variableCache.setVariable("stringVariableName", "stringVariableValue");

        assertEquals(VALUES_STRING, _varCacheHandler.write(_variableCache));
    }

    @Test
    void testRead() {
        _variableCache = _varCacheHandler.read(VALUES_STRING);

        assertNotNull(_variableCache);
        assertEquals(12, _variableCache.size());

        assertTrue((boolean) _variableCache.getVariable("booleanVariable"));

        Collection<Integer> collectionOfInts =
                _variableCache.getVariable("collectionOfInts");
        assertNotNull(collectionOfInts);
        assertEquals(3, collectionOfInts.size());
        assertTrue(collectionOfInts.contains(123));
        assertTrue(collectionOfInts.contains(456));
        assertTrue(collectionOfInts.contains(789));

        Collection<Map<Integer,Boolean>> collectionOfMaps =
                _variableCache.getVariable("collectionOfMaps");
        assertNotNull(collectionOfMaps);
        assertEquals(2, collectionOfMaps.size());
        Map<Integer,Boolean> mapOfIntegersToBooleans1 = collectionOfMaps.get(0);
        assertFalse(mapOfIntegersToBooleans1.get(1));
        assertTrue(mapOfIntegersToBooleans1.get(2));
        assertTrue(mapOfIntegersToBooleans1.get(3));
        Map<Integer,Boolean> mapOfIntegersToBooleans2 = collectionOfMaps.get(1);
        assertFalse(mapOfIntegersToBooleans2.get(4));
        assertTrue(mapOfIntegersToBooleans2.get(5));
        assertFalse(mapOfIntegersToBooleans2.get(6));

        Coordinate coordinate = _variableCache.getVariable("coordinate");
        assertNotNull(coordinate);
        assertEquals(123, coordinate.getX());
        assertEquals(456, coordinate.getY());

        EntityUuid entityUuid = _variableCache.getVariable("entityUuid");
        assertNotNull(entityUuid);
        assertEquals("0115d3a5-383a-46f5-92db-6d9c23bbf9b8", entityUuid.toString());

        Integer integer = _variableCache.getVariable("integer");
        assertNotNull(integer);
        assertEquals((Integer) 123456789, integer);

        Map<Integer,Map<Integer,Boolean>> mapOfIntsToMapsOfIntsToBooleans =
                _variableCache.getVariable("mapOfIntsToMapsOfIntsToBooleans");
        assertNotNull(mapOfIntsToMapsOfIntsToBooleans);
        assertEquals(3, mapOfIntsToMapsOfIntsToBooleans.size());
        Map<Integer,Boolean> map1 = mapOfIntsToMapsOfIntsToBooleans.get(123);
        assertEquals(3, map1.size());
        assertFalse(map1.get(1));
        assertTrue(map1.get(2));
        assertTrue(map1.get(3));
        Map<Integer,Boolean> map2 = mapOfIntsToMapsOfIntsToBooleans.get(456);
        assertEquals(3, map2.size());
        assertFalse(map2.get(4));
        assertTrue(map2.get(5));
        assertFalse(map2.get(6));
        Map<Integer,Boolean> map3 = mapOfIntsToMapsOfIntsToBooleans.get(789);
        assertEquals(3, map3.size());
        assertTrue(map3.get(7));
        assertFalse(map3.get(8));
        assertFalse(map3.get(9));

        Pair<String,String> pairOfStrings = _variableCache.getVariable("pairOfStrings");
        assertNotNull(pairOfStrings);
        assertEquals("pairString1", pairOfStrings.getItem1());
        assertEquals("pairString2", pairOfStrings.getItem2());

        Pair<String,Collection<Integer>> pairOfStringAndCollectionOfInts =
                _variableCache.getVariable("pairOfStringAndCollectionOfInts");
        assertNotNull(pairOfStringAndCollectionOfInts);
        assertEquals("item1", pairOfStringAndCollectionOfInts.getItem1());
        assertEquals(3, pairOfStringAndCollectionOfInts.getItem2().size());
        assertTrue(pairOfStringAndCollectionOfInts.getItem2().contains(123));
        assertTrue(pairOfStringAndCollectionOfInts.getItem2().contains(456));
        assertTrue(pairOfStringAndCollectionOfInts.getItem2().contains(789));

        Registry<HasIdAndNameStub> registry = _variableCache.getVariable("registry");
        assertNotNull(registry);
        assertEquals(3, registry.size());
        assertEquals("name1", registry.get("id1").getName());
        assertEquals("name2", registry.get("id2").getName());
        assertEquals("name3", registry.get("id3").getName());

        assertEquals("stringVariableValue",
                _variableCache.getVariable("stringVariableName"));
    }
}
