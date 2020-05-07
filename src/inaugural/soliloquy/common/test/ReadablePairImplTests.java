package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.ReadablePairImpl;
import inaugural.soliloquy.common.test.fakes.FakePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.ReadablePair;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testHashCode() {
        final int item1 = 123123;
        final String item2 = "item2";
        ReadablePair<Integer,String> pair = new ReadablePairImpl<>(item1, item2, item1, item2);

        final int expectedHashCode = Objects.hash(item1, item2);

        assertEquals(expectedHashCode, pair.hashCode());
    }

    @Test
    void testEquals() {
        final String unequalValue1 = "unequalValue1";
        final int unequalValue2 = 445566;

        ReadablePairImpl<String,Integer> pairWithNullValue =
                new ReadablePairImpl<>(null, VALUE_2, ARCHETYPE_1, ARCHETYPE_2);
        Pair<String,Integer> pairEqualToPairWithNullValue = new FakePair<>(null, VALUE_2);

        Pair<String,Integer> equalPair = new FakePair<>(VALUE_1, VALUE_2);
        Pair<String,Integer> unequalPair1 = new FakePair<>(unequalValue1, VALUE_2);
        Pair<String,Integer> unequalPair2 = new FakePair<>(VALUE_1, unequalValue2);

        assertEquals(_readablePair, equalPair);
        assertEquals(pairWithNullValue, pairEqualToPairWithNullValue);
        assertNotEquals(_readablePair, unequalPair1);
        assertNotEquals(_readablePair, unequalPair2);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    void testToString() {
        assertThrows(UnsupportedOperationException.class, _readablePair::toString);
    }
}
