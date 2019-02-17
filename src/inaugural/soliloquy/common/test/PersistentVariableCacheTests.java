package inaugural.soliloquy.common.test;

import java.util.HashMap;

import inaugural.soliloquy.common.PersistentVariableCache;
import inaugural.soliloquy.common.test.Stubs.CollectionFactoryStub;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import soliloquy.common.specs.IAction;
import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.ICollectionFactory;
import soliloquy.common.specs.IPair;
import soliloquy.common.specs.IPairFactory;
import soliloquy.common.specs.IPersistentValueToRead;
import soliloquy.common.specs.IPersistentValueToWrite;
import soliloquy.common.specs.IPersistentValueTypeHandler;
import soliloquy.common.specs.IPersistentValuesHandler;
import soliloquy.common.specs.IPersistentVariable;
import soliloquy.common.specs.IPersistentVariableFactory;

public class PersistentVariableCacheTests extends TestCase {
	private PersistentVariableCache _persistentVariableCache;
	
	private static String _persistentValuesHandlerDataRead;
	private static boolean _persistentValuesHandlerBooleanRead;
	private static HashMap<String,Object> _persistentVariableFactoryValuesRead;
	private static HashMap<String,Object> _persistentVariableFactoryValuesWritten;
	
	private final IPersistentVariableFactory PERSISTENT_VARIABLE_FACTORY = new PersistentVariableFactoryStub();
	private final IPersistentValuesHandler PERSISTENT_VALUES_HANDLER = new PersistentValuesHandlerStub();
	private final IPairFactory PAIR_FACTORY = new PairFactoryStub();
	private final ICollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PersistentVariableCacheTests( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( PersistentVariableCacheTests.class );
    }
    
    @Override
    protected void setUp() throws Exception
    {
    	// PairFactory and archetype not necessary for test suite
    	_persistentVariableCache = new PersistentVariableCache(PAIR_FACTORY, "", null, COLLECTION_FACTORY,
    			PERSISTENT_VARIABLE_FACTORY, PERSISTENT_VALUES_HANDLER);
    	
    	_persistentVariableFactoryValuesRead = new HashMap<String,Object>();
    	_persistentVariableFactoryValuesWritten = new HashMap<String,Object>();
    }
    
    public void testPut()
    {
    	IPersistentVariable persistentVariable1 = PERSISTENT_VARIABLE_FACTORY.make("variable1", "value1");

    	_persistentVariableCache.put(persistentVariable1);
    	
    	assertTrue(_persistentVariableCache.size() == 1);
    	assertTrue(_persistentVariableCache.get("variable1").getValue().equals("value1"));
    }
    
    public void testPutWithInvalidName()
    {
    	try
    	{
    		IPersistentVariable persistentVariable = PERSISTENT_VARIABLE_FACTORY.make("name", "value");
    		_persistentVariableCache.put("notTheName", persistentVariable);
    		assertTrue(false);
    	}
    	catch (IllegalArgumentException e)
    	{
    		assertTrue(true);    		
    	}
    	catch (Exception e)
    	{
    		assertTrue(false);
    	}
    }
    
    public void testPutWithNullOrEmptyParams()
    {
    	try
    	{
    		IPersistentVariable persistentVariableWithNoName = PERSISTENT_VARIABLE_FACTORY.make("", "value");
    		_persistentVariableCache.put("", persistentVariableWithNoName);
    		assertTrue(false);
    	}
    	catch (IllegalArgumentException e)
    	{
    		assertTrue(true);    		
    	}
    	catch (Exception e)
    	{
    		assertTrue(false);
    	}
    	
    	try
    	{
    		_persistentVariableCache.put("name", null);
    		assertTrue(false);
    	}
    	catch (IllegalArgumentException e)
    	{
    		assertTrue(true);    		
    	}
    	catch (Exception e)
    	{
    		assertTrue(false);
    	}
    	
    	try
    	{
    		_persistentVariableCache.put(null);
    		assertTrue(false);
    	}
    	catch (IllegalArgumentException e)
    	{
    		assertTrue(true);    		
    	}
    	catch (Exception e)
    	{
    		assertTrue(false);
    	}
    	
    	try
    	{
    		IPersistentVariable persistentVariableWithNoValue = PERSISTENT_VARIABLE_FACTORY.make("name", null);
    		_persistentVariableCache.put("name", persistentVariableWithNoValue);
    		assertTrue(false);
    	}
    	catch (IllegalArgumentException e)
    	{
    		assertTrue(true);    		
    	}
    	catch (Exception e)
    	{
    		assertTrue(false);
    	}
    }
    
