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

	private final IPersistentVariableFactory PERSISTENT_VARIABLE_FACTORY = new PersistentVariableFactoryStub();
	private final ICollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
	
    @BeforeEach
	void setUp() {
    	// archetype not necessary for test suite
    	_persistentVariableCache = new PersistentVariableCache(COLLECTION_FACTORY);
    }
    
    @Test
	void testPutAndSize() {
		IPersistentVariable persistentVariable1 = PERSISTENT_VARIABLE_FACTORY.make("variable1", "value1");
		IPersistentVariable persistentVariable2 = PERSISTENT_VARIABLE_FACTORY.make("variable2", "value2");
		IPersistentVariable persistentVariable3 = PERSISTENT_VARIABLE_FACTORY.make("variable3", "value3");

		_persistentVariableCache.put(persistentVariable1);
		_persistentVariableCache.put(persistentVariable2);
		_persistentVariableCache.put(persistentVariable3);

		assertEquals(3, _persistentVariableCache.size());
    }

	@Test
	void testPutAndGet() {
		assertNull(_persistentVariableCache.get("variable1"));
		IPersistentVariable persistentVariable1 = PERSISTENT_VARIABLE_FACTORY.make("variable1", "value1");

		_persistentVariableCache.put(persistentVariable1);

		assertEquals("value1", _persistentVariableCache.get("variable1").getValue());
	}

    @Test
	void testPutWithNullOrEmptyParams() {
		assertThrows(IllegalArgumentException.class, () -> _persistentVariableCache.put(null));
		assertThrows(IllegalArgumentException.class, () -> {
			IPersistentVariable persistentVariableWithNoValue = PERSISTENT_VARIABLE_FACTORY.make("name", null);
			_persistentVariableCache.put(persistentVariableWithNoValue);
		});
    }

    @Test
	void testPersistentVariableNamesRepresentation() {
		IPersistentVariable persistentVariable1 = PERSISTENT_VARIABLE_FACTORY.make("variable1", "value1");
		IPersistentVariable persistentVariable2 = PERSISTENT_VARIABLE_FACTORY.make("variable2", "value2");
		IPersistentVariable persistentVariable3 = PERSISTENT_VARIABLE_FACTORY.make("variable3", "value3");

		_persistentVariableCache.put(persistentVariable1);
		_persistentVariableCache.put(persistentVariable2);
		_persistentVariableCache.put(persistentVariable3);

		ICollection<String> persistentVariableNamesRepresentation =
				_persistentVariableCache.persistentVariableNamesRepresentation();

		assertNotNull(persistentVariableNamesRepresentation);
		assertEquals(3, persistentVariableNamesRepresentation.size());
		assertTrue(persistentVariableNamesRepresentation.contains("variable1"));
		assertTrue(persistentVariableNamesRepresentation.contains("variable2"));
		assertTrue(persistentVariableNamesRepresentation.contains("variable3"));
	}

	@Test
	void testRemove() {
    	assertFalse(_persistentVariableCache.remove("variable1"));

		IPersistentVariable persistentVariable1 = PERSISTENT_VARIABLE_FACTORY.make("variable1", "value1");
		_persistentVariableCache.put(persistentVariable1);

		assertTrue(_persistentVariableCache.remove("variable1"));
		assertFalse(_persistentVariableCache.remove("variable1"));
		assertEquals(0, _persistentVariableCache.size());
	}

	@Test
	void testClear() {
    	assertEquals(0, _persistentVariableCache.size());

		IPersistentVariable persistentVariable1 = PERSISTENT_VARIABLE_FACTORY.make("variable1", "value1");
		IPersistentVariable persistentVariable2 = PERSISTENT_VARIABLE_FACTORY.make("variable2", "value2");
		IPersistentVariable persistentVariable3 = PERSISTENT_VARIABLE_FACTORY.make("variable3", "value3");

		_persistentVariableCache.put(persistentVariable1);
		_persistentVariableCache.put(persistentVariable2);
		_persistentVariableCache.put(persistentVariable3);

		assertEquals(3, _persistentVariableCache.size());

		_persistentVariableCache.clear();

		assertEquals(0, _persistentVariableCache.size());
	}

    // TODO: Move these over to the test suite for the persistent type handler for this class
