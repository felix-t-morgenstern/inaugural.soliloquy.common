package inaugural.soliloquy.common.test;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.PersistentVariableCache;
import inaugural.soliloquy.common.test.stubs.CollectionFactoryStub;
import soliloquy.common.specs.*;

import static org.junit.jupiter.api.Assertions.*;

class PersistentVariableCacheTests {
	private PersistentVariableCache _persistentVariableCache;
	
	private static String _persistentValuesHandlerDataRead;
	private static boolean _persistentValuesHandlerBooleanRead;
	private static HashMap<String,Object> _persistentVariableFactoryValuesRead;
	private static HashMap<String,Object> _persistentVariableFactoryValuesWritten;
	
	private final IPersistentVariableFactory PERSISTENT_VARIABLE_FACTORY = new PersistentVariableFactoryStub();
	private final IPersistentValuesHandler PERSISTENT_VALUES_HANDLER = new PersistentValuesHandlerStub();
	private final IPairFactory PAIR_FACTORY = new PairFactoryStub();
	private final ICollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
	
    @BeforeEach
	void setUp() {
    	// PairFactory and archetype not necessary for test suite
    	_persistentVariableCache = new PersistentVariableCache(PAIR_FACTORY, "", null, COLLECTION_FACTORY,
    			PERSISTENT_VARIABLE_FACTORY, PERSISTENT_VALUES_HANDLER);
    	
    	_persistentVariableFactoryValuesRead = new HashMap<>();
    	_persistentVariableFactoryValuesWritten = new HashMap<>();
    }
    
    @Test
	void testPut() {
    	IPersistentVariable persistentVariable1 = PERSISTENT_VARIABLE_FACTORY.make("variable1", "value1");

    	_persistentVariableCache.put(persistentVariable1);

		assertEquals(1, _persistentVariableCache.size());
		assertEquals("value1", _persistentVariableCache.get("variable1").getValue());
    }

    @Test
	void testPutWithInvalidName() {
    	assertThrows(IllegalArgumentException.class, () -> {
			IPersistentVariable persistentVariable = PERSISTENT_VARIABLE_FACTORY.make("name", "value");
			_persistentVariableCache.put("notTheName", persistentVariable);
		});
    }

    @Test
	void testPutWithNullOrEmptyParams() {
		assertThrows(IllegalArgumentException.class, () -> {
			IPersistentVariable persistentVariableWithNoName = PERSISTENT_VARIABLE_FACTORY.make("", "value");
			_persistentVariableCache.put("", persistentVariableWithNoName);
		});
		assertThrows(IllegalArgumentException.class, () -> _persistentVariableCache.put("name", null));
		assertThrows(IllegalArgumentException.class, () -> _persistentVariableCache.put(null));
		assertThrows(IllegalArgumentException.class, () -> {
			IPersistentVariable persistentVariableWithNoValue = PERSISTENT_VARIABLE_FACTORY.make("name", null);
			_persistentVariableCache.put("name", persistentVariableWithNoValue);
		});
    }

    @Test
	void testRead() {
    	String jankyInput = "name1,value1;name2,value2;name3,value3";
    	
    	_persistentVariableCache.read(jankyInput, true);
    	
    	// Test whether data and overridePreviousData are passed through to the PersistentValuesHandler
		assertEquals(_persistentValuesHandlerDataRead, jankyInput);
		assertTrue(_persistentValuesHandlerBooleanRead);
    	
    	// Test whether the output of the PersistentValuesHandler is passed through to the PersistentVariableFactory
		assertEquals(3, _persistentVariableFactoryValuesRead.size());
		assertEquals("value1", _persistentVariableFactoryValuesRead.get("name1"));
		assertEquals("value2", _persistentVariableFactoryValuesRead.get("name2"));
		assertEquals("value3", _persistentVariableFactoryValuesRead.get("name3"));
    	
    	// Test whether the PersistentVariableCache contains the output of the PersistentVariableFactory
		assertEquals(3, _persistentVariableCache.size());
		assertEquals("value1", _persistentVariableCache.get("name1").getValue());
		assertEquals("value2", _persistentVariableCache.get("name2").getValue());
		assertEquals("value3", _persistentVariableCache.get("name3").getValue());
    	
    	// Test whether overridePreviousData works
    	
    	String jankyInput2 = "name3,badValue;name4,value4";
    	
    	_persistentVariableCache.read(jankyInput2, false);

		assertEquals(4, _persistentVariableCache.size());
		assertEquals("value1", _persistentVariableCache.get("name1").getValue());
		assertEquals("value2", _persistentVariableCache.get("name2").getValue());
		assertEquals("value3", _persistentVariableCache.get("name3").getValue());
		assertEquals("value4", _persistentVariableCache.get("name4").getValue());
    	
    	String jankyInput3 = "name1,newValue1;name4,newValue4";
    	
    	_persistentVariableCache.read(jankyInput3, true);

		assertEquals(4, _persistentVariableCache.size());
		assertEquals("newValue1", _persistentVariableCache.get("name1").getValue());
		assertEquals("value2", _persistentVariableCache.get("name2").getValue());
		assertEquals("value3", _persistentVariableCache.get("name3").getValue());
		assertEquals("newValue4", _persistentVariableCache.get("name4").getValue());
    }

