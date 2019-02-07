package inaugural.soliloquy.common.test.java.persistentvaluetypehandlers;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentStringHandler;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PersistentStringHandlerTests extends TestCase {
	private PersistentStringHandler persistentStringHandler;
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PersistentStringHandlerTests( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( PersistentStringHandlerTests.class );
    }
    
	@Override
    protected void setUp() throws Exception
    {
    	persistentStringHandler = new PersistentStringHandler();
    }
    
    public void testRead()
    {
    	assertTrue(persistentStringHandler.read("This Value!").equals("This Value!"));
    }
	
	public void testReadNull()
	{
		try
		{
			persistentStringHandler.read(null);
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
    	assertTrue(persistentStringHandler.write("This Value!").equals("This Value!"));
    }
	
	public void testGetArchetype()
	{
		assertTrue(persistentStringHandler.getArchetype() != null);
	}
	
	public void testGetInterfaceName()
	{
		assertTrue(persistentStringHandler.getInterfaceName().equals("soliloquy.common.persistentvaluetypehandlers.IPersistentValueTypeHandler<java.lang.String>"));
	}
}
