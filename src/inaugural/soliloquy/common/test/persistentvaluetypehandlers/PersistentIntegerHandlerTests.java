package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentIntegerHandler;

public class PersistentIntegerHandlerTests {
	private PersistentIntegerHandler persistentIntegerHandler;
    
	@BeforeEach
    protected void setUp() throws Exception {
    	persistentIntegerHandler = new PersistentIntegerHandler();
    }

	@Test
	public void testRead() {
		assertTrue(persistentIntegerHandler.read("123") == 123);
	}

	@Test
	public void testReadNull() {
		try {
			persistentIntegerHandler.read(null);
			assertTrue(false);
		} catch(IllegalArgumentException e) {
			assertTrue(true);
		} catch(Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testWrite() {
		assertTrue(persistentIntegerHandler.write(123).equals("123"));
	}

	@Test
	public void testWriteWhenNull() {
		assertTrue(persistentIntegerHandler.write(null).equals(""));
	}

	@Test
	public void testGetArchetype() {
		assertTrue(persistentIntegerHandler.getArchetype() != null);
	}

	@Test
	public void testGetInterfaceName() {
		assertTrue(persistentIntegerHandler.getInterfaceName()
				.equals("soliloquy.common.persistentvaluetypehandlers.IPersistentValueTypeHandler<java.lang.Integer>"));
	}
}
