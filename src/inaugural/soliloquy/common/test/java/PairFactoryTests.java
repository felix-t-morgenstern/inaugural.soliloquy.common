package inaugural.soliloquy.common.test.java;

import inaugural.soliloquy.common.PairFactory;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import soliloquy.common.specs.IPair;

public class PairFactoryTests extends TestCase {
	private PairFactory _pairfactory;
	
	private IPair<String,Integer> newPair;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PairFactoryTests( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( PairFactoryTests.class );
    }
    
    @Override
    protected void setUp() throws Exception
    {
    	_pairfactory = new PairFactory();
    }
    
    public void testMake()
    {
    	newPair = _pairfactory.make("String", 123);
    	assertTrue(newPair instanceof IPair<?,?>);
    	assertTrue(newPair.getItem1() == "String");
    	assertTrue(newPair.getItem2() == 123);
    }
    
    public void testMakeWithNullParams()
    {
    	try
    	{
        	newPair = _pairfactory.make("String", null);
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
    	try
    	{
        	newPair = _pairfactory.make(null, 0);
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
    	try
    	{
        	newPair = _pairfactory.make("String", 0, "String", null);
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
    	try
    	{
        	newPair = _pairfactory.make("String", 0, null, 0);
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
}
