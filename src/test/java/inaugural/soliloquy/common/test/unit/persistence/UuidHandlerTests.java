package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.UuidHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.persistence.TypeHandler;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class UuidHandlerTests {
    private final UUID UUID = java.util.UUID.randomUUID();

    private TypeHandler<UUID> handler;

    @BeforeEach
    public void setUp() {
        handler = new UuidHandler();
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
}
