package inaugural.soliloquy.common.test.java;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import inaugural.soliloquy.common.GenericParamsSet;
import inaugural.soliloquy.common.GenericParamsSetFactory;
import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.IFunction;
import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.common.specs.IMap;
import soliloquy.common.specs.IMapFactory;
import soliloquy.common.specs.IPair;
import soliloquy.common.specs.IPersistentValuesHandler;

public class GenericParamsSetFactoryTests extends TestCase {
	private GenericParamsSetFactory _genericParamsSetFactory;
	
	private IPersistentValuesHandler _persistentValuesHandlerMock;
	
	private final IMapFactory MAP_FACTORY = new MapFactoryStub();
	
	private static final String PERSISTENT_VARIABLE_WRITE_VALUE = "GenericString";

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public GenericParamsSetFactoryTests( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( GenericParamsSetFactoryTests.class );
    }
    
    @Override
    protected void setUp() throws Exception
    {
    	_persistentValuesHandlerMock = mock(IPersistentValuesHandler.class);
    	when(_persistentValuesHandlerMock.writeValues(any())).thenReturn(PERSISTENT_VARIABLE_WRITE_VALUE);
    	when(_persistentValuesHandlerMock.makePersistentValueToWrite(any(),any())).thenReturn(null);

    	// TODO: Mock the MapFactory *somehow*, so this is truly a unit test again
    	_genericParamsSetFactory = new GenericParamsSetFactory(_persistentValuesHandlerMock, MAP_FACTORY);
    	
    }
    
    public void testMake()
    {
    	IGenericParamsSet genericParamsSet = _genericParamsSetFactory.make();
    	assertTrue(genericParamsSet instanceof GenericParamsSet);
    	
    	genericParamsSet.addParam("Blah", 123);
    	assertTrue((Integer) genericParamsSet.getParam(Integer.class.getCanonicalName(), "Blah") == 123);
    	assertTrue(genericParamsSet.write().equals(PERSISTENT_VARIABLE_WRITE_VALUE));
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
				// Stub method; unimplemented
				throw new UnsupportedOperationException();
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
				// Stub method; unimplemented
				throw new UnsupportedOperationException();
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
				// Stub method; unimplemented
				throw new UnsupportedOperationException();
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
