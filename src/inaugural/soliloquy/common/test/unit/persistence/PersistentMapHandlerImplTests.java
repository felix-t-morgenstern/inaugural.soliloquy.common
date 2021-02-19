package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.PersistentMapHandlerImpl;
import inaugural.soliloquy.common.test.fakes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.*;
import soliloquy.specs.common.persistence.PersistentMapHandler;
import soliloquy.specs.common.persistence.PersistentValuesHandler;

import static org.junit.jupiter.api.Assertions.*;

class PersistentMapHandlerImplTests {
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

    private PersistentMapHandlerImpl _persistentMapHandler;

    @BeforeEach
    void setUp() {
        _persistentMapHandler = new PersistentMapHandlerImpl(PERSISTENT_VALUES_HANDLER, MAP_FACTORY);
    }

    @Test
    void testWrite() {
        Map<String,Integer> map = new FakeMap<>("", 0);
        map.put(KEY_1, VALUE_1);
        map.put(KEY_2, VALUE_2);
        map.put(KEY_3, VALUE_3);

        assertEquals(VALUES_STRING, _persistentMapHandler.write(map));
    }

    @Test
    void testWriteNull() {
        assertThrows(IllegalArgumentException.class,
                () -> _persistentMapHandler.write(null));
    }

    @SuppressWarnings("unchecked")
    @Test
    void testRead() {
        Map<String,Integer> map = _persistentMapHandler.read(VALUES_STRING);

        assertEquals(3, map.size());
        assertEquals(VALUE_1, map.get(KEY_1));
        assertEquals(VALUE_2, map.get(KEY_2));
        assertEquals(VALUE_3, map.get(KEY_3));
        assertNotNull(map.getFirstArchetype());
        assertNotNull(map.getSecondArchetype());
    }

    @Test
    void testGetInterfaceName() {
        assertThrows(UnsupportedOperationException.class,
                () -> _persistentMapHandler.getInterfaceName());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    void testGenerateArchetype() {
        final String valueType = Map.class.getCanonicalName() + "<" +
                String.class.getCanonicalName() + "," + List.class.getCanonicalName() +
                "<" + Integer.class.getCanonicalName() + ">>";

        Map<String,List<Integer>> archetype =
                _persistentMapHandler.generateArchetype(valueType);

        assertNotNull(archetype);
        assertNotNull(archetype.getFirstArchetype());
        assertNotNull(archetype.getSecondArchetype());
        assertNotNull(((List)archetype.getSecondArchetype()).getArchetype());
    }

    @Test
    void testGenerateArchetypeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _persistentMapHandler.generateArchetype(null));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentMapHandler.generateArchetype(""));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentMapHandler.generateArchetype(Pair.class.getCanonicalName()));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentMapHandler.generateArchetype(Pair.class.getCanonicalName() +
                        "<"));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentMapHandler.generateArchetype(Pair.class.getCanonicalName() +
                        "<>"));
    }

    @Test
    void testHashCode() {
        assertEquals(PersistentMapHandlerImpl.class.getCanonicalName().hashCode(),
                _persistentMapHandler.hashCode());
    }

    @Test
    void testEquals() {
        PersistentMapHandler equalHandler =
                new PersistentMapHandlerImpl(PERSISTENT_VALUES_HANDLER, MAP_FACTORY);
        PersistentMapHandler unequalHandler = new FakePersistentMapHandler();

        assertEquals(_persistentMapHandler, equalHandler);
        assertNotEquals(_persistentMapHandler, unequalHandler);
        assertNotEquals(null, _persistentMapHandler);
    }

    @Test
    void testToString() {
        assertEquals(PersistentMapHandlerImpl.class.getCanonicalName(),
                _persistentMapHandler.toString());
    }
}
