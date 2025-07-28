package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.Coordinate2dHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Coordinate2d;

import static inaugural.soliloquy.tools.random.Random.randomInt;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static soliloquy.specs.common.valueobjects.Coordinate2d.coordinate2dOf;

public class Coordinate2dHandlerTests {
    private final int X = randomInt();
    private final int Y = randomInt();

    private final String WRITTEN_VALUE = String.format("{\"x\":%d,\"y\":%d}", X, Y);

    private TypeHandler<Coordinate2d> handler;

    @BeforeEach
    public void setUp() {
        handler = new Coordinate2dHandler();
    }

    @Test
    public void testWrite() {
        var output = handler.write(coordinate2dOf(X, Y));

        assertEquals(WRITTEN_VALUE, output);
    }

    @Test
    public void testWriteWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> handler.write(null));
    }

    @Test
    public void testRead() {
        assertEquals(coordinate2dOf(X, Y), handler.read(WRITTEN_VALUE));
    }

    @Test
    public void testReadWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> handler.read(null));
        assertThrows(IllegalArgumentException.class, () -> handler.read(""));
    }
}
