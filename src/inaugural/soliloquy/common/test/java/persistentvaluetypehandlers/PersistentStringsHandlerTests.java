package inaugural.soliloquy.common.test.java.persistentvaluetypehandlers;

import java.util.ArrayList;
import java.util.Iterator;

import inaugural.soliloquy.common.Collection;
import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentStringsHandler;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.ICollectionFactory;

public class PersistentStringsHandlerTests extends TestCase {
	private PersistentStringsHandler _persistentStringsHandler;
	private String STRING1 = "STRING1";
	private String STRING2 = "STRING2";
	private String STRING3 = "STRING3";
	
	private final ICollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PersistentStringsHandlerTests( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( PersistentStringsHandlerTests.class );
    }
    
	@Override
    protected void setUp() throws Exception
    {
    	_persistentStringsHandler = new PersistentStringsHandler(COLLECTION_FACTORY);
    }
	
	public void testRead()
	{
		String sourceData = STRING1 + "\u001f" + STRING2 + "\u001f" + STRING3;
		ICollection<String> expectedResults = new Collection<String>("");
		expectedResults.addAll(new String[] {STRING1, STRING2, STRING3});
		
		ICollection<String> parsedData = _persistentStringsHandler.read(sourceData);
		
		assertTrue(expectedResults.equals(parsedData));
	}
	
	public void testWrite()
	{
		ICollection<String> sourceData = new Collection<String>(new String[] {STRING1, STRING2, STRING3}, "");
		String expectedResult = STRING1 + "\u001f" + STRING2 + "\u001f" + STRING3;
		
		String parsedData = _persistentStringsHandler.write(sourceData);
		
		assertTrue(parsedData.equals(expectedResult));
	}
	
	public void testGetParameterizedClassName()
	{
		assertTrue(_persistentStringsHandler.getParameterizedClassName().equals("soliloquy.common.persistentvaluetypehandlers.IPersistentValueTypeHandler<soliloquy.common.specs.ICollection<java.lang.String>>"));
	}
	
	private class CollectionFactoryStub implements ICollectionFactory
	{
		@Override
		public <T> ICollection<T> make(T archetype) throws IllegalArgumentException {
			return new CollectionStub<T>(archetype);
		}

		@Override
		public <T> ICollection<T> make(T[] items, T archetype) throws IllegalArgumentException {
			// Stub; not implemented
			throw new UnsupportedOperationException();
		}
		
		private class CollectionStub<V> implements ICollection<V>
		{
			private final V ARCHETYPE;
			
			private CollectionStub(V archetype)
			{
				ARCHETYPE = archetype;
			}
			
			private ArrayList<V> _collection = new ArrayList<V>();

			@Override
			public Iterator<V> iterator() {
				// Stub; not implemented
				throw new UnsupportedOperationException();
			}

			@Override
			public ICollection<V> makeClone() {
				// Stub; not implemented
				throw new UnsupportedOperationException();
			}

			@Override
			public V getArchetype() {
				return ARCHETYPE;
			}

			@Override
			public String getParameterizedClassName() {
				return "soliloquy.common.specs.ICollection<" + ARCHETYPE.getClass().getCanonicalName() + ">";
			}

			@Override
			public void add(V item) throws UnsupportedOperationException {
				_collection.add(item);
			}

			@Override
			public void addAll(ICollection<? extends V> items) throws UnsupportedOperationException {
				// Stub; not implemented
				throw new UnsupportedOperationException();
			}

			@Override
			public void addAll(V[] items) throws UnsupportedOperationException {
				// Stub; not implemented
				throw new UnsupportedOperationException();
			}

			@Override
			public void clear() throws UnsupportedOperationException {
				// Stub; not implemented
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean contains(V item) {
				// Stub; not implemented
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean equals(ICollection<V> items) {
				// Stub; not implemented
				throw new UnsupportedOperationException();
			}

			@Override
			public V get(int index) {
				return _collection.get(index);
			}

			@Override
			public boolean isEmpty() {
				// Stub; not implemented
				throw new UnsupportedOperationException();
			}

			@Override
			public Object[] toArray() {
				// Stub; not implemented
				throw new UnsupportedOperationException();
			}

			@Override
			public int size() {
				return _collection.size();
			}

			@Override
			public boolean removeItem(V item) throws UnsupportedOperationException {
				// Stub; not implemented
				throw new UnsupportedOperationException();
			}
			
		}
	}
}
