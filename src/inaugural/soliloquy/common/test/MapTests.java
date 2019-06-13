package inaugural.soliloquy.common.test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import inaugural.soliloquy.common.test.stubs.CollectionFactoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import inaugural.soliloquy.common.Map;
import inaugural.soliloquy.common.test.stubs.MapValidatorStub;
import inaugural.soliloquy.common.test.stubs.PairFactoryStub;
import soliloquy.common.specs.*;

class MapTests {
	private Map<String,String> _map;
	private IPair<String,String> _pairMock;
	private IPair<String,String> _pairMock2;
	private IFunction<IPair<String,String>,String> _validatorStub;
	private ICollection<IPair<String,String>> _pairCollectionMock;
	private Iterator<IPair<String,String>> _pairCollectionIteratorMock;
	
	private final String PAIR_1_KEY = "Key1";
	private final String PAIR_1_VALUE = "Value1";
	private final String PAIR_2_KEY = "Key2";
	private final String PAIR_2_VALUE = "Value2";
	private final String FIRST_ARCHETYPE = "FIRST_ARCHETYPE";
	private final String SECOND_ARCHETYPE = "SECOND_ARCHETYPE";
	
	private final IPairFactory PAIR_FACTORY = new PairFactoryStub();
	private final ICollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
	
    @SuppressWarnings("unchecked")
	@BeforeEach
	void setUp() {
    	Mockito.reset();
    	_map = new Map<>(PAIR_FACTORY, FIRST_ARCHETYPE, SECOND_ARCHETYPE, COLLECTION_FACTORY);
    	
		_pairMock = mock(IPair.class);
    	when(_pairMock.getItem1()).thenReturn(PAIR_1_KEY);
    	when(_pairMock.getItem2()).thenReturn(PAIR_1_VALUE);
    	
		_pairMock2 = mock(IPair.class);
    	when(_pairMock2.getItem1()).thenReturn(PAIR_2_KEY);
    	when(_pairMock2.getItem2()).thenReturn(PAIR_2_VALUE);
    	
    	_validatorStub = new MapValidatorStub();
    	
    	_pairCollectionIteratorMock = mock(Iterator.class);
    	when(_pairCollectionIteratorMock.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);
    	when(_pairCollectionIteratorMock.next()).thenReturn(_pairMock).thenReturn(_pairMock2);
    	
    	_pairCollectionMock = mock(ICollection.class);
    	when(_pairCollectionMock.iterator()).thenReturn(_pairCollectionIteratorMock);
    }

    @Test
	void testPutNullOrBlankKey() {
		assertThrows(IllegalArgumentException.class, () -> _map.put(null, "String"));
		assertThrows(IllegalArgumentException.class, () -> _map.put("", "String"));
    }

    @Test
	void testPutAndContainsKeyAndValue() {
    	_map.put("Key1", "Value1");
    	assertTrue(_map.containsKey("Key1"));
    	assertTrue(_map.containsValue("Value1"));
    }

    @Test
	void testGet() {
    	_map.put("Key1", "Value1");
		assertSame("Value1", _map.get("Key1"));
    }

    @Test
	void testGetExceptions() {
		assertThrows(IllegalArgumentException.class, () -> _map.get(""));
		assertThrows(IllegalArgumentException.class, () -> _map.get(null));
    }

    @Test
	void testPutAll() {
    	_map.putAll(_pairCollectionMock);
    	assertTrue(_map.containsKey(PAIR_1_KEY));
    	assertTrue(_map.containsKey(PAIR_2_KEY));
    }

    @Test
	void testClear() {
    	_map.put("String1", "String2");
    	_map.clear();
    	assertTrue(_map.isEmpty());
    }

    @Test
	void testContains() {
    	assertTrue(!_map.contains(_pairMock));
    	_map.put(PAIR_1_KEY, PAIR_1_VALUE);
    	assertTrue(_map.contains(_pairMock));
    }