    public void testRead()
    {
    	String jankyInput = "name1,value1;name2,value2;name3,value3";
    	
    	_persistentVariableCache.read(jankyInput, true);
    	
    	// Test whether data and overridePreviousData are passed through to the PersistentValuesHandler
    	assertTrue(_persistentValuesHandlerDataRead.equals(jankyInput));
    	assertTrue(_persistentValuesHandlerBooleanRead == true);
    	
    	// Test whether the output of the PersistentValuesHandler is passed through to the PersistentVariableFactory
    	assertTrue(_persistentVariableFactoryValuesRead.size() == 3);
    	assertTrue(_persistentVariableFactoryValuesRead.get("name1").equals("value1"));
    	assertTrue(_persistentVariableFactoryValuesRead.get("name2").equals("value2"));
    	assertTrue(_persistentVariableFactoryValuesRead.get("name3").equals("value3"));
    	
    	// Test whether the PersistentVariableCache contains the output of the PersistentVariableFactory
    	assertTrue(_persistentVariableCache.size() == 3);
    	assertTrue(_persistentVariableCache.get("name1").getValue().equals("value1"));
    	assertTrue(_persistentVariableCache.get("name2").getValue().equals("value2"));
    	assertTrue(_persistentVariableCache.get("name3").getValue().equals("value3"));
    	
    	// Test whether overridePreviousData works
    	
    	String jankyInput2 = "name3,badValue;name4,value4";
    	
    	_persistentVariableCache.read(jankyInput2, false);
    	
    	assertTrue(_persistentVariableCache.size() == 4);
    	assertTrue(_persistentVariableCache.get("name1").getValue().equals("value1"));
    	assertTrue(_persistentVariableCache.get("name2").getValue().equals("value2"));
    	assertTrue(_persistentVariableCache.get("name3").getValue().equals("value3"));
    	assertTrue(_persistentVariableCache.get("name4").getValue().equals("value4"));
    	
    	String jankyInput3 = "name1,newValue1;name4,newValue4";
    	
    	_persistentVariableCache.read(jankyInput3, true);
    	
    	assertTrue(_persistentVariableCache.size() == 4);
    	assertTrue(_persistentVariableCache.get("name1").getValue().equals("newValue1"));
    	assertTrue(_persistentVariableCache.get("name2").getValue().equals("value2"));
    	assertTrue(_persistentVariableCache.get("name3").getValue().equals("value3"));
    	assertTrue(_persistentVariableCache.get("name4").getValue().equals("newValue4"));
    }
    
    
    
    public void testWrite()
    {
    	IPersistentVariable persistentVariable1 = PERSISTENT_VARIABLE_FACTORY.make("variable1", "value1");
    	IPersistentVariable persistentVariable2 = PERSISTENT_VARIABLE_FACTORY.make("variable2", "value2");
    	IPersistentVariable persistentVariable3 = PERSISTENT_VARIABLE_FACTORY.make("variable3", "value3");

    	_persistentVariableCache.put(persistentVariable1);
    	_persistentVariableCache.put(persistentVariable2);
    	_persistentVariableCache.put(persistentVariable3);
    	
    	String writtenValue = _persistentVariableCache.write();

    	// Test whether the contents of the PersistentVariableCache are passed to the PersistentValuesHandler
    	
    	assertTrue(_persistentVariableFactoryValuesWritten.size() == 3);
    	assertTrue(_persistentVariableFactoryValuesWritten.get("variable1").equals("value1"));
    	assertTrue(_persistentVariableFactoryValuesWritten.get("variable2").equals("value2"));
    	assertTrue(_persistentVariableFactoryValuesWritten.get("variable3").equals("value3"));
    	
    	// Test whether the output of the PersistentValuesHandler is returned as a string
    	// NB: Not sure why Collection returns results out-of-order at the moment; this test is brittle because of that behavior, but that is arguably a benefit
    	
    	assertTrue(writtenValue.equals("variable1,value1;variable3,value3;variable2,value2"));
    }
    
    private class PersistentVariableFactoryStub implements IPersistentVariableFactory
    {

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
    	
		private class PersistentVariableStub implements IPersistentVariable
		{
			private final String NAME;
			private final Object VALUE;
			
			private PersistentVariableStub(String name, Object value)
			{
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
    
    private class PersistentValuesHandlerStub implements IPersistentValuesHandler
    {
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
			for(String nameValuePair : nameValuePairs)
			{
				String[] nameAndValue = nameValuePair.split(",");
				IPersistentValueToWrite persistentValueToWrite = new PersistentValueToWrite(null, nameAndValue[0], nameAndValue[1]);
				IPair<IPersistentValueToWrite<?>, Boolean> valueToProcess = PAIR_FACTORY.make(persistentValueToWrite, overridePreviousData);
				valueProcessing.run(valueToProcess);
			}
		}

		@SuppressWarnings("rawtypes")
		@Override
		public String writeValues(ICollection<IPersistentValueToWrite<?>> persistentValuesToProcess) {
			String result = "";
			for (IPersistentValueToWrite persistentValueToWrite : persistentValuesToProcess)
			{
				_persistentVariableFactoryValuesWritten.put(persistentValueToWrite.name(), persistentValueToWrite.value());
				if (!result.equals(""))
				{
					result += ";";
				}
				result += persistentValueToWrite.name() + "," + persistentValueToWrite.value();
			}
			return result;
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
			
			private PairStub(K item1, V item2)
			{
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
    	
    	private PersistentValueToWrite(String typeName, String name, T value)
    	{
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
