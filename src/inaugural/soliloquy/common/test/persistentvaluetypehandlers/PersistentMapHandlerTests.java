package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentMapHandler;
import inaugural.soliloquy.common.test.stubs.MapFactoryStub;
import inaugural.soliloquy.common.test.stubs.MapStub;
import inaugural.soliloquy.common.test.stubs.PersistentValuesHandlerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.*;

import static org.junit.jupiter.api.Assertions.*;

class PersistentMapHandlerTests {
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER =
            new PersistentValuesHandlerStub();
    private final MapFactory MAP_FACTORY = new MapFactoryStub();
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

    private PersistentMapHandler _persistentMapHandler;

    @BeforeEach
    void setUp() {
        _persistentMapHandler = new PersistentMapHandler(PERSISTENT_VALUES_HANDLER, MAP_FACTORY);
    }

    @Test
    void testWrite() {
        Map<String,Integer> map = new MapStub<>("", 0);
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

    @SuppressWarnings("unchecked")
    @Test
    void testGenerateArchetype() {
        final String valueType = Map.class.getCanonicalName() + "<" +
                String.class.getCanonicalName() + "," + Collection.class.getCanonicalName() +
                "<" + Integer.class.getCanonicalName() + ">>";

        Map<String,Collection<Integer>> archetype =
                _persistentMapHandler.generateArchetype(valueType);

        assertNotNull(archetype);
        assertNotNull(archetype.getFirstArchetype());
        assertNotNull(archetype.getSecondArchetype());
        assertNotNull(((Collection)archetype.getSecondArchetype()).getArchetype());
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
}
