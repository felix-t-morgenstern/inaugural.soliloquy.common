package inaugural.soliloquy.common.test.unit.factories;

import inaugural.soliloquy.common.factories.MapFactoryImpl;
import org.junit.Before;
import org.junit.Test;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.Map;

import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MapFactoryImplTests {
    private final String KEY = randomString();
    private final Integer VALUE = randomInt();
    private final String KEY_ARCHETYPE = randomString();
    private final Integer VALUE_ARCHETYPE = randomInt();

    private MapFactory factory;

    @Before
    public void setUp() {
        factory = new MapFactoryImpl();
    }

    @Test
    public void testMake() {
        var map = factory.make(KEY_ARCHETYPE, VALUE_ARCHETYPE);
        map.put(KEY, VALUE);

        assertNotNull(map);
        assertNotNull(map.firstArchetype());
        assertEquals(KEY_ARCHETYPE, map.firstArchetype());
        assertNotNull(map.secondArchetype());
        assertEquals(VALUE_ARCHETYPE, map.secondArchetype());
        assertEquals(VALUE, map.get(KEY));
    }

    @SuppressWarnings("unused")
    @Test
    public void testMakeWithNullArchetypes() {
        assertThrows(IllegalArgumentException.class, () -> factory.make(KEY_ARCHETYPE, null));
        assertThrows(IllegalArgumentException.class, () -> factory.make(null, VALUE_ARCHETYPE));
    }

    @Test
    public void testArchetypeWithNullArchetype() {
        //noinspection unchecked
        Map<String, Integer> mockMapWithNullKeyArchetype = mock(Map.class);
        when(mockMapWithNullKeyArchetype.firstArchetype()).thenReturn(null);
        when(mockMapWithNullKeyArchetype.secondArchetype()).thenReturn(randomInt());

        //noinspection unchecked
        Map<String, Integer> mockMapWithNullValueArchetype = mock(Map.class);
        when(mockMapWithNullValueArchetype.firstArchetype()).thenReturn(randomString());
        when(mockMapWithNullValueArchetype.secondArchetype()).thenReturn(null);

        assertThrows(IllegalArgumentException.class,
                () -> factory.make(mockMapWithNullKeyArchetype, VALUE));
        assertThrows(IllegalArgumentException.class,
                () -> factory.make(mockMapWithNullValueArchetype, VALUE));
        assertThrows(IllegalArgumentException.class,
                () -> factory.make(VALUE, mockMapWithNullKeyArchetype));
        assertThrows(IllegalArgumentException.class,
                () -> factory.make(VALUE, mockMapWithNullValueArchetype));
    }

    @Test
    public void testHashCode() {
        assertEquals(MapFactoryImpl.class.getCanonicalName().hashCode(),
                factory.hashCode());
    }

    @Test
    public void testEquals() {
        var equalMapFactory = new MapFactoryImpl();
        var unequalMapFactory = mock(MapFactory.class);

        assertEquals(factory, equalMapFactory);
        assertNotEquals(factory, unequalMapFactory);
        assertNotEquals(null, factory);
    }

    @Test
    public void testToString() {
        assertEquals(MapFactoryImpl.class.getCanonicalName(),
                factory.toString());
    }
}
