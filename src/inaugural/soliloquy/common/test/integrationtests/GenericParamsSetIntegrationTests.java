package inaugural.soliloquy.common.test.integrationtests;

import com.google.inject.Guice;
import com.google.inject.Injector;
import inaugural.soliloquy.common.CommonModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.IPersistentValueTypeHandler;
import soliloquy.specs.common.entities.IPersistentValuesHandler;
import soliloquy.specs.common.factories.*;
import soliloquy.specs.common.valueobjects.*;

import static org.junit.jupiter.api.Assertions.*;

class GenericParamsSetIntegrationTests {
    private IGenericParamsSet _genericParamsSet;

    private ICollectionFactory _collectionFactory;
    private ICoordinateFactory _coordinateFactory;
    private IEntityUuidFactory _entityUuidFactory;
    private IMapFactory _mapFactory;
    private IPairFactory _pairFactory;

    private IPersistentValueTypeHandler<IGenericParamsSet> _genericParamsSetHandler;

    private final String VALUES_STRING = "[{\"typeName\":\"soliloquy.specs.common.valueobjects.IEntityUuid\",\"paramNames\":[\"entityUuid1\",\"entityUuid2\",\"entityUuid3\"],\"paramValues\":[\"5b18b261-00d9-44f4-8390-6bfa70ddbd0f\",\"f098c089-72d4-4c29-835d-4aee72f70abc\",\"dec10cbc-88ed-49f0-90f6-3a8c73922a1a\"]},{\"typeName\":\"soliloquy.specs.common.valueobjects.IMap\\u003cjava.lang.String,soliloquy.specs.common.valueobjects.IMap\\u003cjava.lang.Integer,java.lang.String\\u003e\\u003e\",\"paramNames\":[\"mapOfStringsToMapsOfIntegersToStrings\"],\"paramValues\":[\"{\\\"keyValueType\\\":\\\"java.lang.String\\\",\\\"valueValueType\\\":\\\"soliloquy.specs.common.valueobjects.IMap\\\\u003cjava.lang.Integer,java.lang.String\\\\u003e\\\",\\\"keySerializedValues\\\":[\\\"map3\\\",\\\"map2\\\",\\\"map1\\\"],\\\"valueSerializedValues\\\":[\\\"{\\\\\\\"keyValueType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\",\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.String\\\\\\\",\\\\\\\"keySerializedValues\\\\\\\":[\\\\\\\"7\\\\\\\",\\\\\\\"8\\\\\\\",\\\\\\\"9\\\\\\\"],\\\\\\\"valueSerializedValues\\\\\\\":[\\\\\\\"seven\\\\\\\",\\\\\\\"eight\\\\\\\",\\\\\\\"nine\\\\\\\"]}\\\",\\\"{\\\\\\\"keyValueType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\",\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.String\\\\\\\",\\\\\\\"keySerializedValues\\\\\\\":[\\\\\\\"4\\\\\\\",\\\\\\\"5\\\\\\\",\\\\\\\"6\\\\\\\"],\\\\\\\"valueSerializedValues\\\\\\\":[\\\\\\\"four\\\\\\\",\\\\\\\"five\\\\\\\",\\\\\\\"six\\\\\\\"]}\\\",\\\"{\\\\\\\"keyValueType\\\\\\\":\\\\\\\"java.lang.Integer\\\\\\\",\\\\\\\"valueValueType\\\\\\\":\\\\\\\"java.lang.String\\\\\\\",\\\\\\\"keySerializedValues\\\\\\\":[\\\\\\\"1\\\\\\\",\\\\\\\"2\\\\\\\",\\\\\\\"3\\\\\\\"],\\\\\\\"valueSerializedValues\\\\\\\":[\\\\\\\"one\\\\\\\",\\\\\\\"two\\\\\\\",\\\\\\\"three\\\\\\\"]}\\\"]}\"]},{\"typeName\":\"java.lang.Boolean\",\"paramNames\":[\"Boolean1Name\",\"Boolean3Name\",\"Boolean2Name\"],\"paramValues\":[\"true\",\"true\",\"false\"]},{\"typeName\":\"soliloquy.specs.common.valueobjects.ICoordinate\",\"paramNames\":[\"coordinate3\",\"coordinate2\",\"coordinate1\"],\"paramValues\":[\"{\\\"x\\\":5,\\\"y\\\":6}\",\"{\\\"x\\\":3,\\\"y\\\":4}\",\"{\\\"x\\\":1,\\\"y\\\":2}\"]},{\"typeName\":\"soliloquy.specs.common.valueobjects.IPair\\u003cjava.lang.String,java.lang.String\\u003e\",\"paramNames\":[\"pairOfStrings1\",\"pairOfStrings2\",\"pairOfStrings3\"],\"paramValues\":[\"{\\\"valueType1\\\":\\\"java.lang.String\\\",\\\"serializedValue1\\\":\\\"pair1string1\\\",\\\"valueType2\\\":\\\"java.lang.String\\\",\\\"serializedValue2\\\":\\\"pair1string2\\\"}\",\"{\\\"valueType1\\\":\\\"java.lang.String\\\",\\\"serializedValue1\\\":\\\"pair2string1\\\",\\\"valueType2\\\":\\\"java.lang.String\\\",\\\"serializedValue2\\\":\\\"pair2string2\\\"}\",\"{\\\"valueType1\\\":\\\"java.lang.String\\\",\\\"serializedValue1\\\":\\\"pair3string1\\\",\\\"valueType2\\\":\\\"java.lang.String\\\",\\\"serializedValue2\\\":\\\"pair3string2\\\"}\"]},{\"typeName\":\"java.lang.String\",\"paramNames\":[\"String1Name\",\"String2Name\",\"String3Name\"],\"paramValues\":[\"String1Value\",\"String2Value\",\"String3Value\"]},{\"typeName\":\"soliloquy.specs.common.valueobjects.ICollection\\u003cjava.lang.Integer\\u003e\",\"paramNames\":[\"integerCollection3\",\"integerCollection2\",\"integerCollection1\"],\"paramValues\":[\"{\\\"typeName\\\":\\\"java.lang.Integer\\\",\\\"serializedValues\\\":[\\\"7\\\",\\\"8\\\",\\\"9\\\"]}\",\"{\\\"typeName\\\":\\\"java.lang.Integer\\\",\\\"serializedValues\\\":[\\\"4\\\",\\\"5\\\",\\\"6\\\"]}\",\"{\\\"typeName\\\":\\\"java.lang.Integer\\\",\\\"serializedValues\\\":[\\\"1\\\",\\\"2\\\",\\\"3\\\"]}\"]},{\"typeName\":\"java.lang.Integer\",\"paramNames\":[\"Integer1Name\",\"Integer3Name\",\"Integer2Name\"],\"paramValues\":[\"123\",\"789\",\"456\"]}]";

