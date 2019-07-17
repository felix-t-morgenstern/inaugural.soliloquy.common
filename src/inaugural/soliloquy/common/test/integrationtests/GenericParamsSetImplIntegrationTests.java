package inaugural.soliloquy.common.test.integrationtests;

import com.google.inject.Guice;
import com.google.inject.Injector;
import inaugural.soliloquy.common.CommonModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.*;
import soliloquy.specs.common.infrastructure.*;
import soliloquy.specs.common.valueobjects.*;

import static org.junit.jupiter.api.Assertions.*;

class GenericParamsSetImplIntegrationTests {
    private GenericParamsSet _genericParamsSet;

    private CollectionFactory _collectionFactory;
    private CoordinateFactory _coordinateFactory;
    private EntityUuidFactory _entityUuidFactory;
    private MapFactory _mapFactory;
    private PairFactory _pairFactory;

    private PersistentValueTypeHandler<GenericParamsSet> _genericParamsSetHandler;

    private final String VALUES_STRING = "[{\"typeName\":\"soliloquy.specs.common.valueobjects.EntityUuid\",\"paramNames\":[\"entityUuid1\",\"entityUuid2\",\"entityUuid3\"],\"paramValues\":[\"5b18b261-00d9-44f4-8390-6bfa70ddbd0f\",\"f098c089-72d4-4c29-835d-4aee72f70abc\",\"dec10cbc-88ed-49f0-90f6-3a8c73922a1a\"]},{\"typeName\":\"java.lang.Boolean\",\"paramNames\":[\"Boolean1Name\",\"Boolean3Name\",\"Boolean2Name\"],\"paramValues\":[\"true\",\"true\",\"false\"]},{\"typeName\":\"soliloquy.specs.common.infrastructure.Map\\u003cjava.lang.String,soliloquy.specs.common.infrastructure.Map\\u003cjava.lang.Integer,java.lang.String\\u003e\\u003e\",\"paramNames\":[\"mapOfStringsToMapsOfIntegersToStrings\"],\"paramValues\":[\"{\\\"keyValueType\\\":\\\"java.lang.String\\\",\\\"valueValueType\\\":\\\"soliloquy.specs.common.infrastructure.Map\\\\u003cjava.lang.Integer,java.lang.String\\\\u003e\\\",\\\"keySerializedValues\\\":[\\\"map3\\\",\\\"map2\\\",\\\"map1\\\"],\\\"valueSerializedValues\\\":[\\\"{\\\\\\\"keyValueType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\",\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.String\\\\\\\",\\\\\\\"keySerializedValues\\\\\\\":[\\\\\\\"7\\\\\\\",\\\\\\\"8\\\\\\\",\\\\\\\"9\\\\\\\"],\\\\\\\"valueSerializedValues\\\\\\\":[\\\\\\\"seven\\\\\\\",\\\\\\\"eight\\\\\\\",\\\\\\\"nine\\\\\\\"]}\\\",\\\"{\\\\\\\"keyValueType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\",\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.String\\\\\\\",\\\\\\\"keySerializedValues\\\\\\\":[\\\\\\\"4\\\\\\\",\\\\\\\"5\\\\\\\",\\\\\\\"6\\\\\\\"],\\\\\\\"valueSerializedValues\\\\\\\":[\\\\\\\"four\\\\\\\",\\\\\\\"five\\\\\\\",\\\\\\\"six\\\\\\\"]}\\\",\\\"{\\\\\\\"keyValueType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\",\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.String\\\\\\\",\\\\\\\"keySerializedValues\\\\\\\":[\\\\\\\"1\\\\\\\",\\\\\\\"2\\\\\\\",\\\\\\\"3\\\\\\\"],\\\\\\\"valueSerializedValues\\\\\\\":[\\\\\\\"one\\\\\\\",\\\\\\\"two\\\\\\\",\\\\\\\"three\\\\\\\"]}\\\"]}\"]},{\"typeName\":\"java.lang.String\",\"paramNames\":[\"String1Name\",\"String2Name\",\"String3Name\"],\"paramValues\":[\"String1Value\",\"String2Value\",\"String3Value\"]},{\"typeName\":\"soliloquy.specs.common.infrastructure.Collection\\u003cjava.lang.Integer\\u003e\",\"paramNames\":[\"integerCollection3\",\"integerCollection2\",\"integerCollection1\"],\"paramValues\":[\"{\\\"typeName\\\":\\\"java.lang.Integer\\\",\\\"serializedValues\\\":[\\\"7\\\",\\\"8\\\",\\\"9\\\"]}\",\"{\\\"typeName\\\":\\\"java.lang.Integer\\\",\\\"serializedValues\\\":[\\\"4\\\",\\\"5\\\",\\\"6\\\"]}\",\"{\\\"typeName\\\":\\\"java.lang.Integer\\\",\\\"serializedValues\\\":[\\\"1\\\",\\\"2\\\",\\\"3\\\"]}\"]},{\"typeName\":\"java.lang.Integer\",\"paramNames\":[\"Integer1Name\",\"Integer3Name\",\"Integer2Name\"],\"paramValues\":[\"123\",\"789\",\"456\"]},{\"typeName\":\"soliloquy.specs.common.valueobjects.Coordinate\",\"paramNames\":[\"coordinate3\",\"coordinate2\",\"coordinate1\"],\"paramValues\":[\"{\\\"x\\\":5,\\\"y\\\":6}\",\"{\\\"x\\\":3,\\\"y\\\":4}\",\"{\\\"x\\\":1,\\\"y\\\":2}\"]},{\"typeName\":\"soliloquy.specs.common.infrastructure.Pair\\u003cjava.lang.String,java.lang.String\\u003e\",\"paramNames\":[\"pairOfStrings1\",\"pairOfStrings2\",\"pairOfStrings3\"],\"paramValues\":[\"{\\\"valueType1\\\":\\\"java.lang.String\\\",\\\"serializedValue1\\\":\\\"pair1string1\\\",\\\"valueType2\\\":\\\"java.lang.String\\\",\\\"serializedValue2\\\":\\\"pair1string2\\\"}\",\"{\\\"valueType1\\\":\\\"java.lang.String\\\",\\\"serializedValue1\\\":\\\"pair2string1\\\",\\\"valueType2\\\":\\\"java.lang.String\\\",\\\"serializedValue2\\\":\\\"pair2string2\\\"}\",\"{\\\"valueType1\\\":\\\"java.lang.String\\\",\\\"serializedValue1\\\":\\\"pair3string1\\\",\\\"valueType2\\\":\\\"java.lang.String\\\",\\\"serializedValue2\\\":\\\"pair3string2\\\"}\"]}]";

