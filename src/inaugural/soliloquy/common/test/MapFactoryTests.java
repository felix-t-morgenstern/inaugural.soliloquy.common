package inaugural.soliloquy.common.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.MapFactory;
import soliloquy.common.specs.IMap;

public class MapFactoryTests {
	private MapFactory _mapFactory;

    @BeforeEach
    protected void setUp() throws Exception
    {
    	// (No need to test IPairFactory functionality in this suite)
    	_mapFactory = new MapFactory(null);
    }

    @Test
    public void testMake()
    {
    	IMap<String,Integer> newMap1 = _mapFactory.make("",0);
    	assertTrue(newMap1 instanceof IMap<?,?>);
    	
    	newMap1.put("String1", 123);
    	assertTrue(newMap1.get("String1") == 123);
    }
    
    @SuppressWarnings("unused")
    @Test
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
