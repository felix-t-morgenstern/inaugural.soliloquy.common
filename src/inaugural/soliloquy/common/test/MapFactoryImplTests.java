package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.MapFactoryImpl;
import inaugural.soliloquy.common.test.fakes.FakeCollectionFactory;
import inaugural.soliloquy.common.test.fakes.FakeMap;
import inaugural.soliloquy.common.test.fakes.FakeMapFactory;
import inaugural.soliloquy.common.test.fakes.FakePairFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.Map;

import static org.junit.jupiter.api.Assertions.*;

class MapFactoryImplTests {
    private MapFactoryImpl _mapFactory;

    @BeforeEach
    void setUp() {
        // (No need to test PairFactory functionality in this suite)
        // TODO: Revisit the assumption that PairFactory needn't be tested here
        _mapFactory = new MapFactoryImpl(new FakePairFactory(), new FakeCollectionFactory());
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new MapFactoryImpl(null, new FakeCollectionFactory()));
        assertThrows(IllegalArgumentException.class,
                () -> new MapFactoryImpl(new FakePairFactory(), null));
    }

    @Test
    void testMake() {
        Map<String,Integer> map = _mapFactory.make("",0);
        assertNotNull(map);
        assertNotNull(map.getFirstArchetype());
        assertNotNull(map.getSecondArchetype());

        map.put("String1", 123);
        assertEquals(123, (int) map.get("String1"));
    }
    
    @SuppressWarnings("unused")
    @Test
    void testMakeWithNullArchetypes() {
        assertThrows(IllegalArgumentException.class, () -> _mapFactory.make("",null));
        assertThrows(IllegalArgumentException.class, () -> _mapFactory.make(null,0));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    void testArchetypeWithNullArchetype() {
        FakeMap archetype = new FakeMap(null, null);

        assertThrows(IllegalArgumentException.class,
                () -> _mapFactory.make(archetype, 123));
        assertThrows(IllegalArgumentException.class,
                () -> _mapFactory.make(123, archetype));
    }

    @Test
    void testHashCode() {
        assertEquals(MapFactoryImpl.class.getCanonicalName().hashCode(),
                _mapFactory.hashCode());
    }

    @Test
    void testEquals() {
        MapFactory equalMapUuidFactory = new MapFactoryImpl(new FakePairFactory(), new FakeCollectionFactory());
        MapFactory unequalMapUuidFactory = new FakeMapFactory();

        assertEquals(_mapFactory, equalMapUuidFactory);
        assertNotEquals(_mapFactory, unequalMapUuidFactory);
        assertNotEquals(null, _mapFactory);
    }

    @Test
    void testToString() {
        assertEquals(MapFactoryImpl.class.getCanonicalName(),
                _mapFactory.toString());
    }
}
