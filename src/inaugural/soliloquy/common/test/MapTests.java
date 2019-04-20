package inaugural.soliloquy.common.test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import inaugural.soliloquy.common.Map;
import inaugural.soliloquy.common.test.stubs.MapValidatorStub;
import inaugural.soliloquy.common.test.stubs.PairFactoryStub;
import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.IFunction;
import soliloquy.common.specs.IMap;
import soliloquy.common.specs.IPair;
import soliloquy.common.specs.IPairFactory;

public class MapTests {
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
	
    @SuppressWarnings("unchecked")
	@BeforeEach
    protected void setUp() throws Exception {
    	Mockito.reset();
    	_map = new Map<String,String>(PAIR_FACTORY,FIRST_ARCHETYPE,SECOND_ARCHETYPE);
    	
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
    public void testPutNullOrBlankKey() {
    	try {
    		_map.put(null, "String");
    		assertTrue(false);
    	} catch(IllegalArgumentException e) {
    		assertTrue(true);
    	} catch(Exception e) {
    		assertTrue(false);
    	}
    	try {
    		_map.put("", "String");
    		assertTrue(false);
    	} catch(IllegalArgumentException e) {
    		assertTrue(true);
    	} catch(Exception e) {
    		assertTrue(false);
    	}
    }

    @Test
    public void testPutAndContainsKeyAndValue() {
    	_map.put("Key1", "Value1");
    	assertTrue(_map.containsKey("Key1"));
    	assertTrue(_map.containsValue("Value1"));
    }

    @Test
    public void testGet() {
    	_map.put("Key1", "Value1");
    	assertTrue(_map.get("Key1") == "Value1");
    }

    @Test
    public void testGetExceptions() {
    	try {
    		_map.get("");
    		assertTrue(false);
    	} catch(IllegalArgumentException e) {
    		assertTrue(true);
    	} catch(Exception e) {
    		assertTrue(false);
    	}
    	
    	try {
    		_map.get(null);
    		assertTrue(false);
    	} catch(IllegalArgumentException e) {
    		assertTrue(true);
    	} catch(Exception e) {
    		assertTrue(false);
    	}
    }

    @Test
	public void testPutAll() {
    	_map.putAll(_pairCollectionMock);
    	assertTrue(_map.containsKey(PAIR_1_KEY));
    	assertTrue(_map.containsKey(PAIR_2_KEY));
    }

    @Test
    public void testClear() {
    	_map.put("String1", "String2");
    	_map.clear();
    	assertTrue(_map.isEmpty());
    }

    @Test
	public void testContains() {
    	assertTrue(!_map.contains(_pairMock));
    	_map.put(PAIR_1_KEY, PAIR_1_VALUE);
    	assertTrue(_map.contains(_pairMock));
    }

    @Test
	public void testSize() {
		assertTrue(_map.size() == 0);
		_map.put(PAIR_1_KEY, PAIR_1_VALUE);
		assertTrue(_map.size() == 1);
		_map.clear();
		assertTrue(_map.size() == 0);
		_map.put("String 1", "String 1");
		_map.put("String 2", "String 2");
		_map.put("String 1", "String 2");
		assertTrue(_map.size() == 2);
	}
	
	@SuppressWarnings("unchecked")
    @Test
	public void testEqualsCollection() {
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

    @Test
	public void testEqualsException() {
		try {
			_map.equals((IMap<String,String>)null);
			assertTrue(false);
		} catch(IllegalArgumentException e) {
			assertTrue(true);
		} catch(Exception e) {
			assertTrue(false);
		}
		try {
			_map.equals((ICollection<String>)null);
			assertTrue(false);
		} catch(IllegalArgumentException e) {
			assertTrue(true);
		} catch(Exception e) {
			assertTrue(false);
		}
	}

    @Test
	public void testGetKeys() {
		_map.put("Key1", "Value1");
		_map.put("Key2", "Value2");
		_map.put("Key3", "Value3");
		ICollection<String> ids = _map.getKeys();
		
		String[] expectedIds = new String[] {"Key1", "Key2", "Key3"};
		Boolean[] expectedIdsFound = new Boolean[expectedIds.length];
		for(String id : ids) {
			for(Integer i = 0; i < expectedIds.length; i++) {
				if (id == expectedIds[i]) {
					expectedIdsFound[i] = true;
				}
				continue;
			}
		}
		for(Integer i = 0; i < expectedIdsFound.length; i++) {
			assertTrue(expectedIdsFound[i]);
		}
	}

    @Test
	public void testGetValues() {
		_map.put("Key1", "Value1");
		_map.put("Key2", "Value2");
		_map.put("Key3", "Value3");
		ICollection<String> values = _map.getValues();
		
		String[] expectedValues = new String[] {"Value1", "Value2", "Value3"};
		Boolean[] expectedValuesFound = new Boolean[expectedValues.length];
		for(String value : values) {
			for(Integer i = 0; i < expectedValues.length; i++) {
				if (value == expectedValues[i]) {
					expectedValuesFound[i] = true;
				}
				continue;
			}
		}
		for(Integer i = 0; i < expectedValuesFound.length; i++) {
			assertTrue(expectedValuesFound[i]);
		}
	}

    @Test
	public void testValidator() {
		_map.setValidator(_validatorStub);
		
		try {
			_map.put(PAIR_2_KEY, PAIR_2_VALUE);
			assertTrue(false);
		} catch(IllegalArgumentException e) {
			assertTrue(true);
		} catch(Exception e) {
			assertTrue(false);
		}
		
		try {
			_map.putAll(_pairCollectionMock);
			assertTrue(false);
		} catch(IllegalArgumentException e) {
			assertTrue(e.toString().contains("Input key (Key2) not equal to Key1"));
		} catch(Exception e) {
			assertTrue(false);
		}
	}

    @Test
	public void testEqualsMap() {
		Map<String,String> secondMap = new Map<String,String>(null,"","");
		_map.put("Key1", "Value1");
		assertTrue(!_map.equals(secondMap));
		secondMap.put("Key2", "Value2");
		assertTrue(!_map.equals(secondMap));
		_map.put("Key2", "Value2");
		secondMap.put("Key1", "Value1");
		assertTrue(_map.equals(secondMap));
	}

    @Test
	public void testRemoveByKey() {
		_map.put(PAIR_1_KEY, PAIR_1_VALUE);
		_map.put(PAIR_2_KEY, PAIR_2_VALUE);
		assertTrue(_map.size() == 2);
		
		assertTrue(_map.removeByKey("not-a-key") == null);
		assertTrue(_map.size() == 2);
		assertTrue(_map.removeByKey(PAIR_1_KEY) == PAIR_1_VALUE);
		assertTrue(_map.size() == 1);
	}

    @Test
	public void testRemoveByKeyAndValue() {
		_map.put(PAIR_1_KEY, PAIR_1_VALUE);
		_map.put(PAIR_2_KEY, PAIR_2_VALUE);
		assertTrue(_map.size() == 2);
		
		assertTrue(!_map.removeByKeyAndValue("not-a-key", "not-a-value"));
		assertTrue(_map.size() == 2);
		assertTrue(_map.removeByKeyAndValue(PAIR_1_KEY, PAIR_1_VALUE));
		assertTrue(_map.size() == 1);
	}

    @Test
	public void testItemExists() {
		assertTrue(!_map.itemExists(PAIR_1_KEY));
		
		_map.put(PAIR_1_KEY, PAIR_1_VALUE);
		assertTrue(_map.itemExists(PAIR_1_KEY));
	}

    @Test
	public void testIndicesOf() {
		assertTrue(_map.indicesOf(PAIR_1_VALUE).size() == 0);
		
		_map.put(PAIR_1_KEY, PAIR_1_VALUE);
		
		assertTrue(_map.indicesOf(PAIR_1_VALUE).size() == 1);
		assertTrue(_map.indicesOf(PAIR_1_VALUE).contains(PAIR_1_KEY));
	}

    @Test
	public void testIterator() {
		_map.put(PAIR_1_KEY, PAIR_1_VALUE);
		_map.put(PAIR_2_KEY, PAIR_2_VALUE);
		int counter = 0;
		for (IPair<String,String> pair : _map) {
			if (counter == 0) {
				assertTrue(pair.getItem1().equals(PAIR_2_KEY));
				assertTrue(pair.getItem2().equals(PAIR_2_VALUE));
			}
			if (counter == 1) {
				assertTrue(pair.getItem1().equals(PAIR_1_KEY));
				assertTrue(pair.getItem2().equals(PAIR_1_VALUE));
			}
			counter++;
		}
	}

    @Test
	public void testGetFirstArchetype() {
		assertTrue(FIRST_ARCHETYPE.equals(_map.getFirstArchetype()));
	}

    @Test
	public void testGetSecondArchetype() {
		assertTrue(SECOND_ARCHETYPE.equals(_map.getSecondArchetype()));
	}

    @Test
	public void testGetInterfaceName() {
		assertTrue("soliloquy.common.specs.IMap<java.lang.String,java.lang.String>".equals(_map.getInterfaceName()));
	}

    @Test
	public void testMakeClone() {
		_map.put(PAIR_1_KEY, PAIR_1_VALUE);
		_map.put(PAIR_2_KEY, PAIR_2_VALUE);
		
		IMap<String,String> clonedMap = _map.makeClone();
		assertTrue(clonedMap != null);
		assertTrue(clonedMap.getFirstArchetype() == _map.getFirstArchetype());
		assertTrue(clonedMap.getSecondArchetype() == _map.getSecondArchetype());
		for(IPair<String,String> item : _map) {
			assertTrue(clonedMap.contains(item));
		}
		assertTrue(_map.size() == clonedMap.size());
		assertNotNull(clonedMap.getFirstArchetype());
		assertNotNull(clonedMap.getSecondArchetype());
	}


}
