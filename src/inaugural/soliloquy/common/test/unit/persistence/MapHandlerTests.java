package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.MapHandler;
import inaugural.soliloquy.common.test.fakes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.*;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;

import static org.junit.jupiter.api.Assertions.*;

class MapHandlerTests {
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER =
            new FakePersistentValuesHandler();
    private final MapFactory MAP_FACTORY = new FakeMapFactory();
    private final String KEY_1 = "key1";
    private final String KEY_2 = "key2";
    private final String KEY_3 = "key3";
    private final Integer VALUE_1 = 123;
    private final Integer VALUE_2 = 456;
    private final Integer VALUE_3 = 789;
    private final String VALUES_STRING = String.format(
            "{\"keyValueType\":\"%s\",\"valueValueType\":\"%s\"," +
                    "\"keySerializedValues\":[\"%s\",\"%s\",\"%s\"]," +
                    "\"valueSerializedValues\":[\"%d\",\"%d\",\"%d\"]}",
            String.class.getCanonicalName(), Integer.class.getCanonicalName(),
            KEY_1, KEY_2, KEY_3, VALUE_1, VALUE_2, VALUE_3);

    private MapHandler _mapHandler;

    @BeforeEach
    void setUp() {
        _mapHandler = new MapHandler(PERSISTENT_VALUES_HANDLER, MAP_FACTORY);
    }

    @Test
    void testWrite() {
        Map<String,Integer> map = new FakeMap<>("", 0);
        map.put(KEY_1, VALUE_1);
        map.put(KEY_2, VALUE_2);
        map.put(KEY_3, VALUE_3);

        assertEquals(VALUES_STRING, _mapHandler.write(map));
    }

    @Test
    void testWriteNull() {
        assertThrows(IllegalArgumentException.class,
                () -> _mapHandler.write(null));
    }

    @SuppressWarnings("unchecked")
    @Test
    void testRead() {
        Map<String,Integer> map = _mapHandler.read(VALUES_STRING);

        assertEquals(3, map.size());
        assertEquals(VALUE_1, map.get(KEY_1));
        assertEquals(VALUE_2, map.get(KEY_2));
        assertEquals(VALUE_3, map.get(KEY_3));
        assertNotNull(map.getFirstArchetype());
        assertNotNull(map.getSecondArchetype());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                Map.class.getCanonicalName() + ">",
                _mapHandler.getInterfaceName());
    }

    @Test
    void testGetArchetype() {
        assertNotNull(_mapHandler.getArchetype());
        assertEquals(Map.class.getCanonicalName(), _mapHandler.getArchetype().getInterfaceName());
    }

    @Test
    void testGenerateArchetype() {
        //noinspection unchecked
        Map<String, Integer> generatedArchetype = _mapHandler.generateArchetype(
                String.class.getCanonicalName(), Integer.class.getCanonicalName());

        assertNotNull(generatedArchetype);
        assertNotNull(generatedArchetype.getFirstArchetype());
        assertNotNull(generatedArchetype.getSecondArchetype());
    }

    @Test
    void testGenerateArchetypeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _mapHandler.generateArchetype(null, Integer.class.getCanonicalName()));
        assertThrows(IllegalArgumentException.class, () ->
                _mapHandler.generateArchetype("", Integer.class.getCanonicalName()));
        assertThrows(IllegalArgumentException.class, () ->
                _mapHandler.generateArchetype(Integer.class.getCanonicalName(), null));
        assertThrows(IllegalArgumentException.class, () ->
                _mapHandler.generateArchetype(Integer.class.getCanonicalName(), ""));
    }

    @Test
    void testHashCode() {
        assertEquals((TypeHandler.class.getCanonicalName() + "<" +
                        Map.class.getCanonicalName() + ">").hashCode(),
                _mapHandler.hashCode());
    }

    @Test
    void testEquals() {
        //noinspection rawtypes
        TypeHandler<Map> equalHandler = new MapHandler(PERSISTENT_VALUES_HANDLER, MAP_FACTORY);
        //noinspection rawtypes
        TypeHandler<Map> unequalHandler = new FakeMapHandler();

        assertEquals(_mapHandler, equalHandler);
        assertNotEquals(_mapHandler, unequalHandler);
        assertNotEquals(null, _mapHandler);
    }

    @Test
    void testToString() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Map.class.getCanonicalName() + ">",
                _mapHandler.toString());
    }
}
