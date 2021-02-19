package inaugural.soliloquy.common.test.unit.infrastructure;

import inaugural.soliloquy.common.infrastructure.MapImpl;
import inaugural.soliloquy.common.test.fakes.FakeList;
import inaugural.soliloquy.common.test.fakes.FakeListFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Map;

import java.util.HashMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class MapImplTests {
    private final FakeListFactory LIST_FACTORY = new FakeListFactory();
    private final String PAIR_1_KEY = "Key1";
    private final String PAIR_1_VALUE = "Value1";
    private final String PAIR_2_KEY = "Key2";
    private final String PAIR_2_VALUE = "Value2";
    private final String KEY_ARCHETYPE = "FIRST_ARCHETYPE";
    private final String VALUE_ARCHETYPE = "SECOND_ARCHETYPE";

    private MapImpl<String,String> _map;

    @BeforeEach
    void setUp() {
        LIST_FACTORY.NextListToReturn = null;
        LIST_FACTORY.MakeCollectionInput = null;
        LIST_FACTORY.MakeArchetypeInput = null;
        _map = new MapImpl<>(LIST_FACTORY, KEY_ARCHETYPE, VALUE_ARCHETYPE);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new MapImpl<>(null, new HashMap<>(), KEY_ARCHETYPE, VALUE_ARCHETYPE));
        assertThrows(IllegalArgumentException.class,
                () -> new MapImpl<>(LIST_FACTORY, null, KEY_ARCHETYPE, VALUE_ARCHETYPE));
        assertThrows(IllegalArgumentException.class,
                () -> new MapImpl<>(LIST_FACTORY, new HashMap<>(), null, VALUE_ARCHETYPE));
        assertThrows(IllegalArgumentException.class,
                () -> new MapImpl<>(LIST_FACTORY, new HashMap<>(), KEY_ARCHETYPE, null));

        assertThrows(IllegalArgumentException.class,
                () -> new MapImpl<>(null, KEY_ARCHETYPE, VALUE_ARCHETYPE));
        assertThrows(IllegalArgumentException.class,
                () -> new MapImpl<>(LIST_FACTORY, null, VALUE_ARCHETYPE));
        assertThrows(IllegalArgumentException.class,
                () -> new MapImpl<>(LIST_FACTORY, KEY_ARCHETYPE, null));
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

        Map<String,String> clonedMap = _map.makeClone();
        assertNotNull(clonedMap);
        assertNotSame(_map, clonedMap);
        assertEquals(_map, clonedMap);
        assertNotNull(clonedMap.getFirstArchetype());
        assertNotNull(clonedMap.getSecondArchetype());
        assertSame(clonedMap.getFirstArchetype(), _map.getFirstArchetype());
        assertSame(clonedMap.getSecondArchetype(), _map.getSecondArchetype());
    }

    @Test
    void getValuesList() {
        _map.put(PAIR_1_KEY, PAIR_1_VALUE);
        _map.put(PAIR_2_KEY, PAIR_2_VALUE);
        LIST_FACTORY.NextListToReturn = new FakeList<String>();

        List<String> valuesList = _map.getValuesList();

        assertSame(LIST_FACTORY.NextListToReturn, valuesList);
        assertEquals(_map.values(), LIST_FACTORY.MakeCollectionInput);
        assertSame(_map.getSecondArchetype(), LIST_FACTORY.MakeArchetypeInput);
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
