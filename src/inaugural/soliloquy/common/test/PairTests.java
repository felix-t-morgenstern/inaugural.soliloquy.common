package inaugural.soliloquy.common.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.Pair;

public class PairTests {
	private Pair<String,Integer> pair;
	
	private final String FIRST_ARCHETYPE = "FIRST_ARCHETYPE";
	private final Integer SECOND_ARCHETYPE = 2;

    @BeforeEach
    protected void setUp() throws Exception
    {
    	pair = new Pair<String,Integer>(null,null,FIRST_ARCHETYPE,SECOND_ARCHETYPE);
    }
    
    @Test
    public void testGetAndSetItems1And2()
    {
    	assertTrue(pair.getItem1() == null);
    	assertTrue(pair.getItem2() == null);

    	pair.setItem1("String");
    	assertTrue(pair.getItem1() == "String");
    	
    	pair.setItem2(123);
    	assertTrue(pair.getItem2() == 123);
    }

    @Test
    public void testGetFirstArchetype()
    {
    	assertTrue(FIRST_ARCHETYPE.equals(pair.getFirstArchetype()));
    }

    @Test
    public void testGetSecondArchetype()
    {
    	assertTrue(SECOND_ARCHETYPE == pair.getSecondArchetype());
    }

    @Test
    public void testGetParameterizedClassName()
    {
    	assertTrue("soliloquy.common.specs.IPair<java.lang.String,java.lang.Integer>".equals(pair.getInterfaceName()));
    }
}