    @Test
	void testWrite() {
    	IPersistentVariable persistentVariable1 = PERSISTENT_VARIABLE_FACTORY.make("variable1", "value1");
    	IPersistentVariable persistentVariable2 = PERSISTENT_VARIABLE_FACTORY.make("variable2", "value2");
    	IPersistentVariable persistentVariable3 = PERSISTENT_VARIABLE_FACTORY.make("variable3", "value3");

    	_persistentVariableCache.put(persistentVariable1);
    	_persistentVariableCache.put(persistentVariable2);
    	_persistentVariableCache.put(persistentVariable3);
    	
    	String writtenValue = _persistentVariableCache.write();

    	// Test whether the contents of the PersistentVariableCache are passed to the PersistentValuesHandler

		assertEquals(3, _persistentVariableFactoryValuesWritten.size());
		assertEquals("value1", _persistentVariableFactoryValuesWritten.get("variable1"));
		assertEquals("value2", _persistentVariableFactoryValuesWritten.get("variable2"));
		assertEquals("value3", _persistentVariableFactoryValuesWritten.get("variable3"));
    	
    	// Test whether the output of the PersistentValuesHandler is returned as a string
    	// NB: Not sure why Collection returns results out-of-order at the moment; this test is brittle because of that behavior, but that is arguably a benefit

		assertEquals("variable1,value1;variable3,value3;variable2,value2", writtenValue);
    }
    
    private class PersistentVariableFactoryStub implements IPersistentVariableFactory {

		@Override
		public <T> IPersistentVariable make(String name, T value) {
			_persistentVariableFactoryValuesRead.put(name, value);
			
			return new PersistentVariableStub(name, value);
		}

		@Override
		public String getInterfaceName() {
			// Stub; not implemented
			throw new UnsupportedOperationException();
		}
    	
		private class PersistentVariableStub implements IPersistentVariable {
			private final String NAME;
			private final Object VALUE;
			
			private PersistentVariableStub(String name, Object value) {
				NAME = name;
				VALUE = value;
			}

			@Override
			public String getName() {
				return NAME;
			}

			@SuppressWarnings("unchecked")
			@Override
			public <T> T getValue() {
				return (T) VALUE;
			}

			@Override
			public <T> void setValue(T value) {
				// Stub method; not implemented
				throw new UnsupportedOperationException();
			}

			@Override
			public <T> IPersistentValueToWrite<T> toWriteRepresentation() {
				// Stub method; not implemented
				throw new UnsupportedOperationException();
			}

			@Override
			public String getInterfaceName() {
				// Stub; not implemented
				throw new UnsupportedOperationException();
			}
			
		}
    }
    
    private class PersistentValuesHandlerStub implements IPersistentValuesHandler {
    	private final IPairFactory PAIR_FACTORY = new PairFactoryStub();

