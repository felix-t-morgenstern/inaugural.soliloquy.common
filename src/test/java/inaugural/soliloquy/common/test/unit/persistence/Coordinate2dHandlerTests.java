package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.Coordinate2dHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Coordinate2d;

import static inaugural.soliloquy.tools.random.Random.randomInt;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class Coordinate2dHandlerTests {
    private final int X = randomInt();
    private final int Y = randomInt();

    private final String WRITTEN_VALUE = String.format("{\"x\":%d,\"y\":%d}", X, Y);

    private TypeHandler<Coordinate2d> coordinate2dHandler;

    @BeforeEach
    public void setUp() {
        coordinate2dHandler = new Coordinate2dHandler();
    }

    @Test
    public void testWrite() {
        var output = coordinate2dHandler.write(Coordinate2d.of(X, Y));

        assertEquals(WRITTEN_VALUE, output);
    }

    @Test
    public void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> coordinate2dHandler.write(null));
    }

    @Test
    public void testRead() {
        assertEquals(Coordinate2d.of(X, Y), coordinate2dHandler.read(WRITTEN_VALUE));
    }

    @Test
    public void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> coordinate2dHandler.read(null));
        assertThrows(IllegalArgumentException.class, () -> coordinate2dHandler.read(""));
    }

    @Test
    public void testArchetype() {
        var archetype = coordinate2dHandler.archetype();

        assertNotNull(archetype);
        assertEquals(Coordinate2d.class.getCanonicalName(), archetype.getInterfaceName());
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Coordinate2d.class.getCanonicalName() + ">",
                coordinate2dHandler.getInterfaceName());
    }

    @Test
    public void testHashCode() {
        assertEquals((TypeHandler.class.getCanonicalName() + "<" +
                        Coordinate2d.class.getCanonicalName() + ">").hashCode(),
                coordinate2dHandler.hashCode());
    }

    @Test
    public void testEquals() {
        var equalHandler = new Coordinate2dHandler();
        //noinspection unchecked
        TypeHandler<Coordinate2d> unequalHandler = mock(TypeHandler.class);

        assertEquals(coordinate2dHandler, equalHandler);
        assertNotEquals(coordinate2dHandler, unequalHandler);
        assertNotEquals(null, coordinate2dHandler);
    }

    @Test
    public void testToString() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Coordinate2d.class.getCanonicalName() + ">",
                coordinate2dHandler.toString());
    }
}
