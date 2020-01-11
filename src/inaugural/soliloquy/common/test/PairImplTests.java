package inaugural.soliloquy.common.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.PairImpl;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.ReadablePair;

import static org.junit.jupiter.api.Assertions.*;

class PairImplTests {
    private Pair<String,Integer> _pair;

    private final String ARCHETYPE_1 = "Archetype1";
    private final Integer ARCHETYPE_2 = 445566;

    @BeforeEach
    void setUp() {
        _pair = new PairImpl<>(null, null, ARCHETYPE_1, ARCHETYPE_2);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new PairImpl<>("", 0, null, 0));
        assertThrows(IllegalArgumentException.class, () -> new PairImpl<>("", 0, "", null));
    }
    
    @Test
    void testGetAndSetItems1And2() {
        assertNull(_pair.getItem1());
        assertNull(_pair.getItem2());

        String value1 = "Value1";
        _pair.setItem1(value1);
        assertEquals(value1, _pair.getItem1());

        Integer value2 = 112233;
        _pair.setItem2(value2);
        assertEquals(value2, _pair.getItem2());
    }

    @Test
    void testGetFirstArchetype() {
        assertEquals(ARCHETYPE_1, _pair.getFirstArchetype());
    }

    @Test
    void testGetSecondArchetype() {
        assertEquals(ARCHETYPE_2, _pair.getSecondArchetype());
    }

    @Test
    void testRepresentation() {
        String orig1 = "orig1";
        Integer orig2 = 123;
        _pair.setItem1(orig1);
        _pair.setItem2(orig2);

        ReadablePair<String, Integer> representation = _pair.representation();

        _pair.setItem1("new1");
        _pair.setItem2(456);

        assertNotNull(representation);
        assertNotSame(_pair, representation);
        assertEquals(orig1, representation.getItem1());
        assertEquals(orig2, representation.getItem2());
        assertEquals(ARCHETYPE_1, representation.getFirstArchetype());
        assertEquals(ARCHETYPE_2, representation.getSecondArchetype());
        assertThrows(ClassCastException.class, () -> {
            @SuppressWarnings("unused") Pair<String,Integer> pair =
                    ((Pair<String, Integer>)representation);
        });
    }

    @Test
    void testGetParameterizedClassName() {
        assertEquals(Pair.class.getCanonicalName() + "<" + String.class.getCanonicalName() + ","
                + Integer.class.getCanonicalName() + ">", _pair.getInterfaceName());
    }
}
