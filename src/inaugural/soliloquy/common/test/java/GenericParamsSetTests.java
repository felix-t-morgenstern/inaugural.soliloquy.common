package inaugural.soliloquy.common.test.java;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import inaugural.soliloquy.common.GenericParamsSet;
import inaugural.soliloquy.common.test.java.Stubs.GenericParamsSetPersistentValuesHandlerStub;
import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.IFunction;
import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.common.specs.IMap;
import soliloquy.common.specs.IMapFactory;
import soliloquy.common.specs.IPair;

public class GenericParamsSetTests extends TestCase {
	private GenericParamsSet _genericParamsSet;
	private IMap<String,Integer> _mapIntMock;
	private IMap<String,Integer> _mapIntMock2;
	private IMap<String,Boolean> _mapBoolMock;

	private final String INT_PARAM_1_NAME = "Int_param_1";
	private final Integer INT_PARAM_1_VALUE = 123;
	private final String INT_PARAM_2_NAME = "Int_param_2";
	private final Integer INT_PARAM_2_VALUE = 456;

	private final String STR_PARAM_1_NAME = "Str_param_1";
	private final String STR_PARAM_1_VALUE = "Str_param_1_val";
	private final String STR_PARAM_2_NAME = "Str_param_2";
	private final String STR_PARAM_2_VALUE = "Str_param_2_val";
	
	private final IMapFactory MAP_FACTORY = new MapFactoryStub();

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public GenericParamsSetTests( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( GenericParamsSetTests.class );
    }
    
    @SuppressWarnings("unchecked")
	@Override
    protected void setUp() throws Exception
    {
    	_mapIntMock = (IMap<String,Integer>) mock(IMap.class);
    	when(_mapIntMock.get("String")).thenReturn(123);
    	_mapIntMock2 = (IMap<String,Integer>) mock(IMap.class);
    	when(_mapIntMock2.get("String")).thenReturn(456);
    	_mapBoolMock = (IMap<String,Boolean>) mock(IMap.class);
    	when(_mapBoolMock.get("String")).thenReturn(true);
    	
    	_genericParamsSet = new GenericParamsSet(new GenericParamsSetPersistentValuesHandlerStub(), MAP_FACTORY);
    }
    
    public void testAddAndGetParamAndParamExists()
    {
    	assertTrue(_genericParamsSet.getParam(Integer.class.getCanonicalName(), INT_PARAM_1_NAME) == null);
    	assertTrue(!_genericParamsSet.paramExists(Integer.class.getCanonicalName(), INT_PARAM_1_NAME));
    	
    	_genericParamsSet.addParam(INT_PARAM_1_NAME, INT_PARAM_1_VALUE);

    	assertTrue(_genericParamsSet.paramExists(Integer.class.getCanonicalName(), INT_PARAM_1_NAME));
    	assertTrue(_genericParamsSet.getParam(Integer.class.getCanonicalName(), INT_PARAM_1_NAME) == INT_PARAM_1_VALUE);
    }
    
