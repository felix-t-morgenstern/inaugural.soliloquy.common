package inaugural.soliloquy.common.test.unit.valueobjects;

import inaugural.soliloquy.common.valueobjects.CoordinateImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.valueobjects.Coordinate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CoordinateImplTests {
    private CoordinateImpl _coordinate;

    @BeforeEach
    void setUp() {
        _coordinate = new CoordinateImpl(0, 0);
    }

    @Test
    void testGetAndSetXAndY() {
        _coordinate.setX(123);
        assertEquals(123, _coordinate.getX());
        _coordinate.setY(456);
        assertEquals(456, _coordinate.getY());
    }

    @Test
    void testCompareTo() {
        _coordinate.setX(0);
        _coordinate.setY(0);

        CoordinateImpl otherCoordinate = new CoordinateImpl(0, 0);

        assertEquals(0, _coordinate.compareTo(otherCoordinate));

        otherCoordinate.setY(1);
        assertEquals(_coordinate.compareTo(otherCoordinate), -1);

        otherCoordinate.setY(2);
        assertEquals(_coordinate.compareTo(otherCoordinate), -3);

        _coordinate.setX(2);
        assertEquals(2, _coordinate.compareTo(otherCoordinate));

        _coordinate.setX(3);
        assertEquals(6, _coordinate.compareTo(otherCoordinate));
    }

    @Test
    void testMakeClone() {
        _coordinate.setX(123);
        _coordinate.setY(456);
        Coordinate cloned = _coordinate.makeClone();
        _coordinate.setX(-123);
        _coordinate.setY(-456);

        assertEquals(123, cloned.getX());
        assertEquals(456, cloned.getY());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(Coordinate.class.getCanonicalName(), _coordinate.getInterfaceName());
    }
}
