package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.test.stubs.MapStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.PairFactoryImpl;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.Pair;

import static org.junit.jupiter.api.Assertions.*;

class PairFactoryImplTests {
	private PairFactoryImpl _pairfactory;

	@BeforeEach
	void setUp() {
    	_pairfactory = new PairFactoryImpl();
    }
    
    @Test
	void testMake() {
		Pair<String, Integer> _newPair = _pairfactory.make("String", 123);
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

	@SuppressWarnings("unchecked")
	@Test
	void testArchetypeWithNullArchetype() {
		Map archetype1 = new MapStub(123, null);
		Map archetype2 = new MapStub(null, 123);

		assertThrows(IllegalArgumentException.class, () -> _pairfactory.make(123, archetype1));
		assertThrows(IllegalArgumentException.class, () -> _pairfactory.make(archetype2, 123));
		assertThrows(IllegalArgumentException.class,
				() -> _pairfactory.make(123, null,123, archetype1));
		assertThrows(IllegalArgumentException.class,
				() -> _pairfactory.make(null, 123, archetype2, 123));
	}
}
