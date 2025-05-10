package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.Coordinate3dHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Coordinate3d;

import static inaugural.soliloquy.tools.random.Random.randomInt;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class Coordinate3dHandlerTests {
    private final int X = randomInt();
    private final int Y = randomInt();
    private final int Z = randomInt();

    private final String WRITTEN_VALUE = String.format("{\"x\":%d,\"y\":%d,\"z\":%d}", X, Y, Z);

    private TypeHandler<Coordinate3d> Coordinate3dHandler;

    @BeforeEach
    public void setUp() {
        Coordinate3dHandler = new Coordinate3dHandler();
    }

    @Test
    public void testWrite() {
        var output = Coordinate3dHandler.write(Coordinate3d.of(X, Y, Z));

        assertEquals(WRITTEN_VALUE, output);
    }

    @Test
    public void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> Coordinate3dHandler.write(null));
    }

    @Test
    public void testRead() {
        assertEquals(Coordinate3d.of(X, Y, Z), Coordinate3dHandler.read(WRITTEN_VALUE));
    }

    @Test
    public void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> Coordinate3dHandler.read(null));
        assertThrows(IllegalArgumentException.class, () -> Coordinate3dHandler.read(""));
    }

    @Test
    public void testArchetype() {
        var archetype = Coordinate3dHandler.archetype();

        assertNotNull(archetype);
        assertEquals(Coordinate3d.class.getCanonicalName(), archetype.getInterfaceName());
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Coordinate3d.class.getCanonicalName() + ">",
                Coordinate3dHandler.getInterfaceName());
    }

    @Test
    public void testHashCode() {
        assertEquals((TypeHandler.class.getCanonicalName() + "<" +
                        Coordinate3d.class.getCanonicalName() + ">").hashCode(),
                Coordinate3dHandler.hashCode());
    }

    @Test
    public void testEquals() {
        var equalHandler = new Coordinate3dHandler();
        //noinspection unchecked
        TypeHandler<Coordinate3d> unequalHandler = mock(TypeHandler.class);

        assertEquals(Coordinate3dHandler, equalHandler);
        assertNotEquals(Coordinate3dHandler, unequalHandler);
        assertNotEquals(null, Coordinate3dHandler);
    }

    @Test
    public void testToString() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Coordinate3d.class.getCanonicalName() + ">",
                Coordinate3dHandler.toString());
    }
}
