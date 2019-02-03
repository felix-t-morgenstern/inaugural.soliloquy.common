package inaugural.soliloquy.common.test.java.persistentvaluetypehandlers;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentBooleanHandler;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PersistentBooleanHandlerTests extends TestCase {
	private PersistentBooleanHandler persistentBooleanHandler;
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PersistentBooleanHandlerTests( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( PersistentBooleanHandlerTests.class );
    }
    
	@Override
    protected void setUp() throws Exception
    {
		persistentBooleanHandler = new PersistentBooleanHandler();
    }
	
	public void testRead()
	{
		assertTrue(persistentBooleanHandler.read("true") == true);
	}
	
	public void testReadNull()
	{
		try
		{
			persistentBooleanHandler.read(null);
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
		assertTrue(persistentBooleanHandler.write(true).equals("true"));
	}
	
	public void testGetArchetype()
	{
		assertTrue(persistentBooleanHandler.getArchetype() != null);
	}
	
	public void testGetParameterizedClassName()
	{
		assertTrue(persistentBooleanHandler.getParameterizedClassName().equals("soliloquy.common.persistentvaluetypehandlers.IPersistentValueTypeHandler<java.lang.Boolean>"));
	}
}