		@Override
		public void addPersistentValueTypeHandler(IPersistentValueTypeHandler<?> persistentValueTypeHandler)
				throws IllegalArgumentException {
			// Stub method; not implemented
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removePersistentValueTypeHandler(String persistentValueType) {
			// Stub method; not implemented
			throw new UnsupportedOperationException();
		}

		@Override
		public <T> IPersistentValueTypeHandler<T> getPersistentValueTypeHandler(String persistentValueType)
				throws UnsupportedOperationException {
			// Stub method; not implemented
			throw new UnsupportedOperationException();
		}

		@Override
		public ICollection<String> persistentValueTypesHandled() {
			// Stub method; not implemented
			throw new UnsupportedOperationException();
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public void readValues(String valuesString, IAction<IPair<IPersistentValueToWrite<?>, Boolean>> valueProcessing,
				boolean overridePreviousData) {
			_persistentValuesHandlerDataRead = valuesString;
			_persistentValuesHandlerBooleanRead = overridePreviousData;
			
			String[] nameValuePairs = valuesString.split(";");
			for(String nameValuePair : nameValuePairs) {
				String[] nameAndValue = nameValuePair.split(",");
				IPersistentValueToWrite persistentValueToWrite = new PersistentValueToWrite(null, nameAndValue[0], nameAndValue[1]);
				IPair<IPersistentValueToWrite<?>, Boolean> valueToProcess = PAIR_FACTORY.make(persistentValueToWrite, overridePreviousData);
				valueProcessing.run(valueToProcess);
			}
		}

		@SuppressWarnings("rawtypes")
		@Override
		public String writeValues(ICollection<IPersistentValueToWrite<?>> persistentValuesToProcess) {
			StringBuilder result = new StringBuilder();
			for (IPersistentValueToWrite persistentValueToWrite : persistentValuesToProcess) {
				_persistentVariableFactoryValuesWritten.put(persistentValueToWrite.name(), persistentValueToWrite.value());
				if (!result.toString().equals("")) {
					result.append(";");
				}
				result.append(persistentValueToWrite.name()).append(",").append(persistentValueToWrite.value());
			}
			return result.toString();
		}

		@Override
		public IPersistentValueToRead makePersistentValueToRead(String typeName, String name, String value) {
			// Stub method; not implemented
			throw new UnsupportedOperationException();
		}

		@Override
		public <T> IPersistentValueToWrite<T> makePersistentValueToWrite(String name, T value) {
			// Stub method; not implemented
			throw new UnsupportedOperationException();
		}

		@Override
		public void registerPersistentCollectionHandler(IPersistentValueTypeHandler<ICollection> iPersistentValueTypeHandler) {
			// Stub method; not implemented
			throw new UnsupportedOperationException();
		}

		@Override
		public void registerPersistentMapHandler(IPersistentValueTypeHandler<IMap> iPersistentValueTypeHandler) {
			// Stub method; not implemented
			throw new UnsupportedOperationException();
		}

		@Override
		public String getInterfaceName() {
			// Stub; not implemented
			throw new UnsupportedOperationException();
		}
    	
    }
	
	private class PairFactoryStub implements IPairFactory
	{

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public <T1, T2> IPair<T1, T2> make(T1 item1, T2 item2) throws IllegalArgumentException {
			return new PairStub(item1, item2);
		}

		@Override
		public <T1, T2> IPair<T1, T2> make(T1 item1, T2 item2, T1 archetype1, T2 archetype2)
				throws IllegalArgumentException {
			// Stub class; not implemented
			throw new UnsupportedOperationException();
		}

		@Override
		public String getInterfaceName() {
			// Stub; not implemented
			throw new UnsupportedOperationException();
		}
		
		private class PairStub<K,V> implements IPair<K,V>
		{
			private K _item1;
			private V _item2;
			
			private PairStub(K item1, V item2) {
				_item1 = item1;
				_item2 = item2;
			}

			@Override
			public K getFirstArchetype() throws IllegalStateException {
				// Stub method, unimplemented
				throw new UnsupportedOperationException();
			}

			@Override
			public V getSecondArchetype() throws IllegalStateException {
				// Stub method, unimplemented
				throw new UnsupportedOperationException();
			}

			@Override
			public String getInterfaceName() {
				// Stub method, unimplemented
				throw new UnsupportedOperationException();
			}

			@Override
			public K getItem1() {
				return _item1;
			}

			@Override
			public void setItem1(K item) throws IllegalArgumentException {
				_item1 = item;
			}

			@Override
			public V getItem2() {
				return _item2;
			}

			@Override
			public void setItem2(V item) throws IllegalArgumentException {
				_item2 = item;
			}

			@Override
			public String getUnparameterizedInterfaceName() {
				// Stub method; unimplemented
				throw new UnsupportedOperationException();
			}
			
		}
	}
	
	private class PersistentValueToWrite<T> implements IPersistentValueToWrite<T>
    {
    	private final String TYPE_NAME;
    	private final String NAME;
    	private final T VALUE;
    	
    	private PersistentValueToWrite(String typeName, String name, T value) {
    		TYPE_NAME = typeName;
    		NAME = name;
    		VALUE = value;
    	}

		@Override
		public T getArchetype() {
			// Stub method
			throw new UnsupportedOperationException();
		}

		@Override
		public String getInterfaceName() {
			// Stub method
			throw new UnsupportedOperationException();
		}

		@Override
		public String typeName() {
			return TYPE_NAME;
		}

		@Override
		public String name() {
			return NAME;
		}

		@Override
		public T value() {
			return VALUE;
		}

		@Override
		public String getUnparameterizedInterfaceName() {
			// Stub method; unimplemented
			throw new UnsupportedOperationException();
		}
    	
    }
}