//    @Test
//	void testRead() {
//    	String jankyInput = "name1,value1;name2,value2;name3,value3";
//
//    	_persistentVariableCache.read(jankyInput, true);
//
//    	// Test whether data and overridePreviousData are passed through to the PersistentValuesHandler
//		assertEquals(_persistentValuesHandlerDataRead, jankyInput);
//		assertTrue(_persistentValuesHandlerBooleanRead);
//
//    	// Test whether the output of the PersistentValuesHandler is passed through to the PersistentVariableFactory
//		assertEquals(3, _persistentVariableFactoryValuesRead.size());
//		assertEquals("value1", _persistentVariableFactoryValuesRead.get("name1"));
//		assertEquals("value2", _persistentVariableFactoryValuesRead.get("name2"));
//		assertEquals("value3", _persistentVariableFactoryValuesRead.get("name3"));
//
//    	// Test whether the PersistentVariableCache contains the output of the PersistentVariableFactory
//		assertEquals(3, _persistentVariableCache.size());
//		assertEquals("value1", _persistentVariableCache.get("name1").getValue());
//		assertEquals("value2", _persistentVariableCache.get("name2").getValue());
//		assertEquals("value3", _persistentVariableCache.get("name3").getValue());
//
//    	// Test whether overridePreviousData works
//
//    	String jankyInput2 = "name3,badValue;name4,value4";
//
//    	_persistentVariableCache.read(jankyInput2, false);
//
//		assertEquals(4, _persistentVariableCache.size());
//		assertEquals("value1", _persistentVariableCache.get("name1").getValue());
//		assertEquals("value2", _persistentVariableCache.get("name2").getValue());
//		assertEquals("value3", _persistentVariableCache.get("name3").getValue());
//		assertEquals("value4", _persistentVariableCache.get("name4").getValue());
//
//    	String jankyInput3 = "name1,newValue1;name4,newValue4";
//
//    	_persistentVariableCache.read(jankyInput3, true);
//
//		assertEquals(4, _persistentVariableCache.size());
//		assertEquals("newValue1", _persistentVariableCache.get("name1").getValue());
//		assertEquals("value2", _persistentVariableCache.get("name2").getValue());
//		assertEquals("value3", _persistentVariableCache.get("name3").getValue());
//		assertEquals("newValue4", _persistentVariableCache.get("name4").getValue());
//    }
//
//    @Test
//	void testWrite() {
//    	IPersistentVariable persistentVariable1 = PERSISTENT_VARIABLE_FACTORY.make("variable1", "value1");
//    	IPersistentVariable persistentVariable2 = PERSISTENT_VARIABLE_FACTORY.make("variable2", "value2");
//    	IPersistentVariable persistentVariable3 = PERSISTENT_VARIABLE_FACTORY.make("variable3", "value3");
//
//    	_persistentVariableCache.put(persistentVariable1);
//    	_persistentVariableCache.put(persistentVariable2);
//    	_persistentVariableCache.put(persistentVariable3);
//
//    	String writtenValue = _persistentVariableCache.write();
//
//    	// Test whether the contents of the PersistentVariableCache are passed to the PersistentValuesHandler
//
//		assertEquals(3, _persistentVariableFactoryValuesWritten.size());
//		assertEquals("value1", _persistentVariableFactoryValuesWritten.get("variable1"));
//		assertEquals("value2", _persistentVariableFactoryValuesWritten.get("variable2"));
//		assertEquals("value3", _persistentVariableFactoryValuesWritten.get("variable3"));
//
//    	// Test whether the output of the PersistentValuesHandler is returned as a string
//    	// NB: Not sure why Collection returns results out-of-order at the moment; this test is brittle because of that behavior, but that is arguably a benefit
//
//		assertEquals("variable1,value1;variable3,value3;variable2,value2", writtenValue);
//    }
}
