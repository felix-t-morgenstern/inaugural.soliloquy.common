package inaugural.soliloquy.common.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.Pair;

import static org.junit.jupiter.api.Assertions.*;

class PairTests {
	private Pair<String,Integer> _pair;
	
	private final String FIRST_ARCHETYPE = "FIRST_ARCHETYPE";
	private final Integer SECOND_ARCHETYPE = 2;

    @BeforeEach
    void setUp() throws Exception {
    	_pair = new Pair<String,Integer>(null,null,FIRST_ARCHETYPE,SECOND_ARCHETYPE);
    }
    
    @Test
    void testGetAndSetItems1And2() {
        assertNull(_pair.getItem1());
        assertNull(_pair.getItem2());

    	_pair.setItem1("String");
        assertSame("String", _pair.getItem1());
    	
    	_pair.setItem2(123);
        assertEquals(123, (int) _pair.getItem2());
    }

    @Test
    void testGetFirstArchetype() {
        assertEquals(FIRST_ARCHETYPE, _pair.getFirstArchetype());
    }

    @Test
    void testGetSecondArchetype() {
        assertSame(SECOND_ARCHETYPE, _pair.getSecondArchetype());
    }

    @Test
    void testGetParameterizedClassName() {
        assertEquals("soliloquy.common.specs.IPair<java.lang.String,java.lang.Integer>", _pair.getInterfaceName());
    }
}
