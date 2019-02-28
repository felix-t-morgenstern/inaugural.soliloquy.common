package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.Collection;
import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentStringsHandler;
import inaugural.soliloquy.common.test.stubs.CollectionFactoryStub;
import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.ICollectionFactory;

public class PersistentStringsHandlerTests {
	private PersistentStringsHandler _persistentStringsHandler;
	private String STRING1 = "STRING1";
	private String STRING2 = "STRING2";
	private String STRING3 = "STRING3";
	
	private final ICollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    
	@BeforeEach
    protected void setUp() throws Exception
    {
    	_persistentStringsHandler = new PersistentStringsHandler(COLLECTION_FACTORY);
    }

	@Test
	public void testRead()
	{
		String sourceData = STRING1 + "\u001f" + STRING2 + "\u001f" + STRING3;
		ICollection<String> expectedResults = new Collection<String>("");
		expectedResults.addAll(new String[] {STRING1, STRING2, STRING3});
		
		ICollection<String> parsedData = _persistentStringsHandler.read(sourceData);
		
		assertTrue(expectedResults.equals(parsedData));
	}

	@Test
	public void testWrite()
	{
		ICollection<String> sourceData = new Collection<String>(new String[] {STRING1, STRING2, STRING3}, "");
		String expectedResult = STRING1 + "\u001f" + STRING2 + "\u001f" + STRING3;
		
		String parsedData = _persistentStringsHandler.write(sourceData);
		
		assertTrue(parsedData.equals(expectedResult));
	}

	@Test
	public void testGetInterfaceName()
	{
		assertTrue(_persistentStringsHandler.getInterfaceName().equals("soliloquy.common.persistentvaluetypehandlers.IPersistentValueTypeHandler<soliloquy.common.specs.ICollection<java.lang.String>>"));
	}
}
