package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.StringHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.persistence.TypeHandler;

import static org.junit.jupiter.api.Assertions.*;

public class StringHandlerTests {
    private StringHandler _stringHandler;

    @BeforeEach
    protected void setUp() {
        _stringHandler = new StringHandler();
    }

    @Test
    public void testRead() {
        assertEquals("This Value!", _stringHandler.read("This Value!"));
    }

    @Test
    public void testReadNull() {
        try {
            _stringHandler.read(null);
            fail("Should fail on invalid value");
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        catch (Exception e) {
            fail("Should throw correct exception");
        }
    }

    @Test
    public void testWrite() {
        assertEquals("This Value!", _stringHandler.write("This Value!"));
    }

    @Test
    public void testGetArchetype() {
        assertNotNull(_stringHandler.getArchetype());
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        String.class.getCanonicalName() + ">",
                _stringHandler.getInterfaceName());
    }
}
