package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.VertexHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Vertex;

import static inaugural.soliloquy.tools.random.Random.randomVertex;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VertexHandlerTests {
    private final Vertex VERTEX = randomVertex();

    private final String WRITTEN_VAL = String.format("{\"x\":%s,\"y\":%s}", VERTEX.X, VERTEX.Y);

    private TypeHandler<Vertex> handler;

    @BeforeEach
    public void setUp(){
        handler = new VertexHandler();
    }

    @Test
    public void testWrite() {
        assertEquals(WRITTEN_VAL, handler.write(VERTEX));
    }

    @Test
    public void testWriteWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> handler.write(null));
    }

    @Test
    public void testRead() {
        assertEquals(VERTEX, handler.read(WRITTEN_VAL));
    }

    @Test
    public void testReadWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> handler.read(null));
        assertThrows(IllegalArgumentException.class, () -> handler.read(""));
    }
}
