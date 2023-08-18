package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.PairHandler;
import org.junit.Before;
import org.junit.Test;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Pair;

import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.arrayOf;
import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static inaugural.soliloquy.tools.testing.Mock.generateMockPersistentValuesHandlerWithSimpleHandlers;
import static inaugural.soliloquy.tools.valueobjects.Pair.pairOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PairHandlerTests {
    private final String TYPE_1 = String.class.getCanonicalName();
    private final String VALUE_1 = randomString();
    private final String TYPE_2 = Integer.class.getCanonicalName();
    private final Integer VALUE_2 = randomInt();
    private final String VALUES_STRING = String.format(
            "{\"type1\":\"%s\",\"value1\":\"%s\",\"type2\":\"%s\",\"value2\":\"%d\"}",
            TYPE_1, VALUE_1, TYPE_2, VALUE_2);

    @SuppressWarnings("rawtypes")
    private final Pair<PersistentValuesHandler, Map<String, TypeHandler>>
            MOCK_PERSISTENT_VALUES_HANDLER_AND_TYPE_HANDLERS =
            generateMockPersistentValuesHandlerWithSimpleHandlers(arrayOf(VALUE_1),
                    arrayOf(VALUE_2));
    private final PersistentValuesHandler MOCK_PERSISTENT_VALUES_HANDLER =
            MOCK_PERSISTENT_VALUES_HANDLER_AND_TYPE_HANDLERS.item1();
    @SuppressWarnings("rawtypes") private final TypeHandler MOCK_STRING_HANDLER =
            MOCK_PERSISTENT_VALUES_HANDLER_AND_TYPE_HANDLERS.item2()
                    .get(String.class.getCanonicalName());
    @SuppressWarnings("rawtypes") private final TypeHandler MOCK_INTEGER_HANDLER =
            MOCK_PERSISTENT_VALUES_HANDLER_AND_TYPE_HANDLERS.item2()
                    .get(Integer.class.getCanonicalName());

    private PairHandler pairHandler;

    @Before
    public void setUp() {
        pairHandler = new PairHandler(MOCK_PERSISTENT_VALUES_HANDLER);
    }

    @Test
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new PairHandler(null));
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Pair.class.getCanonicalName() + ">",
                pairHandler.getInterfaceName());
    }

    @Test
    public void testArchetype() {
        assertNotNull(pairHandler.archetype());
        assertEquals(Pair.class.getCanonicalName(),
                pairHandler.archetype().getInterfaceName());
    }

    @Test
    public void testWrite() {
        var pair = pairOf(VALUE_1, VALUE_2);

        var output = pairHandler.write(pair);

        assertEquals(VALUES_STRING, output);
        verify(MOCK_PERSISTENT_VALUES_HANDLER, times(1)).getTypeHandler(TYPE_1);
        verify(MOCK_PERSISTENT_VALUES_HANDLER, times(1)).getTypeHandler(TYPE_2);
        //noinspection unchecked
        verify(MOCK_STRING_HANDLER, times(1)).write(VALUE_1);
        //noinspection unchecked
        verify(MOCK_INTEGER_HANDLER, times(1)).write(VALUE_2);
    }

    @Test
    public void testWriteNull() {
        assertThrows(IllegalArgumentException.class, () -> pairHandler.write(null));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testRead() {
        when(MOCK_PERSISTENT_VALUES_HANDLER.generateArchetype(eq(TYPE_1))).thenReturn(
                randomString());
        when(MOCK_PERSISTENT_VALUES_HANDLER.generateArchetype(eq(TYPE_2))).thenReturn(randomInt());

        Pair<String, Integer> pair = pairHandler.read(VALUES_STRING);

        assertNotNull(pair);
        assertEquals(VALUE_1, pair.item1());
        assertEquals(VALUE_2, pair.item2());
        verify(MOCK_PERSISTENT_VALUES_HANDLER, times(1)).getTypeHandler(TYPE_1);
        verify(MOCK_PERSISTENT_VALUES_HANDLER, times(1)).getTypeHandler(TYPE_2);
        verify(MOCK_STRING_HANDLER, times(1)).read(VALUE_1);
        verify(MOCK_INTEGER_HANDLER, times(1)).read(VALUE_2.toString());
    }

    @Test
    public void testReadWithInvalidParameters() {
        assertThrows(IllegalArgumentException.class, () -> pairHandler.read(null));
        assertThrows(IllegalArgumentException.class, () -> pairHandler.read(""));
    }

    @Test
    public void testGenerateArchetype() {
        var archetype1type = randomString();
        var archetype2type = randomString();
        var archetype1 = randomString();
        Integer archetype2 = randomInt();
        when(MOCK_PERSISTENT_VALUES_HANDLER.generateArchetype(archetype1type)).thenReturn(
                archetype1);
        when(MOCK_PERSISTENT_VALUES_HANDLER.generateArchetype(archetype2type)).thenReturn(
                archetype2);

        //noinspection unchecked
        Pair<String, Integer> generatedArchetype =
                pairHandler.generateArchetype(archetype1type, archetype2type);

        assertNotNull(generatedArchetype);
        assertNotNull(generatedArchetype.firstArchetype());
        assertNotNull(generatedArchetype.secondArchetype());
        assertEquals(archetype1, generatedArchetype.firstArchetype());
        assertEquals(archetype2, generatedArchetype.secondArchetype());
    }

    @Test
    public void testGenerateArchetypeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                pairHandler.generateArchetype(null, String.class.getCanonicalName()));
        assertThrows(IllegalArgumentException.class, () ->
                pairHandler.generateArchetype("", String.class.getCanonicalName()));
        assertThrows(IllegalArgumentException.class, () ->
                pairHandler.generateArchetype(String.class.getCanonicalName(), null));
        assertThrows(IllegalArgumentException.class, () ->
                pairHandler.generateArchetype(String.class.getCanonicalName(), ""));
    }
}
