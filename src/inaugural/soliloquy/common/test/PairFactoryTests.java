package inaugural.soliloquy.common.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.PairFactory;
import soliloquy.common.specs.IPair;

import static org.junit.jupiter.api.Assertions.*;

class PairFactoryTests {
	private PairFactory _pairfactory;

	@BeforeEach
	void setUp() {
    	_pairfactory = new PairFactory();
    }
    
    @Test
	void testMake() {
		IPair<String, Integer> _newPair = _pairfactory.make("String", 123);
		assertNotNull(_newPair);
		assertSame("String", _newPair.getItem1());
		assertEquals(123, (int) _newPair.getItem2());
    }

    @Test
	void testMakeWithNullParams() {
		assertThrows(IllegalArgumentException.class,
				() -> _pairfactory.make("String", null));
		assertThrows(IllegalArgumentException.class,
				() -> _pairfactory.make("String", 0, "String", null));
		assertThrows(IllegalArgumentException.class,
				() -> _pairfactory.make("String", 0, null, 0));
    }
}