    @BeforeEach
    void setUp() {
        Injector commonInjector = Guice.createInjector(new CommonModule());

        _genericParamsSet = commonInjector.getInstance(IGenericParamsSetFactory.class).make();

        _collectionFactory = commonInjector.getInstance(ICollectionFactory.class);
        _coordinateFactory = commonInjector.getInstance(ICoordinateFactory.class);
        _entityUuidFactory = commonInjector.getInstance(IEntityUuidFactory.class);
        _mapFactory = commonInjector.getInstance(IMapFactory.class);
        _pairFactory = commonInjector.getInstance(IPairFactory.class);

        _genericParamsSetHandler = commonInjector.getInstance(IPersistentValuesHandler.class)
                .getPersistentValueTypeHandler(IGenericParamsSet.class.getCanonicalName());
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

        ICollection<Integer> integerCollection1 = _collectionFactory.make(0);
        integerCollection1.add(1);
        integerCollection1.add(2);
        integerCollection1.add(3);
        ICollection<Integer> integerCollection2 = _collectionFactory.make(0);
        integerCollection2.add(4);
        integerCollection2.add(5);
        integerCollection2.add(6);
        ICollection<Integer> integerCollection3 = _collectionFactory.make(0);
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

        IMap<Integer,String> mapOfIntegersToStrings1 = _mapFactory.make(0, "");
        mapOfIntegersToStrings1.put(1, "one");
        mapOfIntegersToStrings1.put(2, "two");
        mapOfIntegersToStrings1.put(3, "three");
        IMap<Integer,String> mapOfIntegersToStrings2 = _mapFactory.make(0, "");
        mapOfIntegersToStrings2.put(4, "four");
        mapOfIntegersToStrings2.put(5, "five");
        mapOfIntegersToStrings2.put(6, "six");
        IMap<Integer,String> mapOfIntegersToStrings3 = _mapFactory.make(0, "");
        mapOfIntegersToStrings3.put(7, "seven");
        mapOfIntegersToStrings3.put(8, "eight");
        mapOfIntegersToStrings3.put(9, "nine");
        IMap<String,IMap<Integer,String>> mapOfStringsToMapsOfIntegersToStrings =
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

        IMap<String,String> stringParams =
                _genericParamsSet.getParamsSet(String.class.getCanonicalName());
        assertNotNull(stringParams);
        assertEquals(3, stringParams.size());
        assertEquals("String1Value", stringParams.get("String1Name"));
        assertEquals("String2Value", stringParams.get("String2Name"));
        assertEquals("String3Value", stringParams.get("String3Name"));

        IMap<String,Integer> integerParams =
                _genericParamsSet.getParamsSet(Integer.class.getCanonicalName());
        assertNotNull(integerParams);
        assertEquals(3, integerParams.size());
        assertEquals((Integer) 123, integerParams.get("Integer1Name"));
        assertEquals((Integer) 456, integerParams.get("Integer2Name"));
        assertEquals((Integer) 789, integerParams.get("Integer3Name"));

        IMap<String,Boolean> booleanParams =
                _genericParamsSet.getParamsSet(Boolean.class.getCanonicalName());
        assertNotNull(booleanParams);
        assertEquals(3, booleanParams.size());
        assertTrue(booleanParams.get("Boolean1Name"));
        assertFalse(booleanParams.get("Boolean2Name"));
        assertTrue(booleanParams.get("Boolean3Name"));

        IMap<String,ICollection<Integer>> collectionOfIntegersParams =
                _genericParamsSet.getParamsSet(_collectionFactory.make(0).getInterfaceName());
        assertNotNull(collectionOfIntegersParams);
        ICollection<Integer> integerCollection1 =
                collectionOfIntegersParams.get("integerCollection1");
        assertNotNull(integerCollection1);
        assertEquals(3, integerCollection1.size());
        assertTrue(integerCollection1.contains(1));
        assertTrue(integerCollection1.contains(2));
        assertTrue(integerCollection1.contains(3));
        ICollection<Integer> integerCollection2 =
                collectionOfIntegersParams.get("integerCollection2");
        assertNotNull(integerCollection2);
        assertEquals(3, integerCollection2.size());
        assertTrue(integerCollection2.contains(4));
        assertTrue(integerCollection2.contains(5));
        assertTrue(integerCollection2.contains(6));
        ICollection<Integer> integerCollection3 =
                collectionOfIntegersParams.get("integerCollection3");
        assertNotNull(integerCollection3);
        assertEquals(3, integerCollection3.size());
        assertTrue(integerCollection3.contains(7));
        assertTrue(integerCollection3.contains(8));
        assertTrue(integerCollection3.contains(9));

        IMap<String, ICoordinate> coordinateParams =
                _genericParamsSet.getParamsSet(ICoordinate.class.getCanonicalName());
        ICoordinate coordinate1 = coordinateParams.get("coordinate1");
        assertNotNull(coordinate1);
        assertEquals(1, coordinate1.getX());
        assertEquals(2, coordinate1.getY());
        ICoordinate coordinate2 = coordinateParams.get("coordinate2");
        assertNotNull(coordinate2);
        assertEquals(3, coordinate2.getX());
        assertEquals(4, coordinate2.getY());
        ICoordinate coordinate3 = coordinateParams.get("coordinate3");
        assertNotNull(coordinate3);
        assertEquals(5, coordinate3.getX());
        assertEquals(6, coordinate3.getY());

        IMap<String, IEntityUuid> entityUuidParams =
                _genericParamsSet.getParamsSet(IEntityUuid.class.getCanonicalName());
        IEntityUuid entityUuid1 = entityUuidParams.get("entityUuid1");
        assertNotNull(entityUuid1);
        assertEquals("5b18b261-00d9-44f4-8390-6bfa70ddbd0f", entityUuid1.toString());
        IEntityUuid entityUuid2 = entityUuidParams.get("entityUuid2");
        assertNotNull(entityUuid2);
        assertEquals("f098c089-72d4-4c29-835d-4aee72f70abc", entityUuid2.toString());
        IEntityUuid entityUuid3 = entityUuidParams.get("entityUuid3");
        assertNotNull(entityUuid3);
        assertEquals("dec10cbc-88ed-49f0-90f6-3a8c73922a1a", entityUuid3.toString());

        IMap<String,IMap<String,IMap<Integer,String>>> mapOfStringsToMapsOfIntegersToStringsParams =
                _genericParamsSet.getParamsSet(_mapFactory.make("", _mapFactory.make(0, ""))
                        .getInterfaceName());
        assertNotNull(mapOfStringsToMapsOfIntegersToStringsParams);
        assertEquals(1, mapOfStringsToMapsOfIntegersToStringsParams.size());
        IMap<String,IMap<Integer,String>> mapOfStringsToMapsOfIntegersToStrings =
                mapOfStringsToMapsOfIntegersToStringsParams
                        .get("mapOfStringsToMapsOfIntegersToStrings");
        assertNotNull(mapOfStringsToMapsOfIntegersToStrings);
        assertEquals(3, mapOfStringsToMapsOfIntegersToStrings.size());
        IMap<Integer,String> mapOfIntegersToStrings1 =
                mapOfStringsToMapsOfIntegersToStrings.get("map1");
        assertNotNull(mapOfIntegersToStrings1);
        assertEquals(3, mapOfIntegersToStrings1.size());
        assertEquals("one", mapOfIntegersToStrings1.get(1));
        assertEquals("two", mapOfIntegersToStrings1.get(2));
        assertEquals("three", mapOfIntegersToStrings1.get(3));
        IMap<Integer,String> mapOfIntegersToStrings2 =
                mapOfStringsToMapsOfIntegersToStrings.get("map2");
        assertNotNull(mapOfIntegersToStrings2);
        assertEquals(3, mapOfIntegersToStrings2.size());
        assertEquals("four", mapOfIntegersToStrings2.get(4));
        assertEquals("five", mapOfIntegersToStrings2.get(5));
        assertEquals("six", mapOfIntegersToStrings2.get(6));
        IMap<Integer,String> mapOfIntegersToStrings3 =
                mapOfStringsToMapsOfIntegersToStrings.get("map3");
        assertNotNull(mapOfIntegersToStrings3);
        assertEquals(3, mapOfIntegersToStrings3.size());
        assertEquals("seven", mapOfIntegersToStrings3.get(7));
        assertEquals("eight", mapOfIntegersToStrings3.get(8));
        assertEquals("nine", mapOfIntegersToStrings3.get(9));

        IMap<String,IPair<String,String>> pairOfStringsParams =
                _genericParamsSet.getParamsSet(_pairFactory.make("", "").getInterfaceName());
        assertNotNull(pairOfStringsParams);
        assertEquals(3, pairOfStringsParams.size());
        IPair<String,String> pairOfStrings1 = pairOfStringsParams.get("pairOfStrings1");
        assertNotNull(pairOfStrings1);
        assertEquals("pair1string1", pairOfStrings1.getItem1());
        assertEquals("pair1string2", pairOfStrings1.getItem2());
        IPair<String,String> pairOfStrings2 = pairOfStringsParams.get("pairOfStrings2");
        assertNotNull(pairOfStrings2);
        assertEquals("pair2string1", pairOfStrings2.getItem1());
        assertEquals("pair2string2", pairOfStrings2.getItem2());
        IPair<String,String> pairOfStrings3 = pairOfStringsParams.get("pairOfStrings3");
        assertNotNull(pairOfStrings3);
        assertEquals("pair3string1", pairOfStrings3.getItem1());
        assertEquals("pair3string2", pairOfStrings3.getItem2());
    }
}
