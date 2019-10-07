package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.CoordinateImpl;
import inaugural.soliloquy.common.ReadableCoordinateImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;

import static org.junit.jupiter.api.Assertions.*;

class ReadableCoordinateTests {
    private ReadableCoordinate _readableCoordinate;

    private final int X = 100;
    private final int Y = 200;

    @BeforeEach
    void setUp() {
        _readableCoordinate = new ReadableCoordinateImpl(X,Y);
    }

    @Test
    void testGetXAndGetY() {
        assertEquals(X, _readableCoordinate.getX());
        assertEquals(Y, _readableCoordinate.getY());
    }

    @Test
    void testCompareTo() {
        ReadableCoordinate readableCoordinate = new ReadableCoordinateImpl(0,0);

        CoordinateImpl otherCoordinate = new CoordinateImpl(0,0);

        assertEquals(0, readableCoordinate.compareTo(otherCoordinate));

        otherCoordinate.setY(1);
        assertEquals(-1, readableCoordinate.compareTo(otherCoordinate));

        otherCoordinate.setY(2);
        assertEquals(-3, readableCoordinate.compareTo(otherCoordinate));

        otherCoordinate.setY(0);
        otherCoordinate.setX(2);
        assertEquals(-5, readableCoordinate.compareTo(otherCoordinate));

        otherCoordinate.setX(3);
        assertEquals(-9, readableCoordinate.compareTo(otherCoordinate));
    }

    @Test
    void testMakeClone() {
        Coordinate cloned = _readableCoordinate.makeClone();

        assertEquals(X, cloned.getX());
        assertEquals(Y, cloned.getY());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(ReadableCoordinate.class.getCanonicalName(),
                _readableCoordinate.getInterfaceName());
    }
}
