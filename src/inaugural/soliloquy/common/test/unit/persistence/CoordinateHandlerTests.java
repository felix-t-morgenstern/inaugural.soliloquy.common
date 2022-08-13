package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.CoordinateHandler;
import inaugural.soliloquy.common.test.fakes.FakeCoordinate;
import inaugural.soliloquy.common.test.fakes.FakeCoordinateFactory;
import inaugural.soliloquy.common.test.fakes.FakeCoordinateHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Coordinate;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateHandlerTests {
    private final CoordinateFactory COORDINATE_FACTORY = new FakeCoordinateFactory();
    private final int X = 123;
    private final int Y = 456;
    private final String VALUES_STRING = "{\"x\":123,\"y\":456}";

    private CoordinateHandler _coordinateHandler;

    @BeforeEach
    void setUp() {
        _coordinateHandler = new CoordinateHandler(COORDINATE_FACTORY);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Coordinate.class.getCanonicalName() + ">",
                _coordinateHandler.getInterfaceName());
    }

    @Test
    void testGetArchetype() {
        assertNotNull(_coordinateHandler.getArchetype());
    }

    @Test
    void testWrite() {
        Coordinate coordinate = new FakeCoordinate();
        coordinate.setX(X);
        coordinate.setY(Y);

        assertEquals(VALUES_STRING, _coordinateHandler.write(coordinate));
    }

    @Test
    void testWriteNull() {
        assertEquals("NULL", _coordinateHandler.write(null));
    }

    @Test
    void testRead() {
        Coordinate coordinate = _coordinateHandler.read(VALUES_STRING);

        assertEquals(X, coordinate.getX());
        assertEquals(Y, coordinate.getY());
    }

    @Test
    void testReadNull() {
        assertNull(_coordinateHandler.read("NULL"));
    }

    @Test
    void testReadInvalidStrings() {
        assertThrows(IllegalArgumentException.class,
                () -> _coordinateHandler.read(null));
        assertThrows(IllegalArgumentException.class,
                () -> _coordinateHandler.read(""));
    }

    @Test
    void testHashCode() {
        assertEquals((TypeHandler.class.getCanonicalName() + "<" +
                        Coordinate.class.getCanonicalName() + ">").hashCode(),
                _coordinateHandler.hashCode());
    }

    @Test
    void testEquals() {
        TypeHandler<Coordinate> equalHandler = new CoordinateHandler(COORDINATE_FACTORY);
        TypeHandler<Coordinate> unequalHandler = new FakeCoordinateHandler();

        assertEquals(_coordinateHandler, equalHandler);
        assertNotEquals(_coordinateHandler, unequalHandler);
        assertNotEquals(null, _coordinateHandler);
    }

    @Test
    void testToString() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Coordinate.class.getCanonicalName() + ">",
                _coordinateHandler.toString());
    }
}