    @Test
	void testSize() {
		assertEquals(0, _map.size());
		_map.put(PAIR_1_KEY, PAIR_1_VALUE);
		assertEquals(1, _map.size());
		_map.clear();
		assertEquals(0, _map.size());
		_map.put("String 1", "String 1");
		_map.put("String 2", "String 2");
		_map.put("String 1", "String 2");
		assertEquals(2, _map.size());
	}
	
	@SuppressWarnings("unchecked")
    @Test
	void testEqualsCollection() {
		Iterator<String> stringsIteratorMock = mock(Iterator.class);
		when(stringsIteratorMock.hasNext()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
		when(stringsIteratorMock.next()).thenReturn("String1").thenReturn("String2").thenReturn("String3");
		
		ICollection<String> stringsMock = mock(ICollection.class);
		when(stringsMock.iterator()).thenReturn(stringsIteratorMock);
		when(stringsMock.size()).thenReturn(3);

		_map.put("String1", "String1");
		assertTrue(!_map.equals(stringsMock));
		_map.put("String2", "String2");
		assertTrue(!_map.equals(stringsMock));
		_map.put("String3", "String3");
		assertTrue(_map.equals(stringsMock));
		
		_map.clear();

		Iterator<String> stringsIteratorMock_2 = mock(Iterator.class);
		when(stringsIteratorMock_2.hasNext()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
		when(stringsIteratorMock_2.next()).thenReturn("String1").thenReturn("String2").thenReturn("String3");
		
		ICollection<String> stringsMock_2 = mock(ICollection.class);
		when(stringsMock_2.iterator()).thenReturn(stringsIteratorMock_2);
		when(stringsMock_2.size()).thenReturn(3);
		
		_map.put("String1", "String1");
		assertTrue(!_map.equals(stringsMock_2));
		_map.put("String2", "String2");
		assertTrue(!_map.equals(stringsMock_2));
		_map.put("String3", "String4");
		assertTrue(!_map.equals(stringsMock_2));
	}

    @SuppressWarnings("ResultOfMethodCallIgnored")
	@Test
	void testEqualsException() {
		assertThrows(IllegalArgumentException.class, () -> _map.equals((IMap<String,String>)null));
		assertThrows(IllegalArgumentException.class, () -> _map.equals((ICollection<String>)null));
	}

    @SuppressWarnings("StringEquality")
	@Test
	void testGetKeys() {
		_map.put("Key1", "Value1");
		_map.put("Key2", "Value2");
		_map.put("Key3", "Value3");
		ICollection<String> ids = _map.getKeys();
		
		String[] expectedIds = new String[] {"Key1", "Key2", "Key3"};
		Boolean[] expectedIdsFound = new Boolean[expectedIds.length];
		for(String id : ids) {
			for(int i = 0; i < expectedIds.length; i++) {
				if (id == expectedIds[i]) {
					expectedIdsFound[i] = true;
				}
			}
		}
		for (Boolean aBoolean : expectedIdsFound) {
			assertTrue(aBoolean);
		}
	}

    @SuppressWarnings("StringEquality")
	@Test
	void testGetValues() {
		_map.put("Key1", "Value1");
		_map.put("Key2", "Value2");
		_map.put("Key3", "Value3");
		ICollection<String> values = _map.getValues();
		
		String[] expectedValues = new String[] {"Value1", "Value2", "Value3"};
		Boolean[] expectedValuesFound = new Boolean[expectedValues.length];
		for(String value : values) {
			for(int i = 0; i < expectedValues.length; i++) {
				if (value == expectedValues[i]) {
					expectedValuesFound[i] = true;
				}
			}
		}
		for (Boolean aBoolean : expectedValuesFound) {
			assertTrue(aBoolean);
		}
	}

    @Test
	void testValidators() {
		_map.validators().add(_validatorStub);

		assertThrows(IllegalArgumentException.class, () -> _map.put(PAIR_2_KEY, PAIR_2_VALUE));
		assertThrows(IllegalArgumentException.class, () -> _map.putAll(_pairCollectionMock));
	}

    @Test
	void testEqualsMap() {
		Map<String,String> secondMap = new Map<>(PAIR_FACTORY, "", "", COLLECTION_FACTORY);
		_map.put("Key1", "Value1");
		assertTrue(!_map.equals(secondMap));
		secondMap.put("Key2", "Value2");
		assertTrue(!_map.equals(secondMap));
		_map.put("Key2", "Value2");
		secondMap.put("Key1", "Value1");
		assertTrue(_map.equals(secondMap));
	}

    @Test
	void testRemoveByKey() {
		_map.put(PAIR_1_KEY, PAIR_1_VALUE);
		_map.put(PAIR_2_KEY, PAIR_2_VALUE);
		assertEquals(2, _map.size());

		assertNull(_map.removeByKey("not-a-key"));
		assertEquals(2, _map.size());
		assertSame(_map.removeByKey(PAIR_1_KEY), PAIR_1_VALUE);
		assertEquals(1, _map.size());
	}

    @Test
	void testRemoveByKeyAndValue() {
		_map.put(PAIR_1_KEY, PAIR_1_VALUE);
		_map.put(PAIR_2_KEY, PAIR_2_VALUE);
		assertEquals(2, _map.size());
		
		assertTrue(!_map.removeByKeyAndValue("not-a-key", "not-a-value"));
		assertEquals(2, _map.size());
		assertTrue(_map.removeByKeyAndValue(PAIR_1_KEY, PAIR_1_VALUE));
		assertEquals(1, _map.size());
	}

    @Test
	void testItemExists() {
		assertTrue(!_map.itemExists(PAIR_1_KEY));
		
		_map.put(PAIR_1_KEY, PAIR_1_VALUE);
		assertTrue(_map.itemExists(PAIR_1_KEY));
	}

    @Test
	void testIndicesOf() {
		assertEquals(0, _map.indicesOf(PAIR_1_VALUE).size());
		
		_map.put(PAIR_1_KEY, PAIR_1_VALUE);

		assertEquals(1, _map.indicesOf(PAIR_1_VALUE).size());
		assertTrue(_map.indicesOf(PAIR_1_VALUE).contains(PAIR_1_KEY));
	}

    @Test
	void testIterator() {
		_map.put(PAIR_1_KEY, PAIR_1_VALUE);
		_map.put(PAIR_2_KEY, PAIR_2_VALUE);
		int counter = 0;
		for (IPair<String,String> pair : _map) {
			if (counter == 0) {
				assertEquals(pair.getItem1(), PAIR_2_KEY);
				assertEquals(pair.getItem2(), PAIR_2_VALUE);
			}
			if (counter == 1) {
				assertEquals(pair.getItem1(), PAIR_1_KEY);
				assertEquals(pair.getItem2(), PAIR_1_VALUE);
			}
			counter++;
		}
	}

    @Test
	void testGetFirstArchetype() {
		assertEquals(FIRST_ARCHETYPE, _map.getFirstArchetype());
	}

    @Test
	void testGetSecondArchetype() {
		assertEquals(SECOND_ARCHETYPE, _map.getSecondArchetype());
	}

    @Test
	void testGetInterfaceName() {
		assertEquals(IMap.class.getCanonicalName() + "<java.lang.String,java.lang.String>",
				_map.getInterfaceName());
	}

    @Test
	void testMakeClone() {
		_map.put(PAIR_1_KEY, PAIR_1_VALUE);
		_map.put(PAIR_2_KEY, PAIR_2_VALUE);
		
		IMap<String,String> clonedMap = _map.makeClone();
		assertNotNull(clonedMap);
		assertSame(clonedMap.getFirstArchetype(), _map.getFirstArchetype());
		assertSame(clonedMap.getSecondArchetype(), _map.getSecondArchetype());
		for(IPair<String,String> item : _map) {
			assertTrue(clonedMap.contains(item));
		}
		assertEquals(_map.size(), clonedMap.size());
		assertNotNull(clonedMap.getFirstArchetype());
		assertNotNull(clonedMap.getSecondArchetype());
	}


}
