package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.FunctionHandler;
import inaugural.soliloquy.tools.testing.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.Function;
import soliloquy.specs.common.persistence.TypeHandler;

import static inaugural.soliloquy.tools.random.Random.randomString;
import static inaugural.soliloquy.tools.testing.Assertions.once;
import static inaugural.soliloquy.tools.testing.Mock.generateMockLookupFunctionWithId;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

public class FunctionHandlerTests {
    private final String FUNCTION_ID = randomString();
    @SuppressWarnings("rawtypes") private final Mock.LookupAndEntitiesWithId<Function>
            MOCK_FUNCTION_AND_LOOKUP =
            generateMockLookupFunctionWithId(Function.class, FUNCTION_ID);
    @SuppressWarnings("rawtypes") private final Function MOCK_FUNCTION =
            MOCK_FUNCTION_AND_LOOKUP.entities.getFirst();
    @SuppressWarnings("rawtypes") private final java.util.function.Function<String, Function>
            MOCK_GET_FUNCTION = MOCK_FUNCTION_AND_LOOKUP.lookup;

    private final String WRITTEN_VALUE = String.format("{\"functionId\":\"%s\"}", FUNCTION_ID);

    @SuppressWarnings("rawtypes") private TypeHandler<Function> handler;

    @BeforeEach
    public void setUp() {
        handler = new FunctionHandler(MOCK_GET_FUNCTION);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> new FunctionHandler(null));
    }

    @Test
    public void testWrite() {
        var output = handler.write(MOCK_FUNCTION);

        assertEquals(WRITTEN_VALUE, output);
        verify(MOCK_FUNCTION, once()).id();
    }

    @Test
    public void testWriteWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> handler.write(null));
    }

    @Test
    public void testRead() {
        var output = handler.read(WRITTEN_VALUE);

        assertSame(MOCK_FUNCTION, output);
        verify(MOCK_GET_FUNCTION, once()).apply(FUNCTION_ID);
    }

    @Test
    public void testReadWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> handler.read(null));
        assertThrows(IllegalArgumentException.class, () -> handler.read(""));
    }
}
