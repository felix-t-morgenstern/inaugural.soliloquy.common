package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.ActionHandler;
import inaugural.soliloquy.tools.testing.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.persistence.TypeHandler;

import java.util.function.Function;

import static inaugural.soliloquy.tools.random.Random.randomString;
import static inaugural.soliloquy.tools.testing.Assertions.once;
import static inaugural.soliloquy.tools.testing.Mock.generateMockLookupFunctionWithId;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

public class ActionHandlerTests {
    private final String ACTION_ID = randomString();
    @SuppressWarnings("rawtypes") private final Mock.LookupAndEntitiesWithId<Action>
            MOCK_ACTION_AND_LOOKUP = generateMockLookupFunctionWithId(Action.class, ACTION_ID);
    @SuppressWarnings("rawtypes") private final Action MOCK_ACTION =
            MOCK_ACTION_AND_LOOKUP.entities.getFirst();
    @SuppressWarnings("rawtypes") private final Function<String, Action> MOCK_GET_ACTION =
            MOCK_ACTION_AND_LOOKUP.lookup;

    private final String WRITTEN_VALUE = String.format("{\"actionId\":\"%s\"}", ACTION_ID);

    @SuppressWarnings("rawtypes") private TypeHandler<Action> handler;

    @BeforeEach
    public void setUp() {
        handler = new ActionHandler(MOCK_GET_ACTION);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> new ActionHandler(null));
    }

    @Test
    public void testWrite() {
        var output = handler.write(MOCK_ACTION);

        assertEquals(WRITTEN_VALUE, output);
        verify(MOCK_ACTION, once()).id();
    }

    @Test
    public void testWriteWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> handler.write(null));
    }

    @Test
    public void testRead() {
        var output = handler.read(WRITTEN_VALUE);

        assertSame(MOCK_ACTION, output);
        verify(MOCK_GET_ACTION, once()).apply(ACTION_ID);
    }

    @Test
    public void testReadWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> handler.read(null));
        assertThrows(IllegalArgumentException.class, () -> handler.read(""));
    }
}
