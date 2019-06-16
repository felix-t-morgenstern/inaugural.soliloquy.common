package inaugural.soliloquy.common.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import inaugural.soliloquy.common.CoordinateFactory;
import soliloquy.specs.common.factories.ICoordinateFactory;
import soliloquy.specs.common.valueobjects.ICoordinate;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateFactoryTests {
	private CoordinateFactory _coordinateFactory;

    @BeforeEach
    void setUp() {
    	_coordinateFactory = new CoordinateFactory();
    }

    @Test
    void testMake() {
    	ICoordinate coordinate = _coordinateFactory.make(0,0);
        assertNotNull(coordinate);
    	
    	coordinate.setX(10);
        assertEquals(10, coordinate.getX());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(ICoordinateFactory.class.getCanonicalName(),
                _coordinateFactory.getInterfaceName());
    }
}
