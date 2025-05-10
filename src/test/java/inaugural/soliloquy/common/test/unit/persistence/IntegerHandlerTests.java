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

    @Test
    public void testHashCode() {
        assertEquals((TypeHandler.class.getCanonicalName() + "<" +
                        Integer.class.getCanonicalName() + ">").hashCode(),
                handler.hashCode());
    }

    @Test
    public void testEquals() {
        var equalHandler = new IntegerHandler();
        //noinspection unchecked
        TypeHandler<Integer> unequalHandler = mock(TypeHandler.class);

        assertEquals(handler, equalHandler);
        assertNotEquals(handler, unequalHandler);
        assertNotEquals(null, handler);
    }

    @Test
    public void testToString() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Integer.class.getCanonicalName() + ">",
                handler.toString());
    }

    @Test
    public void testArchetype() {
        assertNotNull(handler.archetype());
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Integer.class.getCanonicalName() + ">",
                handler.getInterfaceName());
    }
}
