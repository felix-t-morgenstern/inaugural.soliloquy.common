package inaugural.soliloquy.common.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import inaugural.soliloquy.common.CoordinateFactory;
import soliloquy.common.specs.ICoordinate;

public class CoordinateFactoryTests {
	private CoordinateFactory _coordinateFactory;

    @BeforeEach
    protected void setUp() throws Exception {
    	_coordinateFactory = new CoordinateFactory();
    }

    @Test
    public void testMake() {
    	ICoordinate coordinate = _coordinateFactory.make(0,0);
    	assertTrue(coordinate instanceof ICoordinate);
    	
    	coordinate.setX(10);
    	assertTrue(coordinate.getX() == 10);
    }
}
