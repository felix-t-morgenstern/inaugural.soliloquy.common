package inaugural.soliloquy.common.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.PersistentVariableCache;
import inaugural.soliloquy.common.test.stubs.CollectionFactoryStub;
import soliloquy.common.specs.*;

import static org.junit.jupiter.api.Assertions.*;

class PersistentVariableCacheTests {
	private IPersistentVariableCache _persistentVariableCache;

	private final ICollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
	
    @BeforeEach
	void setUp() {
    	// archetype not necessary for test suite
    	_persistentVariableCache = new PersistentVariableCache(COLLECTION_FACTORY);
    }
    
    @Test
	void testPutAndSize() {
		_persistentVariableCache.setVariable("variable1", "value1");
		_persistentVariableCache.setVariable("variable2", "value2");
		_persistentVariableCache.setVariable("variable3", "value3");

		assertEquals(3, _persistentVariableCache.size());
    }

	@Test
	void testPutAndGet() {
		assertNull(_persistentVariableCache.getVariable("variable1"));

		_persistentVariableCache.setVariable("variable1", "value1");

		assertEquals("value1", _persistentVariableCache.getVariable("variable1"));
	}

    @Test
	void testPutWithNullOrEmptyParams() {
		assertThrows(IllegalArgumentException.class,
				() -> _persistentVariableCache.setVariable(null, 0));
		assertThrows(IllegalArgumentException.class,
				() -> _persistentVariableCache.setVariable("", 0));
		assertThrows(IllegalArgumentException.class,
				() -> _persistentVariableCache.setVariable("variable", null));
    }

    @Test
	void testPersistentVariableNamesRepresentation() {
		_persistentVariableCache.setVariable("variable1", "value1");
		_persistentVariableCache.setVariable("variable2", "value2");
		_persistentVariableCache.setVariable("variable3", "value3");

		ICollection<String> persistentVariableNamesRepresentation =
				_persistentVariableCache.getNamesRepresentation();

		assertNotNull(persistentVariableNamesRepresentation);
		assertEquals(3, persistentVariableNamesRepresentation.size());
		assertTrue(persistentVariableNamesRepresentation.contains("variable1"));
		assertTrue(persistentVariableNamesRepresentation.contains("variable2"));
		assertTrue(persistentVariableNamesRepresentation.contains("variable3"));
	}

	@Test
	void testRemove() {
    	assertFalse(_persistentVariableCache.remove("variable1"));

		_persistentVariableCache.setVariable("variable1", "value1");

		assertTrue(_persistentVariableCache.remove("variable1"));
		assertFalse(_persistentVariableCache.remove("variable1"));
		assertEquals(0, _persistentVariableCache.size());
	}

	@Test
	void testClear() {
    	assertEquals(0, _persistentVariableCache.size());

		_persistentVariableCache.setVariable("variable1", "value1");
		_persistentVariableCache.setVariable("variable2", "value2");
		_persistentVariableCache.setVariable("variable3", "value3");

		assertEquals(3, _persistentVariableCache.size());

		_persistentVariableCache.clear();

		assertEquals(0, _persistentVariableCache.size());
	}
}
