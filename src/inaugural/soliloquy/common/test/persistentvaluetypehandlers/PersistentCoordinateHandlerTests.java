package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentCoordinateHandler;
import inaugural.soliloquy.common.test.fakes.FakeCoordinate;
import inaugural.soliloquy.common.test.fakes.FakeCoordinateFactory;
import inaugural.soliloquy.common.test.fakes.FakePersistentCoordinateHandler;
import inaugural.soliloquy.tools.persistentvaluetypehandlers.PersistentTypeHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.common.valueobjects.Coordinate;

import static org.junit.jupiter.api.Assertions.*;

class PersistentCoordinateHandlerTests {
    private final CoordinateFactory COORDINATE_FACTORY = new FakeCoordinateFactory();
    private final int X = 123;
    private final int Y = 456;
    private final String VALUES_STRING = "{\"x\":123,\"y\":456}";

    private PersistentCoordinateHandler _persistentCoordinateHandler;

    @BeforeEach
    void setUp() {
        _persistentCoordinateHandler = new PersistentCoordinateHandler(COORDINATE_FACTORY);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(
                PersistentValueTypeHandler.class.getCanonicalName() + "<" +
                        Coordinate.class.getCanonicalName() + ">",
                _persistentCoordinateHandler.getInterfaceName());
    }

    @Test
    void testGetArchetype() {
        assertNotNull(_persistentCoordinateHandler.getArchetype());
    }

    @Test
    void testWrite() {
        Coordinate coordinate = new FakeCoordinate();
        coordinate.setX(X);
        coordinate.setY(Y);

        assertEquals(VALUES_STRING, _persistentCoordinateHandler.write(coordinate));
    }

    @Test
    void testWriteNull() {
        assertEquals("NULL", _persistentCoordinateHandler.write(null));
    }

    @Test
    void testRead() {
        Coordinate coordinate = _persistentCoordinateHandler.read(VALUES_STRING);

        assertEquals(X, coordinate.getX());
        assertEquals(Y, coordinate.getY());
    }

    @Test
    void testReadNull() {
        assertNull(_persistentCoordinateHandler.read("NULL"));
    }

    @Test
    void testReadInvalidStrings() {
        assertThrows(IllegalArgumentException.class,
                () -> _persistentCoordinateHandler.read(null));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentCoordinateHandler.read(""));
    }

    @Test
    void testHashCode() {
        assertEquals((PersistentValueTypeHandler.class.getCanonicalName() + "<" +
                        Coordinate.class.getCanonicalName() + ">").hashCode(),
                _persistentCoordinateHandler.hashCode());
    }

    @Test
    void testEquals() {
        PersistentTypeHandler<Coordinate> equalHandler =
                new PersistentCoordinateHandler(COORDINATE_FACTORY);
        PersistentValueTypeHandler<Coordinate> unequalHandler =
                new FakePersistentCoordinateHandler();

        assertEquals(_persistentCoordinateHandler, equalHandler);
        assertNotEquals(_persistentCoordinateHandler, unequalHandler);
        assertNotEquals(null, _persistentCoordinateHandler);
    }

    @Test
    void testToString() {
        assertEquals(PersistentValueTypeHandler.class.getCanonicalName() + "<" +
                Coordinate.class.getCanonicalName() + ">",
                _persistentCoordinateHandler.toString());
    }
}
