package inaugural.soliloquy.common.test.unit.infrastructure;

import inaugural.soliloquy.common.infrastructure.MapImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.Map;

import java.util.HashMap;
import java.util.TreeMap;

import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;

public class MapImplTests {
    private final String PAIR_1_KEY = randomString();
    private final String PAIR_1_VALUE = randomString();
    private final String PAIR_2_KEY = randomString();
    private final String PAIR_2_VALUE = randomString();
    private final String KEY_ARCHETYPE = randomString();
    private final String VALUE_ARCHETYPE = randomString();

    private Map<String, String> map;

    @BeforeEach
    public void setUp() {
        map = new MapImpl<>(KEY_ARCHETYPE, VALUE_ARCHETYPE);
    }

    @Test
    public void testConstructorWithInvalidParams() {
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
    public void testExtendsHashMap() {
        assertTrue(map instanceof HashMap);
    }

    @Test
    public void testPutNullOrBlankKey() {
        assertThrows(IllegalArgumentException.class, () -> map.put(null, randomString()));
        assertThrows(IllegalArgumentException.class, () -> map.put("", randomString()));
    }

    @Test
    public void testGetExceptions() {
        assertThrows(IllegalArgumentException.class, () -> map.get(""));
        assertThrows(IllegalArgumentException.class, () -> map.get(null));
    }

    @Test
    public void testArchetype() {
        assertEquals(KEY_ARCHETYPE, map.firstArchetype());
    }

    @Test
    public void testSecondArchetype() {
        assertEquals(VALUE_ARCHETYPE, map.secondArchetype());
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(Map.class.getCanonicalName() + "<" + String.class.getCanonicalName() + "," +
                        String.class.getCanonicalName() + ">",
                map.getInterfaceName());
    }

    @Test
    public void testMakeClone() {
        map.put(PAIR_1_KEY, PAIR_1_VALUE);
        map.put(PAIR_2_KEY, PAIR_2_VALUE);

        var clonedMap = map.makeClone();

        assertNotNull(clonedMap);
        assertNotSame(map, clonedMap);
        assertEquals(map, clonedMap);
        assertNotNull(clonedMap.firstArchetype());
        assertNotNull(clonedMap.secondArchetype());
        assertSame(clonedMap.firstArchetype(), map.firstArchetype());
        assertSame(clonedMap.secondArchetype(), map.secondArchetype());
    }

    @Test
    public void testHashCode() {
        // NB: TreeMap is chosen to contrast against the implementation, which uses HashMap
        var comparandMap = new TreeMap<>();
        comparandMap.put(PAIR_1_KEY, PAIR_1_VALUE);
        comparandMap.put(PAIR_2_KEY, PAIR_2_VALUE);
        map.put(PAIR_1_KEY, PAIR_1_VALUE);
        map.put(PAIR_2_KEY, PAIR_2_VALUE);

        assertEquals(comparandMap.hashCode(), map.hashCode());
    }

    @Test
    public void testToString() {
        assertThrows(UnsupportedOperationException.class, map::toString);
    }
}
