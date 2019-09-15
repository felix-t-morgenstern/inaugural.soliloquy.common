package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.test.stubs.CollectionFactoryStub;
import inaugural.soliloquy.common.test.stubs.MapStub;
import inaugural.soliloquy.common.test.stubs.PairFactoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.MapFactoryImpl;
import soliloquy.specs.common.infrastructure.Map;

import static org.junit.jupiter.api.Assertions.*;

class MapFactoryImplTests {
	private MapFactoryImpl _mapFactory;

    @BeforeEach
	void setUp() {
    	// (No need to test PairFactory functionality in this suite)
    	_mapFactory = new MapFactoryImpl(new PairFactoryStub(), new CollectionFactoryStub());
    }

    @Test
	void testMake() {
    	Map<String,Integer> map = _mapFactory.make("",0);
		assertNotNull(map);
		assertNotNull(map.getFirstArchetype());
		assertNotNull(map.getSecondArchetype());
    	
    	map.put("String1", 123);
		assertEquals(123, (int) map.get("String1"));
    }
    
    @SuppressWarnings("unused")
    @Test
	void testMakeWithNullArchetypes() {
		assertThrows(IllegalArgumentException.class, () -> _mapFactory.make("",null));
		assertThrows(IllegalArgumentException.class, () -> _mapFactory.make(null,0));
    }

	@SuppressWarnings("unchecked")
	@Test
	void testArchetypeWithNullArchetype() {
		Map archetype = new MapStub(null, null);

		assertThrows(IllegalArgumentException.class,
				() -> _mapFactory.make(archetype, 123));
		assertThrows(IllegalArgumentException.class,
				() -> _mapFactory.make(123, archetype));
	}
}
