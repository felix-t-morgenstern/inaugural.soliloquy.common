package inaugural.soliloquy.common.test.unit.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.persistence.PersistentStringHandler;
import soliloquy.specs.common.persistence.PersistentValueTypeHandler;

public class PersistentStringHandlerTests {
    private PersistentStringHandler persistentStringHandler;
    
    @BeforeEach
    protected void setUp() throws Exception {
        persistentStringHandler = new PersistentStringHandler();
    }

    @Test
    public void testRead() {
        assertTrue(persistentStringHandler.read("This Value!").equals("This Value!"));
    }

    @Test
    public void testReadNull() {
        try {
            persistentStringHandler.read(null);
            assertTrue(false);
        } catch(IllegalArgumentException e) {
            assertTrue(true);
        } catch(Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void testWrite() {
        assertTrue(persistentStringHandler.write("This Value!").equals("This Value!"));
    }

    @Test
    public void testGetArchetype() {
        assertTrue(persistentStringHandler.getArchetype() != null);
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(PersistentValueTypeHandler.class.getCanonicalName() + "<" +
                        String.class.getCanonicalName() + ">",
                persistentStringHandler.getInterfaceName());
    }
}
