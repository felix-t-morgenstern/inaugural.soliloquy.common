package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentIntegersHandler;
import inaugural.soliloquy.common.test.stubs.CollectionFactoryStub;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.ICollectionFactory;

public class PersistentIntegersHandlerTests {
	private final ICollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
	
	private PersistentIntegersHandler persistentIntegersHandler;

	private ICollection<Integer> integerCollectionMock;
	private Iterator<Integer> integerIteratorMock;
	private ICollection<Integer> integerEmptyCollectionMock;
	private Iterator<Integer> integerEmptyIteratorMock;
    
	@SuppressWarnings("unchecked")
	@BeforeEach
    protected void setUp() throws Exception {
		persistentIntegersHandler = new PersistentIntegersHandler(COLLECTION_FACTORY);
		
		integerIteratorMock = mock(Iterator.class);
		when(integerIteratorMock.hasNext()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
		when(integerIteratorMock.next()).thenReturn(1).thenReturn(2).thenReturn(3).thenReturn(4);
		
		integerCollectionMock = mock(ICollection.class);
		when(integerCollectionMock.iterator()).thenReturn(integerIteratorMock);
		
		integerEmptyIteratorMock = mock(Iterator.class);
		when(integerEmptyIteratorMock.hasNext()).thenReturn(false);
		when(integerEmptyIteratorMock.next()).thenReturn(null);
		
		integerEmptyCollectionMock = mock(ICollection.class);
		when(integerEmptyCollectionMock.iterator()).thenReturn(integerEmptyIteratorMock);
    }
	
	@Test
	public void testRead() {
		ICollection<Integer> readValue = persistentIntegersHandler.read("0,1,2,3");
		
		assertTrue(readValue instanceof ICollection<?>);
		assertTrue(readValue.size() == 4);
		assertTrue(readValue.get(0) == 0);
		assertTrue(readValue.get(1) == 1);
		assertTrue(readValue.get(2) == 2);
		assertTrue(readValue.get(3) == 3);
	}

	@Test
	public void testReadEmpty() {
		ICollection<Integer> readValue = persistentIntegersHandler.read("");
		assertTrue(readValue instanceof ICollection<?>);
		assertTrue(readValue.size() == 0);
	}

	@Test
	public void testReadNull() {
		try {
			persistentIntegersHandler.read(null);
			assertTrue(false);
		} catch(IllegalArgumentException e) {
			assertTrue(true);
		} catch(Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testWrite() {
		String writtenValues = persistentIntegersHandler.write(integerCollectionMock);
		
		assertTrue("1,2,3,4".equals(writtenValues));
	}

	@Test
	public void testWriteEmpty() {
		String writtenValues = persistentIntegersHandler.write(integerEmptyCollectionMock);
		
		assertTrue("".equals(writtenValues));
	}

	@Test
	public void testGetArchetype() {
		assertTrue(persistentIntegersHandler.getArchetype() != null);
	}

	@Test
	public void testGetParameterizedClassName() {
		assertTrue(persistentIntegersHandler.getInterfaceName()
				.equals("soliloquy.common.persistentvaluetypehandlers.IPersistentValueTypeHandler<soliloquy.common.specs.ICollection<java.lang.Integer>>"));
	}
}
