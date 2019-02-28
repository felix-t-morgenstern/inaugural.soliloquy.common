package inaugural.soliloquy.common.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.PairFactory;
import soliloquy.common.specs.IPair;

public class PairFactoryTests {
	private PairFactory _pairfactory;
	
	private IPair<String,Integer> newPair;

    @BeforeEach
    protected void setUp() throws Exception
    {
    	_pairfactory = new PairFactory();
    }
    
    @Test
    public void testMake()
    {
    	newPair = _pairfactory.make("String", 123);
    	assertTrue(newPair instanceof IPair<?,?>);
    	assertTrue(newPair.getItem1() == "String");
    	assertTrue(newPair.getItem2() == 123);
    }

    @Test
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
