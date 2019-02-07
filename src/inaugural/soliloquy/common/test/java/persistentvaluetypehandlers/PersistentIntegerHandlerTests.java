package inaugural.soliloquy.common.test.java.persistentvaluetypehandlers;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentIntegerHandler;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PersistentIntegerHandlerTests extends TestCase {
	private PersistentIntegerHandler persistentIntegerHandler;
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PersistentIntegerHandlerTests( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( PersistentIntegerHandlerTests.class );
    }
    
	@Override
    protected void setUp() throws Exception
    {
    	persistentIntegerHandler = new PersistentIntegerHandler();
    }
	
	public void testRead()
	{
		assertTrue(persistentIntegerHandler.read("123") == 123);
	}
	
	public void testReadNull()
	{
		try
		{
			persistentIntegerHandler.read(null);
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
		assertTrue(persistentIntegerHandler.write(123).equals("123"));
	}
	
	public void testWriteWhenNull()
	{
		assertTrue(persistentIntegerHandler.write(null).equals(""));
	}
	
	public void testGetArchetype()
	{
		assertTrue(persistentIntegerHandler.getArchetype() != null);
	}
	
	public void testGetInterfaceName()
	{
		assertTrue(persistentIntegerHandler.getInterfaceName().equals("soliloquy.common.persistentvaluetypehandlers.IPersistentValueTypeHandler<java.lang.Integer>"));
	}
}
