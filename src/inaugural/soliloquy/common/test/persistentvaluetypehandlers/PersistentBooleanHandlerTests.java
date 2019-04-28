package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentBooleanHandler;

import static org.junit.jupiter.api.Assertions.*;

class PersistentBooleanHandlerTests {
	private PersistentBooleanHandler persistentBooleanHandler;
	
    @BeforeEach
	void setUp() {
		persistentBooleanHandler = new PersistentBooleanHandler();
    }
	
    @Test
	void testRead() {
		assertEquals(true, persistentBooleanHandler.read("true"));
	}

    @Test
	void testReadNull() {
    	assertThrows(IllegalArgumentException.class, () -> persistentBooleanHandler.read(null));
	}

    @Test
	void testWrite() {
		assertEquals("true", persistentBooleanHandler.write(true));
	}

    @Test
	void testGetArchetype() {
		assertNotNull(persistentBooleanHandler.getArchetype());
	}

    @Test
	void testGetInterfaceName() {
		assertEquals("soliloquy.common.specs.IPersistentValueTypeHandler<java.lang.Boolean>",
				persistentBooleanHandler.getInterfaceName());
	}
}
