package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.BooleanHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.persistence.TypeHandler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class BooleanHandlerTests {
    private BooleanHandler booleanHandler;

    @BeforeEach
    public void setUp() {
        booleanHandler = new BooleanHandler();
    }

    @Test
    public void testRead() {
        assertEquals(true, booleanHandler.read("true"));
    }

    @Test
    public void testReadNull() {
        assertThrows(IllegalArgumentException.class, () -> booleanHandler.read(null));
    }

    @Test
    public void testWrite() {
        assertEquals("true", booleanHandler.write(true));
    }

    @Test
    public void testArchetype() {
        assertNotNull(booleanHandler.archetype());
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Boolean.class.getCanonicalName() + ">",
                booleanHandler.getInterfaceName());
    }

    @Test
    public void testHashCode() {
        assertEquals((TypeHandler.class.getCanonicalName() + "<" +
                        Boolean.class.getCanonicalName() + ">").hashCode(),
                booleanHandler.hashCode());
    }

    @Test
    public void testEquals() {
        var equalHandler = new BooleanHandler();
        //noinspection unchecked
        TypeHandler<Boolean> unequalHandler = mock(TypeHandler.class);

        assertEquals(booleanHandler, equalHandler);
        assertNotEquals(booleanHandler, unequalHandler);
        assertNotEquals(null, booleanHandler);
    }

    @Test
    public void testToString() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Boolean.class.getCanonicalName() + ">",
                booleanHandler.toString());
    }
}