    public void testAddParamWithNullValue()
    {
    	try
    	{
    		_genericParamsSet.addParam(INT_PARAM_1_NAME, null);
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
    
    public void testAddParamWithNullArchetype()
    {
    	try
    	{
    		_genericParamsSet.addParam(INT_PARAM_1_NAME, INT_PARAM_1_VALUE, null);
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
    
    public void testRemoveParam()
    {
    	assertTrue(!_genericParamsSet.removeParam(Integer.class.getCanonicalName(), INT_PARAM_1_NAME));
    	_genericParamsSet.addParam(INT_PARAM_1_NAME, INT_PARAM_1_VALUE);
    	assertTrue(_genericParamsSet.paramExists(Integer.class.getCanonicalName(), INT_PARAM_1_NAME));
    	
    	assertTrue(_genericParamsSet.removeParam(Integer.class.getCanonicalName(), INT_PARAM_1_NAME));
    	assertTrue(!_genericParamsSet.paramExists(Integer.class.getCanonicalName(), INT_PARAM_1_NAME));
    }
    
    public void testAddParamsSet()
    {
    	assertTrue(_genericParamsSet.getParamsSet(Integer.class.getCanonicalName()) == null);
    	_genericParamsSet.addParamsSet(_mapIntMock, 0);
    	assertTrue((IMap<String,Integer>)_genericParamsSet.<Integer>getParamsSet(Integer.class.getCanonicalName()) == _mapIntMock);
    }
    
    public void testAddNullParamsSet()
    {
    	try
    	{
    		_genericParamsSet.addParamsSet(null, 0);
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
    
    public void testAddParamsSetWithNullArchetype()
    {
    	try
    	{
    		_genericParamsSet.addParamsSet(_mapIntMock, null);
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
    
    public void testGetNullParamsSet()
    {
    	IMap<String,Integer> paramsSet = _genericParamsSet.getParamsSet(Integer.class.getCanonicalName());
    	assertTrue(paramsSet == null);
    }
    
    public void testAddThenGetParamsSet()
    {
    	assertTrue(_genericParamsSet.getParamsSet(Integer.class.getCanonicalName()) == null);
    	_genericParamsSet.addParamsSet(_mapIntMock, 0);
    	IMap<String,Integer> paramsSet = _genericParamsSet.getParamsSet(Integer.class.getCanonicalName());
    	assertTrue(paramsSet != null);
    	assertTrue(paramsSet.get("String") == 123);
    }
    
    public void testAddParamsSetTwice()
    {
    	try
    	{
    		_genericParamsSet.addParamsSet(_mapIntMock, 0);
    		_genericParamsSet.addParamsSet(_mapIntMock2, 0);
    		assertTrue(false);
    	}
    	catch(UnsupportedOperationException e)
    	{
    		assertTrue(true);
    	}
    	catch(Exception e)
    	{
    		assertTrue(false);
    	}
    }
    
    public void testParamTypes()
    {
    	ICollection<String> paramTypes = _genericParamsSet.paramTypes();
    	assertTrue(paramTypes.isEmpty());
    	
    	_genericParamsSet.addParamsSet(_mapIntMock, 0);
    	_genericParamsSet.addParamsSet(_mapBoolMock, false);
    	
    	paramTypes = _genericParamsSet.paramTypes();
    	assertTrue(!paramTypes.isEmpty());
    	assertTrue(paramTypes.size() == 2);
    	assertTrue(paramTypes.contains(Integer.class.getCanonicalName()));
    	assertTrue(paramTypes.contains(Integer.class.getCanonicalName()));
    }
    
    public void testMakeClone()
    {
    	_genericParamsSet.addParam("Int_Param_1",1);
    	_genericParamsSet.addParam("Int_Param_2",2);
    	_genericParamsSet.addParam("Bool_Param_1",true);
    	_genericParamsSet.addParam("Bool_Param_2",false);
    	
    	IGenericParamsSet cloned = _genericParamsSet.makeClone();
    	
    	assertTrue(cloned.paramTypes().size() == 2);
    	
    	IMap<String,Integer> intParams = cloned.getParamsSet(Integer.class.getCanonicalName());
    	assertTrue(intParams.size() == 2);
    	assertTrue(intParams.get("Int_Param_1") == 1);
    	assertTrue(intParams.get("Int_Param_2") == 2);
    	
    	IMap<String,Boolean> boolParams = cloned.getParamsSet(Boolean.class.getCanonicalName());
    	assertTrue(boolParams.size() == 2);
    	assertTrue(boolParams.get("Bool_Param_1") == true);
    	assertTrue(boolParams.get("Bool_Param_2") == false);
    }
    
    public void testRead()
    {
    	assertTrue(_genericParamsSet.getParam(String.class.getCanonicalName(), "DummyValue") != "This is just some data");
    	_genericParamsSet.read("This is just some data", false);
    	try
    	{
        	_genericParamsSet.read("This is just some data", false);
    		assertTrue(false);
    	}
    	catch(IllegalArgumentException e)
    	{
    		assertTrue(true);
    	}
    	catch(Exception e)
    	{
    		assertTrue(false);
    	}
    	assertTrue(_genericParamsSet.getParam(String.class.getCanonicalName(), "DummyValue") == "This is just some data");
    	_genericParamsSet.read("This is just some more data", true);
    	assertTrue(_genericParamsSet.getParam(String.class.getCanonicalName(), "DummyValue") == "This is just some more data");
    }
    
    public void testWrite()
    {
    	assertTrue(_genericParamsSet.write() == "");

    	_genericParamsSet.addParam(INT_PARAM_1_NAME, INT_PARAM_1_VALUE);
    	_genericParamsSet.addParam(INT_PARAM_2_NAME, INT_PARAM_2_VALUE);
    	_genericParamsSet.addParam(STR_PARAM_1_NAME, STR_PARAM_1_VALUE);
    	_genericParamsSet.addParam(STR_PARAM_2_NAME, STR_PARAM_2_VALUE);

    	// No idea why strings are reversed and ints are not.
    	assertTrue(_genericParamsSet.write().equals("Name:"+STR_PARAM_2_NAME+",Value:"+STR_PARAM_2_VALUE+";"+ 
    			"Name:"+STR_PARAM_1_NAME+",Value:"+STR_PARAM_1_VALUE+";" +
    			"Name:"+INT_PARAM_1_NAME+",Value:"+INT_PARAM_1_VALUE+";" + 
    			"Name:"+INT_PARAM_2_NAME+",Value:"+INT_PARAM_2_VALUE+";"));
    }
    
    private class MapFactoryStub implements IMapFactory
    {

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public <K, V> IMap<K, V> make(K archetype1, V archetype2) {
			return new MapStub();
		}    	
    	
    	private class MapStub<K,V> implements IMap<K,V>
    	{
    		private HashMap<K,V> _map = new HashMap<K,V>();

			@Override
			public Iterator<IPair<K, V>> iterator() {
				// Stub method; unimplemented
				throw new UnsupportedOperationException();
			}

			@Override
			public K getFirstArchetype() throws IllegalStateException {
				// Stub method; unimplemented
				throw new UnsupportedOperationException();
			}

			@Override
			public V getSecondArchetype() throws IllegalStateException {
				// Stub method; unimplemented
				throw new UnsupportedOperationException();
			}

			@Override
			public String getParameterizedClassName() {
				// Stub method; unimplemented
				throw new UnsupportedOperationException();
			}

			@Override
			public IMap<K, V> makeClone() {
				// Stub method; unimplemented
				throw new UnsupportedOperationException();
			}

			@Override
			public void clear() {
				// Stub method; unimplemented
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean containsKey(K key) {
				return _map.containsKey(key);
			}

			@Override
			public boolean containsValue(V value) {
				// Stub method; unimplemented
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean contains(IPair<K, V> item) throws IllegalArgumentException {
				// Stub method; unimplemented
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean equals(ICollection<V> items) throws IllegalArgumentException {
				// Stub method; unimplemented
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean equals(IMap<K, V> map) throws IllegalArgumentException {
				// Stub method; unimplemented
				throw new UnsupportedOperationException();
			}

			@Override
			public V get(K key) throws IllegalArgumentException, IllegalStateException {
				return _map.get(key);
			}

			@Override
			public ICollection<K> getKeys() {
				ICollection<K> keys = new CollectionStub<K>();
				for (K key : _map.keySet())
				{
					keys.add(key);
				}
				return keys;
			}

			@Override
			public ICollection<V> getValues() {
				// Stub method; unimplemented
				throw new UnsupportedOperationException();
			}

			@Override
			public ICollection<K> indicesOf(V item) {
				// Stub method; unimplemented
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean isEmpty() {
				// Stub method; unimplemented
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean itemExists(K key) {
				// Stub method; unimplemented
				throw new UnsupportedOperationException();
			}

			@Override
			public void put(K key, V value) throws IllegalArgumentException {
				_map.put(key, value);
			}

			@Override
			public void putAll(ICollection<IPair<K, V>> items) throws IllegalArgumentException {
				// Stub method; unimplemented
				throw new UnsupportedOperationException();
			}

			@Override
			public V removeByKey(K key) {
				return _map.remove(key);
			}

			@Override
			public boolean removeByKeyAndValue(K key, V value) {
				// Stub method; unimplemented
				throw new UnsupportedOperationException();
			}

			@Override
			public void setValidator(IFunction<IPair<K, V>, String> validator) {
				// Stub method; unimplemented
				throw new UnsupportedOperationException();
			}

			@Override
			public int size() {
				return _map.size();
			}
    		
    	}
    }
    
    private class CollectionStub<V> implements ICollection<V>
    {
    	private ArrayList<V> _collection = new ArrayList<V>();

		@Override
		public Iterator<V> iterator() {
			return _collection.iterator();
		}

		@Override
		public ICollection<V> makeClone() {
			// Stub method; unimplemented
			throw new UnsupportedOperationException();
		}

		@Override
		public V getArchetype() {
			// Stub method; unimplemented
			throw new UnsupportedOperationException();
		}

		@Override
		public String getParameterizedClassName() {
			// Stub method; unimplemented
			throw new UnsupportedOperationException();
		}

		@Override
		public void add(V item) throws UnsupportedOperationException {
			_collection.add(item);
		}

		@Override
		public void addAll(ICollection<? extends V> items) throws UnsupportedOperationException {
			// Stub method; unimplemented
			throw new UnsupportedOperationException();
		}

		@Override
		public void addAll(V[] items) throws UnsupportedOperationException {
			// Stub method; unimplemented
			throw new UnsupportedOperationException();
		}

		@Override
		public void clear() throws UnsupportedOperationException {
			// Stub method; unimplemented
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean contains(V item) {
			// Stub method; unimplemented
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean equals(ICollection<V> items) {
			// Stub method; unimplemented
			throw new UnsupportedOperationException();
		}

		@Override
		public V get(int index) {
			// Stub method; unimplemented
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isEmpty() {
			// Stub method; unimplemented
			throw new UnsupportedOperationException();
		}

		@Override
		public Object[] toArray() {
			// Stub method; unimplemented
			throw new UnsupportedOperationException();
		}

		@Override
		public int size() {
			// Stub method; unimplemented
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removeItem(V item) throws UnsupportedOperationException {
			// Stub method; unimplemented
			throw new UnsupportedOperationException();
		}
    }
}
