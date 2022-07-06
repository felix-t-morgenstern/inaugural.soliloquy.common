package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.BooleanHandler;
import inaugural.soliloquy.common.test.fakes.FakeVariableCacheFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.persistence.TypeHandler;

import static org.junit.jupiter.api.Assertions.*;

class BooleanHandlerTests {
    private BooleanHandler _booleanHandler;

    @BeforeEach
    void setUp() {
        _booleanHandler = new BooleanHandler();
    }

    @Test
    void testRead() {
        assertEquals(true, _booleanHandler.read("true"));
    }

    @Test
    void testReadNull() {
        assertThrows(IllegalArgumentException.class, () -> _booleanHandler.read(null));
    }

    @Test
    void testWrite() {
        assertEquals("true", _booleanHandler.write(true));
    }

    @Test
    void testGetArchetype() {
        assertNotNull(_booleanHandler.getArchetype());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Boolean.class.getCanonicalName() + ">",
                _booleanHandler.getInterfaceName());
    }

    @Test
    void testHashCode() {
        assertEquals((TypeHandler.class.getCanonicalName() + "<" +
                        Boolean.class.getCanonicalName() + ">").hashCode(),
                _booleanHandler.hashCode());
    }

    @Test
    void testEquals() {
        TypeHandler<Boolean> equalPersistentBooleanHandler = new BooleanHandler();
        VariableCacheFactory unequalPersistentBooleanHandler = new FakeVariableCacheFactory();

        assertEquals(_booleanHandler, equalPersistentBooleanHandler);
        assertNotEquals(_booleanHandler, unequalPersistentBooleanHandler);
        assertNotEquals(null, _booleanHandler);
    }

    @Test
    void testToString() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Boolean.class.getCanonicalName() + ">",
                _booleanHandler.toString());
    }
}
