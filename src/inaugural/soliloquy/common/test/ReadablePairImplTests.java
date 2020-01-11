package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.ReadablePairImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.ReadablePair;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReadablePairImplTests {
    private ReadablePair<String,Integer> _readablePair;

    private final String VALUE_1 = "Value1";
    private final Integer VALUE_2 = 112233;
    private final String ARCHETYPE_1 = "Archetype1";
    private final Integer ARCHETYPE_2 = 445566;

    @BeforeEach
    void setUp() {
        _readablePair = new ReadablePairImpl<>(VALUE_1, VALUE_2, ARCHETYPE_1, ARCHETYPE_2);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new ReadablePairImpl<>("", 0, null, 0));
        assertThrows(IllegalArgumentException.class, () -> new ReadablePairImpl<>("", 0, "", null));
    }

    @Test
    void testGetItems1And2() {
        assertEquals(VALUE_1, _readablePair.getItem1());
        assertEquals(VALUE_2, _readablePair.getItem2());
    }

    @Test
    void testGetFirstArchetype() {
        assertEquals(ARCHETYPE_1, _readablePair.getFirstArchetype());
    }

    @Test
    void testGetSecondArchetype() {
        assertEquals(ARCHETYPE_2, _readablePair.getSecondArchetype());
    }

    @Test
    void testGetParameterizedClassName() {
        assertEquals(ReadablePair.class.getCanonicalName() + "<" + String.class.getCanonicalName()
                + "," + Integer.class.getCanonicalName() + ">", _readablePair.getInterfaceName());
    }
}
