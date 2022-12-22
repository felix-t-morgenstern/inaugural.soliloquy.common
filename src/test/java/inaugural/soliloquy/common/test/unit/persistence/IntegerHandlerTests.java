package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.IntegerHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.persistence.TypeHandler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class IntegerHandlerTests {
    private IntegerHandler _integerHandler;

    @BeforeEach
    void setUp() {
        _integerHandler = new IntegerHandler();
    }

    @Test
    void testRead() {
        assertEquals(123, (int) _integerHandler.read("123"));
    }

    @Test
    void testReadNull() {
        assertThrows(IllegalArgumentException.class, () -> _integerHandler.read(null));
    }

    @Test
    void testWrite() {
        assertEquals("123", _integerHandler.write(123));
    }

    @Test
    void testWriteWhenNull() {
        assertEquals("", _integerHandler.write(null));
    }

    @Test
    void testHashCode() {
        assertEquals((TypeHandler.class.getCanonicalName() + "<" +
                        Integer.class.getCanonicalName() + ">").hashCode(),
                _integerHandler.hashCode());
    }

    @Test
    void testEquals() {
        TypeHandler<Integer> equalHandler = new IntegerHandler();
        //noinspection unchecked
        TypeHandler<Integer> unequalHandler = mock(TypeHandler.class);

        assertEquals(_integerHandler, equalHandler);
        assertNotEquals(_integerHandler, unequalHandler);
        assertNotEquals(null, _integerHandler);
    }

    @Test
    void testToString() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Integer.class.getCanonicalName() + ">",
                _integerHandler.toString());
    }

    @Test
    void testGetArchetype() {
        assertNotNull(_integerHandler.getArchetype());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Integer.class.getCanonicalName() + ">",
                _integerHandler.getInterfaceName());
    }
}
