package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.PairImpl;
import inaugural.soliloquy.common.test.fakes.FakePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.Pair;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class PairImplTests {
    private Pair<String,Integer> _pair;

    private final String VALUE_1 = "Value1";
    private final Integer VALUE_2 = 112233;
    private final String ARCHETYPE_1 = "Archetype1";
    private final Integer ARCHETYPE_2 = 445566;

    @BeforeEach
    void setUp() {
        _pair = new PairImpl<>(null, null, ARCHETYPE_1, ARCHETYPE_2);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new PairImpl<>("", 0, null, 0));
        assertThrows(IllegalArgumentException.class, () -> new PairImpl<>("", 0, "", null));
    }
    
    @Test
    void testGetAndSetItems1And2() {
        assertNull(_pair.getItem1());
        assertNull(_pair.getItem2());

        _pair.setItem1(VALUE_1);
        assertEquals(VALUE_1, _pair.getItem1());

        _pair.setItem2(VALUE_2);
        assertEquals(VALUE_2, _pair.getItem2());
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
    void testMakeClone() {
        String orig1 = "orig1";
        Integer orig2 = 123;
        _pair.setItem1(orig1);
        _pair.setItem2(orig2);

        Pair<String, Integer> clone = _pair.makeClone();

        assertNotNull(clone);
        assertNotSame(_pair, clone);
        assertEquals(_pair, clone);

        _pair.setItem1("new1");
        _pair.setItem2(456);

        assertNotEquals(_pair, clone);
    }

    @Test
    void testGetParameterizedClassName() {
        assertEquals(Pair.class.getCanonicalName() + "<" + String.class.getCanonicalName() + ","
                + Integer.class.getCanonicalName() + ">", _pair.getInterfaceName());
    }

    @Test
    void testHashCode() {
        final int item1 = 123123;
        final String item2 = "item2";
        Pair<Integer,String> pair = new PairImpl<>(item1, item2, item1, item2);

        final int expectedHashCode = Objects.hash(item1, item2);

        assertEquals(expectedHashCode, pair.hashCode());
    }

    @Test
    void testEquals() {
        _pair.setItem1(VALUE_1);
        _pair.setItem2(VALUE_2);

        final String unequalValue1 = "unequalValue1";
        final int unequalValue2 = 445566;

        Pair<String,Integer> equalPair = new FakePair<>(VALUE_1, VALUE_2);
        Pair<String,Integer> unequalPair1 = new FakePair<>(unequalValue1, VALUE_2);
        Pair<String,Integer> unequalPair2 = new FakePair<>(VALUE_1, unequalValue2);

        assertEquals(_pair, equalPair);
        assertNotEquals(_pair, unequalPair1);
        assertNotEquals(_pair, unequalPair2);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    void testToString() {
        assertThrows(UnsupportedOperationException.class, _pair::toString);
    }
}
