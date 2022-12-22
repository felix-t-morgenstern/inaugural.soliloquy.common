package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.UuidHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.persistence.TypeHandler;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class UuidHandlerTests {
    private final String ENTITY_UUID_STRING = "5ab46602-2493-4dbd-831c-6e63a6e6094b";
    private final UUID GUID = UUID.fromString(ENTITY_UUID_STRING);

    private TypeHandler<UUID> _uuidHandler;

    @BeforeEach
    void setUp() {
        _uuidHandler = new UuidHandler();
    }

    @Test
    void testGetArchetype() {
        assertNotNull(_uuidHandler.getArchetype());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        UUID.class.getCanonicalName() + ">",
                _uuidHandler.getInterfaceName());
    }

    @Test
    void testRead() {
        assertEquals(GUID, _uuidHandler.read(ENTITY_UUID_STRING));
    }

    @Test
    void testReadNull() {
        assertNull(_uuidHandler.read(""));
    }

    @Test
    void testWrite() {
        assertEquals(ENTITY_UUID_STRING, _uuidHandler.write(GUID));
    }

    @Test
    void testWriteNull() {
        assertEquals("", _uuidHandler.write(null));
    }

    @Test
    void testHashCode() {
        assertEquals((TypeHandler.class.getCanonicalName() + "<" +
                        UUID.class.getCanonicalName() + ">").hashCode(),
                _uuidHandler.hashCode());
    }

    @Test
    void testEquals() {
        TypeHandler<UUID> equalHandler = new UuidHandler();
        //noinspection unchecked
        TypeHandler<UUID> unequalHandler = mock(TypeHandler.class);

        assertEquals(_uuidHandler, equalHandler);
        assertNotEquals(_uuidHandler, unequalHandler);
        assertNotEquals(null, _uuidHandler);
    }

    @Test
    void testToString() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        UUID.class.getCanonicalName() + ">",
                _uuidHandler.toString());
    }
}
