package inaugural.soliloquy.common.test.java;

import inaugural.soliloquy.common.MapFactory;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import soliloquy.common.specs.IMap;

public class MapFactoryTests extends TestCase {
	private MapFactory _mapFactory;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MapFactoryTests( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( MapFactoryTests.class );
    }
    
    @Override
    protected void setUp() throws Exception
    {
    	// (No need to test IPairFactory functionality in this suite)
    	_mapFactory = new MapFactory(null);
    }
    
    public void testMake()
    {
    	IMap<String,Integer> newMap1 = _mapFactory.make("",0);
    	assertTrue(newMap1 instanceof IMap<?,?>);
    	
    	newMap1.put("String1", 123);
    	assertTrue(newMap1.get("String1") == 123);
    }
    
    @SuppressWarnings("unused")
	public void testMakeWithNullArchetypes()
    {
    	try
    	{
    		IMap<String,Integer> newMap1 = _mapFactory.make("",null);
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
    		IMap<String,Integer> newMap2 = _mapFactory.make(null,0);
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
