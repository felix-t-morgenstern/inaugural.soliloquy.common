package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentIntegerHandler;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;

import static org.junit.jupiter.api.Assertions.*;

class PersistentIntegerHandlerTests {
	private PersistentIntegerHandler persistentIntegerHandler;
    
	@BeforeEach
	void setUp() {
    	persistentIntegerHandler = new PersistentIntegerHandler();
    }

	@Test
	void testRead() {
		assertEquals(123, (int) persistentIntegerHandler.read("123"));
	}

	@Test
	void testReadNull() {
		assertThrows(IllegalArgumentException.class, () -> persistentIntegerHandler.read(null));
	}
	
	@Test
	void testWrite() {
		assertEquals("123", persistentIntegerHandler.write(123));
	}

	@Test
	void testWriteWhenNull() {
		assertEquals("", persistentIntegerHandler.write(null));
	}

	@Test
	void testGetArchetype() {
		assertNotNull(persistentIntegerHandler.getArchetype());
	}

	@Test
	void testGetInterfaceName() {
		assertEquals(PersistentValueTypeHandler.class.getCanonicalName() + "<java.lang.Integer>",
				persistentIntegerHandler.getInterfaceName());
	}
}
