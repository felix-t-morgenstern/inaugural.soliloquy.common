package inaugural.soliloquy.common.test.unit.infrastructure;

import inaugural.soliloquy.common.infrastructure.MapImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.Map;

import java.util.HashMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class MapImplTests {
    private final String PAIR_1_KEY = "Key1";
    private final String PAIR_1_VALUE = "Value1";
    private final String PAIR_2_KEY = "Key2";
    private final String PAIR_2_VALUE = "Value2";
    private final String KEY_ARCHETYPE = "FIRST_ARCHETYPE";
    private final String VALUE_ARCHETYPE = "SECOND_ARCHETYPE";

    private MapImpl<String, String> _map;

    @BeforeEach
    void setUp() {
        _map = new MapImpl<>(KEY_ARCHETYPE, VALUE_ARCHETYPE);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new MapImpl<>(null, KEY_ARCHETYPE, VALUE_ARCHETYPE));
        assertThrows(IllegalArgumentException.class,
                () -> new MapImpl<>(new HashMap<>(), null, VALUE_ARCHETYPE));
        assertThrows(IllegalArgumentException.class,
                () -> new MapImpl<>(new HashMap<>(), KEY_ARCHETYPE, null));

        assertThrows(IllegalArgumentException.class,
                () -> new MapImpl<>(null, VALUE_ARCHETYPE));
        assertThrows(IllegalArgumentException.class,
                () -> new MapImpl<>(KEY_ARCHETYPE, null));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testExtendsHashMap() {
        assertTrue(_map instanceof HashMap);
    }

    @Test
    void testPutNullOrBlankKey() {
        assertThrows(IllegalArgumentException.class, () -> _map.put(null, "String"));
        assertThrows(IllegalArgumentException.class, () -> _map.put("", "String"));
    }

    @Test
    void testGetExceptions() {
        assertThrows(IllegalArgumentException.class, () -> _map.get(""));
        assertThrows(IllegalArgumentException.class, () -> _map.get(null));
    }

    @Test
    void testGetFirstArchetype() {
        assertEquals(KEY_ARCHETYPE, _map.getFirstArchetype());
    }

    @Test
    void testGetSecondArchetype() {
        assertEquals(VALUE_ARCHETYPE, _map.getSecondArchetype());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(Map.class.getCanonicalName() + "<java.lang.String,java.lang.String>",
                _map.getInterfaceName());
    }

    @Test
    void testMakeClone() {
        _map.put(PAIR_1_KEY, PAIR_1_VALUE);
        _map.put(PAIR_2_KEY, PAIR_2_VALUE);

        Map<String, String> clonedMap = _map.makeClone();
        assertNotNull(clonedMap);
        assertNotSame(_map, clonedMap);
        assertEquals(_map, clonedMap);
        assertNotNull(clonedMap.getFirstArchetype());
        assertNotNull(clonedMap.getSecondArchetype());
        assertSame(clonedMap.getFirstArchetype(), _map.getFirstArchetype());
        assertSame(clonedMap.getSecondArchetype(), _map.getSecondArchetype());
    }

    @Test
    void testHashCode() {
        // NB: TreeMap is chosen to contrast against the implementation, which uses HashMap
        java.util.Map<String, String> comparandMap = new TreeMap<>();

        comparandMap.put(PAIR_1_KEY, PAIR_1_VALUE);
        comparandMap.put(PAIR_2_KEY, PAIR_2_VALUE);

        _map.put(PAIR_1_KEY, PAIR_1_VALUE);
        _map.put(PAIR_2_KEY, PAIR_2_VALUE);

        assertEquals(comparandMap.hashCode(), _map.hashCode());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    void testToString() {
        assertThrows(UnsupportedOperationException.class, _map::toString);
    }
}
