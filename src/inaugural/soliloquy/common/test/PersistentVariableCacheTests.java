package inaugural.soliloquy.common.test;

import java.util.HashMap;

import inaugural.soliloquy.common.test.stubs.MapFactoryStub;
import inaugural.soliloquy.common.test.stubs.PairFactoryStub;
import inaugural.soliloquy.common.test.stubs.PersistentVariableFactoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.PersistentVariableCache;
import inaugural.soliloquy.common.test.stubs.CollectionFactoryStub;
import soliloquy.common.specs.*;

import static org.junit.jupiter.api.Assertions.*;

class PersistentVariableCacheTests {
	private IPersistentVariableCache _persistentVariableCache;

	private final IPersistentVariableFactory PERSISTENT_VARIABLE_FACTORY =
			new PersistentVariableFactoryStub();
	private final ICollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
	
    @BeforeEach
	void setUp() {
    	// archetype not necessary for test suite
    	_persistentVariableCache = new PersistentVariableCache(COLLECTION_FACTORY);
    }
    
    @Test
	void testPutAndSize() {
		IPersistentVariable persistentVariable1 =
				PERSISTENT_VARIABLE_FACTORY.make("variable1", "value1");
		IPersistentVariable persistentVariable2 =
				PERSISTENT_VARIABLE_FACTORY.make("variable2", "value2");
		IPersistentVariable persistentVariable3 =
				PERSISTENT_VARIABLE_FACTORY.make("variable3", "value3");

		_persistentVariableCache.put(persistentVariable1);
		_persistentVariableCache.put(persistentVariable2);
		_persistentVariableCache.put(persistentVariable3);

		assertEquals(3, _persistentVariableCache.size());
    }

	@Test
	void testPutAndGet() {
		assertNull(_persistentVariableCache.getVariable("variable1"));
		IPersistentVariable persistentVariable1 =
				PERSISTENT_VARIABLE_FACTORY.make("variable1", "value1");

		_persistentVariableCache.put(persistentVariable1);

		assertEquals("value1", _persistentVariableCache.getVariable("variable1").getValue());
	}

    @Test
	void testPutWithNullOrEmptyParams() {
		assertThrows(IllegalArgumentException.class, () -> _persistentVariableCache.put(null));
		assertThrows(IllegalArgumentException.class, () -> {
			IPersistentVariable persistentVariableWithNoValue =
					PERSISTENT_VARIABLE_FACTORY.make("name", null);
			_persistentVariableCache.put(persistentVariableWithNoValue);
		});
    }

    @Test
	void testPersistentVariableNamesRepresentation() {
		IPersistentVariable persistentVariable1 =
				PERSISTENT_VARIABLE_FACTORY.make("variable1", "value1");
		IPersistentVariable persistentVariable2 =
				PERSISTENT_VARIABLE_FACTORY.make("variable2", "value2");
		IPersistentVariable persistentVariable3 =
				PERSISTENT_VARIABLE_FACTORY.make("variable3", "value3");

		_persistentVariableCache.put(persistentVariable1);
		_persistentVariableCache.put(persistentVariable2);
		_persistentVariableCache.put(persistentVariable3);

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

		IPersistentVariable persistentVariable1 =
				PERSISTENT_VARIABLE_FACTORY.make("variable1", "value1");
		_persistentVariableCache.put(persistentVariable1);

		assertTrue(_persistentVariableCache.remove("variable1"));
		assertFalse(_persistentVariableCache.remove("variable1"));
		assertEquals(0, _persistentVariableCache.size());
	}

	@Test
	void testClear() {
    	assertEquals(0, _persistentVariableCache.size());

		IPersistentVariable persistentVariable1 =
				PERSISTENT_VARIABLE_FACTORY.make("variable1", "value1");
		IPersistentVariable persistentVariable2 =
				PERSISTENT_VARIABLE_FACTORY.make("variable2", "value2");
		IPersistentVariable persistentVariable3 =
				PERSISTENT_VARIABLE_FACTORY.make("variable3", "value3");

		_persistentVariableCache.put(persistentVariable1);
		_persistentVariableCache.put(persistentVariable2);
		_persistentVariableCache.put(persistentVariable3);

		assertEquals(3, _persistentVariableCache.size());

		_persistentVariableCache.clear();

		assertEquals(0, _persistentVariableCache.size());
	}
}
