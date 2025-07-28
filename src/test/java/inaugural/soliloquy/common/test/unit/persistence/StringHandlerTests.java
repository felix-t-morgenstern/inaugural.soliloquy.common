package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.StringHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Pair;

import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;

public class StringHandlerTests {
    private final String VALUE = randomString();

    private StringHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new StringHandler();
    }

    @Test
    public void testRead() {
        assertEquals(VALUE, handler.read(VALUE));
    }

    @Test
    public void testReadNull() {
        //noinspection CatchMayIgnoreException
        try {
            handler.read(null);
            fail("Should fail on invalid value");
        }
        catch (IllegalArgumentException e) {
        }
        catch (Exception e) {
            fail("Should throw correct exception");
        }
    }

    @Test
    public void testWrite() {
        assertEquals(VALUE, handler.write(VALUE));
    }
}
