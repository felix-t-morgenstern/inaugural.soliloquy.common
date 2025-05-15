package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.BooleanHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.persistence.TypeHandler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class BooleanHandlerTests {
    private BooleanHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new BooleanHandler();
    }

    @Test
    public void testTypeHandled() {
        assertEquals(Boolean.class.getCanonicalName(), handler.typeHandled());
    }

    @Test
    public void testRead() {
        assertEquals(true, handler.read("true"));
    }

    @Test
    public void testReadNull() {
        assertThrows(IllegalArgumentException.class, () -> handler.read(null));
    }

    @Test
    public void testWrite() {
        assertEquals("true", handler.write(true));
    }
}
