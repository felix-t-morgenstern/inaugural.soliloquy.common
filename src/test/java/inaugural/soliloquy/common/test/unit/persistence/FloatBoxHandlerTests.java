package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.FloatBoxHandler;
import inaugural.soliloquy.common.persistence.VertexHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.FloatBox;

import static inaugural.soliloquy.tools.random.Random.randomFloatBox;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FloatBoxHandlerTests {
    private final FloatBox FLOATBOX = randomFloatBox();

    private final String WRITTEN_VAL =
            String.format("{\"topLeft\":{\"x\":%s,\"y\":%s},\"bottomRight\":{\"x\":%s,\"y\":%s}}",
                    FLOATBOX.LEFT_X, FLOATBOX.TOP_Y, FLOATBOX.RIGHT_X, FLOATBOX.BOTTOM_Y);

    private TypeHandler<FloatBox> handler;

    @BeforeEach
    public void setUp(){
        handler = new FloatBoxHandler();
    }

    @Test
    public void testWrite() {
        assertEquals(WRITTEN_VAL, handler.write(FLOATBOX));
    }

    @Test
    public void testWriteWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> handler.write(null));
    }

    @Test
    public void testRead() {
        assertEquals(FLOATBOX, handler.read(WRITTEN_VAL));
    }

    @Test
    public void testReadWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> handler.read(null));
        assertThrows(IllegalArgumentException.class, () -> handler.read(""));
    }
}
