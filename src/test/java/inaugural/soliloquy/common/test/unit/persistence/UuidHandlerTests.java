package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.UuidHandler;
import org.junit.Before;
import org.junit.Test;
import soliloquy.specs.common.persistence.TypeHandler;

import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class UuidHandlerTests {
    private final UUID UUID = java.util.UUID.randomUUID();

    private TypeHandler<UUID> handler;

    @Before
    public void setUp() {
        handler = new UuidHandler();
    }

    @Test
    public void testArchetype() {
        assertNotNull(handler.archetype());
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        UUID.class.getCanonicalName() + ">",
                handler.getInterfaceName());
    }

    @Test
    public void testRead() {
        assertEquals(UUID, handler.read(UUID.toString()));
    }

    @Test
    public void testReadNull() {
        assertNull(handler.read(""));
    }

    @Test
    public void testWrite() {
        assertEquals(UUID.toString(), handler.write(UUID));
    }

    @Test
    public void testWriteNull() {
        assertEquals("", handler.write(null));
    }

    @Test
    public void testHashCode() {
        assertEquals((TypeHandler.class.getCanonicalName() + "<" +
                        UUID.class.getCanonicalName() + ">").hashCode(),
                handler.hashCode());
    }

    @Test
    public void testEquals() {
        var equalHandler = new UuidHandler();
        //noinspection unchecked
        TypeHandler<UUID> unequalHandler = mock(TypeHandler.class);

        assertEquals(handler, equalHandler);
        assertNotEquals(handler, unequalHandler);
        assertNotEquals(null, handler);
    }

    @Test
    public void testToString() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        UUID.class.getCanonicalName() + ">",
                handler.toString());
    }
}
