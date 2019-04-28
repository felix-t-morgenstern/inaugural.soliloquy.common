package inaugural.soliloquy.common.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.MapFactory;
import soliloquy.common.specs.IMap;

import static org.junit.jupiter.api.Assertions.*;

class MapFactoryTests {
	private MapFactory _mapFactory;

    @BeforeEach
	void setUp() throws Exception {
    	// (No need to test IPairFactory functionality in this suite)
    	_mapFactory = new MapFactory(null);
    }

    @Test
	void testMake() {
    	IMap<String,Integer> newMap1 = _mapFactory.make("",0);
		assertNotNull(newMap1);
    	
    	newMap1.put("String1", 123);
		assertEquals(123, (int) newMap1.get("String1"));
    }
    
    @SuppressWarnings("unused")
    @Test
	void testMakeWithNullArchetypes() {
		assertThrows(IllegalArgumentException.class, () -> _mapFactory.make("",null));
		assertThrows(IllegalArgumentException.class, () -> _mapFactory.make(null,0));
    }
}
