package inaugural.soliloquy.common.test.java.persistentvaluetypehandlers;

import java.util.ArrayList;
import java.util.Iterator;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentIntegersHandler;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static org.mockito.Mockito.*;

import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.ICollectionFactory;

public class PersistentIntegersHandlerTests extends TestCase {
	private final ICollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
	
	private PersistentIntegersHandler persistentIntegersHandler;
	
	private ICollection<Integer> integerCollectionMock;
	private Iterator<Integer> integerIteratorMock;
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PersistentIntegersHandlerTests( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( PersistentIntegersHandlerTests.class );
    }
    
	@SuppressWarnings("unchecked")
	@Override
    protected void setUp() throws Exception
    {
		persistentIntegersHandler = new PersistentIntegersHandler(COLLECTION_FACTORY);
		
		integerIteratorMock = mock(Iterator.class);
		when(integerIteratorMock.hasNext()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
		when(integerIteratorMock.next()).thenReturn(1).thenReturn(2).thenReturn(3).thenReturn(4);
		
		integerCollectionMock = mock(ICollection.class);
		when(integerCollectionMock.iterator()).thenReturn(integerIteratorMock);
    }
	
	public void testRead()
	{
		ICollection<Integer> readValue = persistentIntegersHandler.read("0,1,2,3");
		
		assertTrue(readValue instanceof ICollection<?>);
		assertTrue(readValue.size() == 4);
		assertTrue(readValue.get(0) == 0);
		assertTrue(readValue.get(1) == 1);
		assertTrue(readValue.get(2) == 2);
		assertTrue(readValue.get(3) == 3);
	}
	
	public void testReadEmpty()
	{
		ICollection<Integer> readValue = persistentIntegersHandler.read("");
		assertTrue(readValue instanceof ICollection<?>);
		assertTrue(readValue.size() == 0);
	}
	
	public void testReadNull()
	{
		try
		{
			persistentIntegersHandler.read(null);
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
	}
	
	public void testWrite()
	{
		String writtenValues = persistentIntegersHandler.write(integerCollectionMock);
		
		assertTrue("1,2,3,4".equals(writtenValues));
	}
	
	public void testWriteEmpty()
	{
		//TODO: Write this test
	}
	
	public void testGetArchetype()
	{
		assertTrue(persistentIntegersHandler.getArchetype() != null);
	}
	
	public void testGetParameterizedClassName()
	{
		assertTrue(persistentIntegersHandler.getParameterizedClassName().equals("soliloquy.common.persistentvaluetypehandlers.IPersistentValueTypeHandler<soliloquy.common.specs.ICollection<java.lang.Integer>>"));
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