    @BeforeEach
    void setUp() {
        Injector commonInjector = Guice.createInjector(new CommonModule());

        _genericParamsSet = commonInjector.getInstance(GenericParamsSetFactory.class).make();

        _collectionFactory = commonInjector.getInstance(CollectionFactory.class);
        _coordinateFactory = commonInjector.getInstance(CoordinateFactory.class);
        _entityUuidFactory = commonInjector.getInstance(EntityUuidFactory.class);
        _mapFactory = commonInjector.getInstance(MapFactory.class);
        _pairFactory = commonInjector.getInstance(PairFactory.class);

        _genericParamsSetHandler = commonInjector.getInstance(PersistentValuesHandler.class)
                .getPersistentValueTypeHandler(GenericParamsSet.class.getCanonicalName());
    }

    @Test
    void testWrite() {
        _genericParamsSet.addParam("String1Name", "String1Value");
        _genericParamsSet.addParam("String2Name", "String2Value");
        _genericParamsSet.addParam("String3Name", "String3Value");

        _genericParamsSet.addParam("Integer1Name", 123);
        _genericParamsSet.addParam("Integer2Name", 456);
        _genericParamsSet.addParam("Integer3Name", 789);

        _genericParamsSet.addParam("Boolean1Name", true);
        _genericParamsSet.addParam("Boolean2Name", false);
        _genericParamsSet.addParam("Boolean3Name", true);

        Collection<Integer> integerCollection1 = _collectionFactory.make(0);
        integerCollection1.add(1);
        integerCollection1.add(2);
        integerCollection1.add(3);
        Collection<Integer> integerCollection2 = _collectionFactory.make(0);
        integerCollection2.add(4);
        integerCollection2.add(5);
        integerCollection2.add(6);
        Collection<Integer> integerCollection3 = _collectionFactory.make(0);
        integerCollection3.add(7);
        integerCollection3.add(8);
        integerCollection3.add(9);
        _genericParamsSet.addParam("integerCollection1", integerCollection1);
        _genericParamsSet.addParam("integerCollection2", integerCollection2);
        _genericParamsSet.addParam("integerCollection3", integerCollection3);

        _genericParamsSet.addParam("coordinate1", _coordinateFactory.make(1,2));
        _genericParamsSet.addParam("coordinate2", _coordinateFactory.make(3,4));
        _genericParamsSet.addParam("coordinate3", _coordinateFactory.make(5,6));

        _genericParamsSet.addParam("entityUuid1",
                _entityUuidFactory.createFromString("5b18b261-00d9-44f4-8390-6bfa70ddbd0f"));
        _genericParamsSet.addParam("entityUuid2",
                _entityUuidFactory.createFromString("f098c089-72d4-4c29-835d-4aee72f70abc"));
        _genericParamsSet.addParam("entityUuid3",
                _entityUuidFactory.createFromString("dec10cbc-88ed-49f0-90f6-3a8c73922a1a"));

        Map<Integer,String> mapOfIntegersToStrings1 = _mapFactory.make(0, "");
        mapOfIntegersToStrings1.put(1, "one");
        mapOfIntegersToStrings1.put(2, "two");
        mapOfIntegersToStrings1.put(3, "three");
        Map<Integer,String> mapOfIntegersToStrings2 = _mapFactory.make(0, "");
        mapOfIntegersToStrings2.put(4, "four");
        mapOfIntegersToStrings2.put(5, "five");
        mapOfIntegersToStrings2.put(6, "six");
        Map<Integer,String> mapOfIntegersToStrings3 = _mapFactory.make(0, "");
        mapOfIntegersToStrings3.put(7, "seven");
        mapOfIntegersToStrings3.put(8, "eight");
        mapOfIntegersToStrings3.put(9, "nine");
        Map<String,Map<Integer,String>> mapOfStringsToMapsOfIntegersToStrings =
                _mapFactory.make("", _mapFactory.make(0, ""));
        mapOfStringsToMapsOfIntegersToStrings.put("map1", mapOfIntegersToStrings1);
        mapOfStringsToMapsOfIntegersToStrings.put("map2", mapOfIntegersToStrings2);
        mapOfStringsToMapsOfIntegersToStrings.put("map3", mapOfIntegersToStrings3);
        _genericParamsSet.addParam("mapOfStringsToMapsOfIntegersToStrings",
                mapOfStringsToMapsOfIntegersToStrings);

        _genericParamsSet.addParam("pairOfStrings1",
                _pairFactory.make("pair1string1", "pair1string2"));
        _genericParamsSet.addParam("pairOfStrings2",
                _pairFactory.make("pair2string1", "pair2string2"));
        _genericParamsSet.addParam("pairOfStrings3",
                _pairFactory.make("pair3string1", "pair3string2"));

        assertEquals(VALUES_STRING, _genericParamsSetHandler.write(_genericParamsSet));
    }

