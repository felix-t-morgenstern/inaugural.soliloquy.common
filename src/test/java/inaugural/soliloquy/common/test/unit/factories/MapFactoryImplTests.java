package inaugural.soliloquy.common.test.unit.factories;

import inaugural.soliloquy.common.factories.MapFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.Map;

import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MapFactoryImplTests {
    private MapFactoryImpl _mapFactory;

    @BeforeEach
    void setUp() {
        _mapFactory = new MapFactoryImpl();
    }

    @Test
    void testMake() {
        Map<String, Integer> map = _mapFactory.make("", 0);
        assertNotNull(map);
        assertNotNull(map.getFirstArchetype());
        assertNotNull(map.getSecondArchetype());

        map.put("String1", 123);
        assertEquals(123, (int) map.get("String1"));
    }

    @SuppressWarnings("unused")
    @Test
    void testMakeWithNullArchetypes() {
        assertThrows(IllegalArgumentException.class, () -> _mapFactory.make("", null));
        assertThrows(IllegalArgumentException.class, () -> _mapFactory.make(null, 0));
    }

    @Test
    void testArchetypeWithNullArchetype() {
        //noinspection unchecked
        Map<String, Integer> mockMapWithNullKeyArchetype = mock(Map.class);
        when(mockMapWithNullKeyArchetype.getFirstArchetype()).thenReturn(null);
        when(mockMapWithNullKeyArchetype.getSecondArchetype()).thenReturn(randomInt());

        //noinspection unchecked
        Map<String, Integer> mockMapWithNullValueArchetype = mock(Map.class);
        when(mockMapWithNullValueArchetype.getFirstArchetype()).thenReturn(randomString());
        when(mockMapWithNullValueArchetype.getSecondArchetype()).thenReturn(null);

        assertThrows(IllegalArgumentException.class,
                () -> _mapFactory.make(mockMapWithNullKeyArchetype, 123));
        assertThrows(IllegalArgumentException.class,
                () -> _mapFactory.make(mockMapWithNullValueArchetype, 123));
        assertThrows(IllegalArgumentException.class,
                () -> _mapFactory.make(123, mockMapWithNullKeyArchetype));
        assertThrows(IllegalArgumentException.class,
                () -> _mapFactory.make(123, mockMapWithNullValueArchetype));
    }

    @Test
    void testHashCode() {
        assertEquals(MapFactoryImpl.class.getCanonicalName().hashCode(),
                _mapFactory.hashCode());
    }

    @Test
    void testEquals() {
        MapFactory equalMapFactory = new MapFactoryImpl();
        MapFactory unequalMapFactory = mock(MapFactory.class);

        assertEquals(_mapFactory, equalMapFactory);
        assertNotEquals(_mapFactory, unequalMapFactory);
        assertNotEquals(null, _mapFactory);
    }

    @Test
    void testToString() {
        assertEquals(MapFactoryImpl.class.getCanonicalName(),
                _mapFactory.toString());
    }
}
