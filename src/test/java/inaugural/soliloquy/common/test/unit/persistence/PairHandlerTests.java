package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.PairHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.persistence.PersistenceHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Pair;

import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.arrayOf;
import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static inaugural.soliloquy.tools.testing.Mock.generateMockPersistenceHandlerWithSimpleHandlers;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static soliloquy.specs.common.valueobjects.Pair.pairOf;

public class PairHandlerTests {
    private final String TYPE_1 = String.class.getCanonicalName();
    private final String VALUE_1 = randomString();
    private final String TYPE_2 = Integer.class.getCanonicalName();
    private final Integer VALUE_2 = randomInt();
    private final String VALUES_STRING = String.format(
            "{\"type1\":\"%s\",\"value1\":\"%s\",\"type2\":\"%s\",\"value2\":\"%d\"}",
            TYPE_1, VALUE_1, TYPE_2, VALUE_2);
    private final String VALUES_STRING_FIRST_NULL = String.format(
            "{\"type2\":\"%s\",\"value2\":\"%d\"}",
            TYPE_2, VALUE_2);
    private final String VALUES_STRING_SECOND_NULL = String.format(
            "{\"type1\":\"%s\",\"value1\":\"%s\"}",
            TYPE_1, VALUE_1);

    @SuppressWarnings("rawtypes")
    private final Pair<PersistenceHandler, Map<String, TypeHandler>>
            MOCK_PERSISTENCE_HANDLER_AND_TYPE_HANDLERS =
            generateMockPersistenceHandlerWithSimpleHandlers(arrayOf(VALUE_1),
                    arrayOf(VALUE_2));
    private final PersistenceHandler MOCK_PERSISTENCE_HANDLER =
            MOCK_PERSISTENCE_HANDLER_AND_TYPE_HANDLERS.FIRST;
    @SuppressWarnings("rawtypes") private final TypeHandler MOCK_STRING_HANDLER =
            MOCK_PERSISTENCE_HANDLER_AND_TYPE_HANDLERS.SECOND
                    .get(String.class.getCanonicalName());
    @SuppressWarnings("rawtypes") private final TypeHandler MOCK_INTEGER_HANDLER =
            MOCK_PERSISTENCE_HANDLER_AND_TYPE_HANDLERS.SECOND
                    .get(Integer.class.getCanonicalName());

    private PairHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new PairHandler(MOCK_PERSISTENCE_HANDLER);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> new PairHandler(null));
    }

    @Test
    public void testWrite() {
        var pair = pairOf(VALUE_1, VALUE_2);

        var output = handler.write(pair);

        assertEquals(VALUES_STRING, output);
        verify(MOCK_PERSISTENCE_HANDLER, times(1)).getTypeHandler(TYPE_1);
        verify(MOCK_PERSISTENCE_HANDLER, times(1)).getTypeHandler(TYPE_2);
        //noinspection unchecked
        verify(MOCK_STRING_HANDLER, times(1)).write(VALUE_1);
        //noinspection unchecked
        verify(MOCK_INTEGER_HANDLER, times(1)).write(VALUE_2);
    }

    @Test
    public void testWriteFirstNull() {
        var pair = pairOf(null, VALUE_2);

        var output = handler.write(pair);

        assertEquals(VALUES_STRING_FIRST_NULL, output);
        verify(MOCK_PERSISTENCE_HANDLER, times(1)).getTypeHandler(TYPE_2);
        //noinspection unchecked
        verify(MOCK_INTEGER_HANDLER, times(1)).write(VALUE_2);
    }

    @Test
    public void testWriteSecondNull() {
        var pair = pairOf(VALUE_1, null);

        var output = handler.write(pair);

        assertEquals(VALUES_STRING_SECOND_NULL, output);
        verify(MOCK_PERSISTENCE_HANDLER, times(1)).getTypeHandler(TYPE_1);
        //noinspection unchecked
        verify(MOCK_STRING_HANDLER, times(1)).write(VALUE_1);
    }

    @Test
    public void testWriteNull() {
        assertThrows(IllegalArgumentException.class, () -> handler.write(null));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testRead() {
        Pair<String, Integer> pair = handler.read(VALUES_STRING);

        assertNotNull(pair);
        assertEquals(VALUE_1, pair.FIRST);
        assertEquals(VALUE_2, pair.SECOND);
        verify(MOCK_PERSISTENCE_HANDLER, times(1)).getTypeHandler(TYPE_1);
        verify(MOCK_PERSISTENCE_HANDLER, times(1)).getTypeHandler(TYPE_2);
        verify(MOCK_STRING_HANDLER, times(1)).read(VALUE_1);
        verify(MOCK_INTEGER_HANDLER, times(1)).read(VALUE_2.toString());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testReadFirstNull() {
        Pair<String, Integer> pair = handler.read(VALUES_STRING_FIRST_NULL);

        assertNotNull(pair);
        assertNull(pair.FIRST);
        assertEquals(VALUE_2, pair.SECOND);
        verify(MOCK_PERSISTENCE_HANDLER, times(1)).getTypeHandler(TYPE_2);
        verify(MOCK_INTEGER_HANDLER, times(1)).read(VALUE_2.toString());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testReadSecondNull() {
        Pair<String, Integer> pair = handler.read(VALUES_STRING_SECOND_NULL);

        assertNotNull(pair);
        assertEquals(VALUE_1, pair.FIRST);
        assertNull(pair.SECOND);
        verify(MOCK_PERSISTENCE_HANDLER, times(1)).getTypeHandler(TYPE_1);
        verify(MOCK_STRING_HANDLER, times(1)).read(VALUE_1);
    }

    @Test
    public void testReadWithInvalidParameters() {
        assertThrows(IllegalArgumentException.class, () -> handler.read(null));
        assertThrows(IllegalArgumentException.class, () -> handler.read(""));
    }
}
