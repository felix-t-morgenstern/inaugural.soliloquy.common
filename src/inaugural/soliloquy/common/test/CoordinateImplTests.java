package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.CoordinateImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import soliloquy.specs.common.valueobjects.Coordinate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CoordinateImplTests {
	private CoordinateImpl coordinate;

    @BeforeEach
	void setUp() {
    	Mockito.reset();
    	coordinate = new CoordinateImpl(0,0);
    }

    @Test
	void testGetAndSetXAndY() {
    	coordinate.setX(123);
		assertEquals(123, coordinate.getX());
    	coordinate.setY(456);
		assertEquals(456, coordinate.getY());
    }

    @Test
	void testCompareTo() {
    	coordinate.setX(0);
    	coordinate.setY(0);
    	
    	CoordinateImpl otherCoordinate = new CoordinateImpl(0,0);

		assertEquals(0, coordinate.compareTo(otherCoordinate));
    	
    	otherCoordinate.setY(1);
		assertEquals(coordinate.compareTo(otherCoordinate), -1);
    	
    	otherCoordinate.setY(2);
		assertEquals(coordinate.compareTo(otherCoordinate), -3);
    	
    	coordinate.setX(2);
		assertEquals(2, coordinate.compareTo(otherCoordinate));
    	
    	coordinate.setX(3);
		assertEquals(6, coordinate.compareTo(otherCoordinate));
    }

    @Test
	void testMakeClone() {
    	coordinate.setX(123);
    	coordinate.setY(456);
    	Coordinate cloned = coordinate.makeClone();
    	coordinate.setX(-123);
    	coordinate.setY(-456);

		assertEquals(123, cloned.getX());
		assertEquals(456, cloned.getY());
    }

    @Test
	void testGetInterfaceName() {
    	assertEquals(Coordinate.class.getCanonicalName(), new CoordinateImpl(0,0).getInterfaceName());
	}
}