    @Test
    void testRead() {
        _genericParamsSet = _genericParamsSetHandler.read(VALUES_STRING);

        assertNotNull(_genericParamsSet);
        assertEquals(8, _genericParamsSet.paramTypes().size());

        Map<String,String> stringParams =
                _genericParamsSet.getParamsSet(String.class.getCanonicalName());
        assertNotNull(stringParams);
        assertEquals(3, stringParams.size());
        assertEquals("String1Value", stringParams.get("String1Name"));
        assertEquals("String2Value", stringParams.get("String2Name"));
        assertEquals("String3Value", stringParams.get("String3Name"));

        Map<String,Integer> integerParams =
                _genericParamsSet.getParamsSet(Integer.class.getCanonicalName());
        assertNotNull(integerParams);
        assertEquals(3, integerParams.size());
        assertEquals((Integer) 123, integerParams.get("Integer1Name"));
        assertEquals((Integer) 456, integerParams.get("Integer2Name"));
        assertEquals((Integer) 789, integerParams.get("Integer3Name"));

        Map<String,Boolean> booleanParams =
                _genericParamsSet.getParamsSet(Boolean.class.getCanonicalName());
        assertNotNull(booleanParams);
        assertEquals(3, booleanParams.size());
        assertTrue(booleanParams.get("Boolean1Name"));
        assertFalse(booleanParams.get("Boolean2Name"));
        assertTrue(booleanParams.get("Boolean3Name"));

        Map<String,Collection<Integer>> collectionOfIntegersParams =
                _genericParamsSet.getParamsSet(_collectionFactory.make(0).getInterfaceName());
        assertNotNull(collectionOfIntegersParams);
        Collection<Integer> integerCollection1 =
                collectionOfIntegersParams.get("integerCollection1");
        assertNotNull(integerCollection1);
        assertEquals(3, integerCollection1.size());
        assertTrue(integerCollection1.contains(1));
        assertTrue(integerCollection1.contains(2));
        assertTrue(integerCollection1.contains(3));
        Collection<Integer> integerCollection2 =
                collectionOfIntegersParams.get("integerCollection2");
        assertNotNull(integerCollection2);
        assertEquals(3, integerCollection2.size());
        assertTrue(integerCollection2.contains(4));
        assertTrue(integerCollection2.contains(5));
        assertTrue(integerCollection2.contains(6));
        Collection<Integer> integerCollection3 =
                collectionOfIntegersParams.get("integerCollection3");
        assertNotNull(integerCollection3);
        assertEquals(3, integerCollection3.size());
        assertTrue(integerCollection3.contains(7));
        assertTrue(integerCollection3.contains(8));
        assertTrue(integerCollection3.contains(9));

        Map<String, Coordinate> coordinateParams =
                _genericParamsSet.getParamsSet(Coordinate.class.getCanonicalName());
        Coordinate coordinate1 = coordinateParams.get("coordinate1");
        assertNotNull(coordinate1);
        assertEquals(1, coordinate1.getX());
        assertEquals(2, coordinate1.getY());
        Coordinate coordinate2 = coordinateParams.get("coordinate2");
        assertNotNull(coordinate2);
        assertEquals(3, coordinate2.getX());
        assertEquals(4, coordinate2.getY());
        Coordinate coordinate3 = coordinateParams.get("coordinate3");
        assertNotNull(coordinate3);
        assertEquals(5, coordinate3.getX());
        assertEquals(6, coordinate3.getY());

        Map<String, EntityUuid> entityUuidParams =
                _genericParamsSet.getParamsSet(EntityUuid.class.getCanonicalName());
        EntityUuid entityUuid1 = entityUuidParams.get("entityUuid1");
        assertNotNull(entityUuid1);
        assertEquals("5b18b261-00d9-44f4-8390-6bfa70ddbd0f", entityUuid1.toString());
        EntityUuid entityUuid2 = entityUuidParams.get("entityUuid2");
        assertNotNull(entityUuid2);
        assertEquals("f098c089-72d4-4c29-835d-4aee72f70abc", entityUuid2.toString());
        EntityUuid entityUuid3 = entityUuidParams.get("entityUuid3");
        assertNotNull(entityUuid3);
        assertEquals("dec10cbc-88ed-49f0-90f6-3a8c73922a1a", entityUuid3.toString());

        Map<String,Map<String,Map<Integer,String>>> mapOfStringsToMapsOfIntegersToStringsParams =
                _genericParamsSet.getParamsSet(_mapFactory.make("", _mapFactory.make(0, ""))
                        .getInterfaceName());
        assertNotNull(mapOfStringsToMapsOfIntegersToStringsParams);
        assertEquals(1, mapOfStringsToMapsOfIntegersToStringsParams.size());
        Map<String,Map<Integer,String>> mapOfStringsToMapsOfIntegersToStrings =
                mapOfStringsToMapsOfIntegersToStringsParams
                        .get("mapOfStringsToMapsOfIntegersToStrings");
        assertNotNull(mapOfStringsToMapsOfIntegersToStrings);
        assertEquals(3, mapOfStringsToMapsOfIntegersToStrings.size());
        Map<Integer,String> mapOfIntegersToStrings1 =
                mapOfStringsToMapsOfIntegersToStrings.get("map1");
        assertNotNull(mapOfIntegersToStrings1);
        assertEquals(3, mapOfIntegersToStrings1.size());
        assertEquals("one", mapOfIntegersToStrings1.get(1));
        assertEquals("two", mapOfIntegersToStrings1.get(2));
        assertEquals("three", mapOfIntegersToStrings1.get(3));
        Map<Integer,String> mapOfIntegersToStrings2 =
                mapOfStringsToMapsOfIntegersToStrings.get("map2");
        assertNotNull(mapOfIntegersToStrings2);
        assertEquals(3, mapOfIntegersToStrings2.size());
        assertEquals("four", mapOfIntegersToStrings2.get(4));
        assertEquals("five", mapOfIntegersToStrings2.get(5));
        assertEquals("six", mapOfIntegersToStrings2.get(6));
        Map<Integer,String> mapOfIntegersToStrings3 =
                mapOfStringsToMapsOfIntegersToStrings.get("map3");
        assertNotNull(mapOfIntegersToStrings3);
        assertEquals(3, mapOfIntegersToStrings3.size());
        assertEquals("seven", mapOfIntegersToStrings3.get(7));
        assertEquals("eight", mapOfIntegersToStrings3.get(8));
        assertEquals("nine", mapOfIntegersToStrings3.get(9));

        Map<String,Pair<String,String>> pairOfStringsParams =
                _genericParamsSet.getParamsSet(_pairFactory.make("", "").getInterfaceName());
        assertNotNull(pairOfStringsParams);
        assertEquals(3, pairOfStringsParams.size());
        Pair<String,String> pairOfStrings1 = pairOfStringsParams.get("pairOfStrings1");
        assertNotNull(pairOfStrings1);
        assertEquals("pair1string1", pairOfStrings1.getItem1());
        assertEquals("pair1string2", pairOfStrings1.getItem2());
        Pair<String,String> pairOfStrings2 = pairOfStringsParams.get("pairOfStrings2");
        assertNotNull(pairOfStrings2);
        assertEquals("pair2string1", pairOfStrings2.getItem1());
        assertEquals("pair2string2", pairOfStrings2.getItem2());
        Pair<String,String> pairOfStrings3 = pairOfStringsParams.get("pairOfStrings3");
        assertNotNull(pairOfStrings3);
        assertEquals("pair3string1", pairOfStrings3.getItem1());
        assertEquals("pair3string2", pairOfStrings3.getItem2());
    }
}
