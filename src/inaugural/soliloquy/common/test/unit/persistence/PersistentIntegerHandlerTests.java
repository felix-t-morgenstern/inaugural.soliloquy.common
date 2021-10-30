package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.PersistentIntegerHandler;
import inaugural.soliloquy.common.test.fakes.FakePersistentIntegerHandler;
import inaugural.soliloquy.tools.persistence.PersistentTypeHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.persistence.PersistentValueTypeHandler;

import static org.junit.jupiter.api.Assertions.*;

class PersistentIntegerHandlerTests {
    private PersistentIntegerHandler _persistentIntegerHandler;
    
    @BeforeEach
    void setUp() {
        _persistentIntegerHandler = new PersistentIntegerHandler();
    }

    @Test
    void testRead() {
        assertEquals(123, (int) _persistentIntegerHandler.read("123"));
    }

    @Test
    void testReadNull() {
        assertThrows(IllegalArgumentException.class, () -> _persistentIntegerHandler.read(null));
    }

    @Test
    void testWrite() {
        assertEquals("123", _persistentIntegerHandler.write(123));
    }

    @Test
    void testWriteWhenNull() {
        assertEquals("", _persistentIntegerHandler.write(null));
    }

    @Test
    void testHashCode() {
        assertEquals((PersistentValueTypeHandler.class.getCanonicalName() + "<" +
                        Integer.class.getCanonicalName() + ">").hashCode(),
                _persistentIntegerHandler.hashCode());
    }

    @Test
    void testEquals() {
        PersistentTypeHandler<Integer> equalHandler =
                new PersistentIntegerHandler();
        PersistentValueTypeHandler<Integer> unequalHandler =
                new FakePersistentIntegerHandler();

        assertEquals(_persistentIntegerHandler, equalHandler);
        assertNotEquals(_persistentIntegerHandler, unequalHandler);
        assertNotEquals(null, _persistentIntegerHandler);
    }

    @Test
    void testToString() {
        assertEquals(PersistentValueTypeHandler.class.getCanonicalName() + "<" +
                        Integer.class.getCanonicalName() + ">",
                _persistentIntegerHandler.toString());
    }

    @Test
    void testGetArchetype() {
        assertNotNull(_persistentIntegerHandler.getArchetype());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(PersistentValueTypeHandler.class.getCanonicalName() + "<java.lang.Integer>",
                _persistentIntegerHandler.getInterfaceName());
    }
}
