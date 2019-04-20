package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentBooleanHandler;

public class PersistentBooleanHandlerTests {
	private PersistentBooleanHandler persistentBooleanHandler;
	
    @BeforeEach
    protected void setUp() throws Exception {
		persistentBooleanHandler = new PersistentBooleanHandler();
    }
	
    @Test
	public void testRead() {
		assertTrue(persistentBooleanHandler.read("true") == true);
	}

    @Test
	public void testReadNull() {
		try {
			persistentBooleanHandler.read(null);
			assertTrue(false);
		} catch(IllegalArgumentException e) {
			assertTrue(true);
		} catch(Exception e) {
			assertTrue(false);
		}
	}

    @Test
	public void testWrite() {
		assertTrue(persistentBooleanHandler.write(true).equals("true"));
	}

    @Test
	public void testGetArchetype() {
		assertTrue(persistentBooleanHandler.getArchetype() != null);
	}

    @Test
	public void testGetInterfaceName() {
		assertEquals("soliloquy.common.specs.IPersistentValueTypeHandler<java.lang.Boolean>",
				persistentBooleanHandler.getInterfaceName());
	}
}
