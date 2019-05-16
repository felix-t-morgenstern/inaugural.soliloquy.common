package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentCoordinateHandler;
import inaugural.soliloquy.common.test.stubs.CoordinateFactoryStub;
import inaugural.soliloquy.common.test.stubs.CoordinateStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.common.specs.ICoordinate;
import soliloquy.common.specs.ICoordinateFactory;
import soliloquy.common.specs.IPersistentValueTypeHandler;

import static org.junit.jupiter.api.Assertions.*;

class PersistentCoordinateHandlerTests {
    private final ICoordinateFactory COORDINATE_FACTORY = new CoordinateFactoryStub();
    private final int X = 123;
    private final int Y = 456;

    private PersistentCoordinateHandler _persistentCoordinateHandler;

    @BeforeEach
    void setUp() {
        _persistentCoordinateHandler = new PersistentCoordinateHandler(COORDINATE_FACTORY);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(
                IPersistentValueTypeHandler.class.getCanonicalName() + "<" +
                        ICoordinate.class.getCanonicalName() + ">",
                _persistentCoordinateHandler.getInterfaceName());
    }

    @Test
    void testGetArchetype() {
        assertNotNull(_persistentCoordinateHandler.getArchetype());
    }

    @Test
    void testWrite() {
        ICoordinate coordinate = new CoordinateStub();
        coordinate.setX(X);
        coordinate.setY(Y);

        assertEquals(String.format("%d,%d", X, Y), _persistentCoordinateHandler.write(coordinate));
    }

    @Test
    void testWriteNull() {
        assertEquals("NULL", _persistentCoordinateHandler.write(null));
    }

    @Test
    void testRead() {
        ICoordinate coordinate = _persistentCoordinateHandler.read(String.format("%d,%d", X, Y));

        assertEquals(X, coordinate.getX());
        assertEquals(Y, coordinate.getY());
    }

    @Test
    void testReadNull() {
        assertEquals(_persistentCoordinateHandler.read("NULL"), null);
    }

    @Test
    void testReadInvalidStrings() {
        assertThrows(IllegalArgumentException.class,
                () -> _persistentCoordinateHandler.read("qwe,rty"));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentCoordinateHandler.read("123"));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentCoordinateHandler.read("123,456,789"));
    }
}
