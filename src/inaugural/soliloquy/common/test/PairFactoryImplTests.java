package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.test.fakes.FakeMap;
import inaugural.soliloquy.common.test.fakes.FakePairFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.PairFactoryImpl;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.common.infrastructure.Pair;

import static org.junit.jupiter.api.Assertions.*;

class PairFactoryImplTests {
    private PairFactoryImpl _pairFactory;

    @BeforeEach
    void setUp() {
        _pairFactory = new PairFactoryImpl();
    }
    
    @Test
    void testMake() {
        Pair<String, Integer> pair = _pairFactory.make("String", 123);
        assertNotNull(pair);
        assertNotNull(pair.getFirstArchetype());
        assertNotNull(pair.getSecondArchetype());
        assertSame("String", pair.getItem1());
        assertEquals(123, (int) pair.getItem2());
    }

    @Test
    void testMakeWithNullParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _pairFactory.make("String", null));
        assertThrows(IllegalArgumentException.class,
                () -> _pairFactory.make("String", 0, "String", null));
        assertThrows(IllegalArgumentException.class,
                () -> _pairFactory.make("String", 0, null, 0));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    void testArchetypeWithNullArchetype() {
        FakeMap archetype1 = new FakeMap(123, null);
        FakeMap archetype2 = new FakeMap(null, 123);

        assertThrows(IllegalArgumentException.class, () -> _pairFactory.make(123, archetype1));
        assertThrows(IllegalArgumentException.class, () -> _pairFactory.make(archetype2, 123));
        assertThrows(IllegalArgumentException.class,
                () -> _pairFactory.make(123, null,123, archetype1));
        assertThrows(IllegalArgumentException.class,
                () -> _pairFactory.make(null, 123, archetype2, 123));
    }

    @Test
    void testHashCode() {
        assertEquals(PairFactoryImpl.class.getCanonicalName().hashCode(),
                _pairFactory.hashCode());
    }

    @Test
    void testEquals() {
        PairFactory equalPairFactory = new PairFactoryImpl();
        PairFactory unequalPairFactory = new FakePairFactory();

        assertEquals(_pairFactory, equalPairFactory);
        assertNotEquals(_pairFactory, unequalPairFactory);
        assertNotEquals(null, _pairFactory);
    }

    @Test
    void testToString() {
        assertEquals(PairFactoryImpl.class.getCanonicalName(),
                _pairFactory.toString());
    }
}
