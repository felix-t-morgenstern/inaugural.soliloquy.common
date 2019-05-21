package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentMapHandler;
import inaugural.soliloquy.common.test.stubs.MapFactoryStub;
import inaugural.soliloquy.common.test.stubs.MapStub;
import inaugural.soliloquy.common.test.stubs.PersistentValuesHandlerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.common.specs.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PersistentMapHandlerTests {
    private final IPersistentValuesHandler PERSISTENT_VALUES_HANDLER = new PersistentValuesHandlerStub();
    private final IMapFactory MAP_FACTORY = new MapFactoryStub();
    private final String KEY_1 = "key1";
    private final String KEY_2 = "key2";
    private final String KEY_3 = "key3";
    private final Integer VALUE_1 = 123;
    private final Integer VALUE_2 = 456;
    private final Integer VALUE_3 = 789;
    private final String VALUES_STRING = String.format(
            "%s\u001f%s\u001d%s\u001f%d\u001e%s\u001f%d\u001e%s\u001f%d",
            String.class.getCanonicalName(), Integer.class.getCanonicalName(),
            KEY_1, VALUE_1,
            KEY_2, VALUE_2,
            KEY_3, VALUE_3);

    private IPersistentMapHandler _persistentMapHandler;

    @BeforeEach
    void setUp() {
        _persistentMapHandler = new PersistentMapHandler(PERSISTENT_VALUES_HANDLER, MAP_FACTORY);
    }

    @Test
    void testWrite() {
        IMap<String,Integer> map = new MapStub<>("", 0);
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
        IMap<String,Integer> map = _persistentMapHandler.read(VALUES_STRING);

        assertEquals(3, map.size());
        assertEquals(VALUE_1, map.get(KEY_1));
        assertEquals(VALUE_2, map.get(KEY_2));
        assertEquals(VALUE_3, map.get(KEY_3));
        assertNotNull(map.getFirstArchetype());
        assertNotNull(map.getSecondArchetype());
    }

    // TODO: Add tests for invalid valueString fed into read()

    @Test
    void testGetInterfaceName() {
        assertThrows(UnsupportedOperationException.class,
                () -> _persistentMapHandler.getInterfaceName());
    }

    @Test
    void testGenerateArchetype() {
        final String valueType = IMap.class.getCanonicalName() + "<" +
                String.class.getCanonicalName() + "," + ICollection.class.getCanonicalName() +
                "<" + Integer.class.getCanonicalName() + ">";

        IMap<String,ICollection<Integer>> archetype =
                _persistentMapHandler.generateArchetype(valueType);

        assertNotNull(archetype);
        assertNotNull(archetype.getFirstArchetype());
        assertNotNull(archetype.getSecondArchetype());
        assertNotNull(((ICollection)archetype.getSecondArchetype()).getArchetype());
    }

    // TODO: Add tests of invalid inputs
}
