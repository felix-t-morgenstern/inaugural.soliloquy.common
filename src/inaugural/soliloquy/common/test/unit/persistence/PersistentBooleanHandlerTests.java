package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.PersistentBooleanHandler;
import inaugural.soliloquy.common.test.fakes.FakeVariableCacheFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.persistence.PersistentValueTypeHandler;

import static org.junit.jupiter.api.Assertions.*;

class PersistentBooleanHandlerTests {
    private PersistentBooleanHandler _persistentBooleanHandler;

    @BeforeEach
    void setUp() {
        _persistentBooleanHandler = new PersistentBooleanHandler();
    }

    @Test
    void testRead() {
        assertEquals(true, _persistentBooleanHandler.read("true"));
    }

    @Test
    void testReadNull() {
        assertThrows(IllegalArgumentException.class, () -> _persistentBooleanHandler.read(null));
    }

    @Test
    void testWrite() {
        assertEquals("true", _persistentBooleanHandler.write(true));
    }

    @Test
    void testGetArchetype() {
        assertNotNull(_persistentBooleanHandler.getArchetype());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(PersistentValueTypeHandler.class.getCanonicalName() + "<" +
                        Boolean.class.getCanonicalName() + ">",
                _persistentBooleanHandler.getInterfaceName());
    }

    @Test
    void testHashCode() {
        assertEquals(PersistentBooleanHandler.class.getCanonicalName().hashCode(),
                _persistentBooleanHandler.hashCode());
    }

    @Test
    void testEquals() {
        PersistentValueTypeHandler<Boolean> equalPersistentBooleanHandler =
                new PersistentBooleanHandler();
        VariableCacheFactory unequalPersistentBooleanHandler = new FakeVariableCacheFactory();

        assertEquals(_persistentBooleanHandler, equalPersistentBooleanHandler);
        assertNotEquals(_persistentBooleanHandler, unequalPersistentBooleanHandler);
        assertNotEquals(null, _persistentBooleanHandler);
    }

    @Test
    void testToString() {
        assertEquals(PersistentBooleanHandler.class.getCanonicalName(),
                _persistentBooleanHandler.toString());
    }
}
