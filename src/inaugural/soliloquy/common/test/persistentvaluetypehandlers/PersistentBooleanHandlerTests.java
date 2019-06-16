package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentBooleanHandler;
import soliloquy.specs.common.entities.IPersistentValueTypeHandler;

import static org.junit.jupiter.api.Assertions.*;

class PersistentBooleanHandlerTests {
	private PersistentBooleanHandler _persistentBooleanHandler;
	
    @BeforeEach
	void setUp() {
		_persistentBooleanHandler = new PersistentBooleanHandler();
    }
	
    @Test
	void testRead() {
		assertEquals(true, _persistentBooleanHandler.read("true"));
	}

    @Test
	void testReadNull() {
    	assertThrows(IllegalArgumentException.class, () -> _persistentBooleanHandler.read(null));
	}

    @Test
	void testWrite() {
		assertEquals("true", _persistentBooleanHandler.write(true));
	}

    @Test
	void testGetArchetype() {
		assertNotNull(_persistentBooleanHandler.getArchetype());
	}

    @Test
	void testGetInterfaceName() {
		assertEquals(IPersistentValueTypeHandler.class.getCanonicalName() + "<" +
						Boolean.class.getCanonicalName() + ">",
				_persistentBooleanHandler.getInterfaceName());
	}
}
