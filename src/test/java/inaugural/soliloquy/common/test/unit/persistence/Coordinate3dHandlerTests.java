package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.Coordinate3dHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Coordinate3d;

import static inaugural.soliloquy.tools.random.Random.randomInt;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static soliloquy.specs.common.valueobjects.Coordinate3d.coordinate3dOf;

public class Coordinate3dHandlerTests {
    private final int X = randomInt();
    private final int Y = randomInt();
    private final int Z = randomInt();

    private final String WRITTEN_VALUE = String.format("{\"x\":%d,\"y\":%d,\"z\":%d}", X, Y, Z);

    private TypeHandler<Coordinate3d> handler;

    @BeforeEach
    public void setUp() {
        handler = new Coordinate3dHandler();
    }

    @Test
    public void testTypeHandled() {
        assertEquals(Coordinate3d.class.getCanonicalName(), handler.typeHandled());
    }

    @Test
    public void testWrite() {
        var output = handler.write(coordinate3dOf(X, Y, Z));

        assertEquals(WRITTEN_VALUE, output);
    }

    @Test
    public void testWriteWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> handler.write(null));
    }

    @Test
    public void testRead() {
        assertEquals(coordinate3dOf(X, Y, Z), handler.read(WRITTEN_VALUE));
    }

    @Test
    public void testReadWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> handler.read(null));
        assertThrows(IllegalArgumentException.class, () -> handler.read(""));
    }
}
