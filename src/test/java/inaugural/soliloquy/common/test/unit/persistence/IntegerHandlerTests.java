package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.IntegerHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.persistence.TypeHandler;

import static inaugural.soliloquy.tools.random.Random.randomInt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class IntegerHandlerTests {
    private final Integer VALUE = randomInt();
    private final String WRITTEN_VALUE = VALUE.toString();

    private IntegerHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new IntegerHandler();
    }

    @Test
    public void testTypeHandled() {
        assertEquals(Integer.class.getCanonicalName(), handler.typeHandled());
    }

    @Test
    public void testRead() {
        assertEquals(VALUE, handler.read(WRITTEN_VALUE));
    }

    @Test
    public void testReadNull() {
        assertThrows(IllegalArgumentException.class, () -> handler.read(null));
    }

    @Test
    public void testWrite() {
        assertEquals(WRITTEN_VALUE, handler.write(VALUE));
    }

    @Test
    public void testWriteWhenNull() {
        assertEquals("", handler.write(null));
    }
}
