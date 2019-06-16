package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import soliloquy.specs.common.valueobjects.ICoordinate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CoordinateTests {
	private Coordinate coordinate;

    @BeforeEach
	void setUp() {
    	Mockito.reset();
    	coordinate = new Coordinate(0,0);
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
    	
    	Coordinate otherCoordinate = new Coordinate(0,0);

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
    	ICoordinate cloned = coordinate.makeClone();
    	coordinate.setX(-123);
    	coordinate.setY(-456);

		assertEquals(123, cloned.getX());
		assertEquals(456, cloned.getY());
    }

    @Test
	void testGetInterfaceName() {
    	assertEquals(ICoordinate.class.getCanonicalName(), new Coordinate(0,0).getInterfaceName());
	}
}
