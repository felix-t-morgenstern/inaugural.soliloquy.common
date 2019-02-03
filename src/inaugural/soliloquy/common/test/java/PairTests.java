package inaugural.soliloquy.common.test.java;

import inaugural.soliloquy.common.Pair;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PairTests extends TestCase {
	private Pair<String,Integer> pair;
	
	private final String FIRST_ARCHETYPE = "FIRST_ARCHETYPE";
	private final Integer SECOND_ARCHETYPE = 2;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PairTests( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( PairTests.class );
    }
    
    @Override
    protected void setUp() throws Exception
    {
    	pair = new Pair<String,Integer>(null,null,FIRST_ARCHETYPE,SECOND_ARCHETYPE);
    }
    
    public void testGetAndSetItems1And2()
    {
    	assertTrue(pair.getItem1() == null);
    	assertTrue(pair.getItem2() == null);

    	pair.setItem1("String");
    	assertTrue(pair.getItem1() == "String");
    	
    	pair.setItem2(123);
    	assertTrue(pair.getItem2() == 123);
    }
    
    public void testGetFirstArchetype()
    {
    	assertTrue(FIRST_ARCHETYPE.equals(pair.getFirstArchetype()));
    }
    
    public void testGetSecondArchetype()
    {
    	assertTrue(SECOND_ARCHETYPE == pair.getSecondArchetype());
    }
    
    public void testGetParameterizedClassName()
    {
    	assertTrue("soliloquy.common.specs.IPair<java.lang.String,java.lang.Integer>".equals(pair.getParameterizedClassName()));
    }
}
