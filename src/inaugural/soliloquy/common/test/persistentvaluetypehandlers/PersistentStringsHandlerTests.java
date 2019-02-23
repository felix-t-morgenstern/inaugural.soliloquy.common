package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import inaugural.soliloquy.common.Collection;
import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentStringsHandler;
import inaugural.soliloquy.common.test.stubs.CollectionFactoryStub;
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
	
	public void testGetInterfaceName()
	{
		assertTrue(_persistentStringsHandler.getInterfaceName().equals("soliloquy.common.persistentvaluetypehandlers.IPersistentValueTypeHandler<soliloquy.common.specs.ICollection<java.lang.String>>"));
	}
}
