package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.PairHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Pair;

import java.util.Map;

import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static inaugural.soliloquy.tools.testing.Mock.generateMockPersistentValuesHandlerWithSimpleHandlers;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PairHandlerTests {
    private final String VALUE_1_TYPE = String.class.getCanonicalName();
    private final String VALUE_1 = randomString();
    private final String VALUE_2_TYPE = Integer.class.getCanonicalName();
    private final Integer VALUE_2 = randomInt();
    private final String VALUES_STRING = String.format(
            "{\"valueType1\":\"%s\",\"serializedValue1\":\"%s\",\"valueType2\":\"%s\"," +
                    "\"serializedValue2\":\"%d\"}",
            VALUE_1_TYPE, VALUE_1, VALUE_2_TYPE, VALUE_2);

    @SuppressWarnings("rawtypes")
    private final Pair<PersistentValuesHandler, Map<String, TypeHandler>>
            MOCK_PERSISTENT_VALUES_HANDLER_AND_TYPE_HANDLERS =
            generateMockPersistentValuesHandlerWithSimpleHandlers(
                    new String[]{VALUE_1},
                    new Integer[]{VALUE_2});
    private final PersistentValuesHandler MOCK_PERSISTENT_VALUES_HANDLER =
            MOCK_PERSISTENT_VALUES_HANDLER_AND_TYPE_HANDLERS.getItem1();
    @SuppressWarnings("rawtypes") private final TypeHandler MOCK_STRING_HANDLER =
            MOCK_PERSISTENT_VALUES_HANDLER_AND_TYPE_HANDLERS.getItem2()
                    .get(String.class.getCanonicalName());
    @SuppressWarnings("rawtypes") private final TypeHandler MOCK_INTEGER_HANDLER =
            MOCK_PERSISTENT_VALUES_HANDLER_AND_TYPE_HANDLERS.getItem2()
                    .get(Integer.class.getCanonicalName());

    private PairHandler pairHandler;

    @BeforeEach
    void setUp() {
        pairHandler = new PairHandler(MOCK_PERSISTENT_VALUES_HANDLER);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new PairHandler(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Pair.class.getCanonicalName() + ">",
                pairHandler.getInterfaceName());
    }

    @Test
    void testGetArchetype() {
        assertNotNull(pairHandler.getArchetype());
        assertEquals(Pair.class.getCanonicalName(),
                pairHandler.getArchetype().getInterfaceName());
    }

    @Test
    void testWrite() {
        Pair<String, Integer> pair = new Pair<>(VALUE_1, VALUE_2);

        String output = pairHandler.write(pair);

        assertEquals(VALUES_STRING, output);
        verify(MOCK_PERSISTENT_VALUES_HANDLER, times(1)).getTypeHandler(VALUE_1_TYPE);
        verify(MOCK_PERSISTENT_VALUES_HANDLER, times(1)).getTypeHandler(VALUE_2_TYPE);
        //noinspection unchecked
        verify(MOCK_STRING_HANDLER, times(1)).write(VALUE_1);
        //noinspection unchecked
        verify(MOCK_INTEGER_HANDLER, times(1)).write(VALUE_2);
    }

    @Test
    void testWriteNull() {
        assertThrows(IllegalArgumentException.class, () -> pairHandler.write(null));
    }

    @SuppressWarnings("unchecked")
    @Test
    void testRead() {
        Pair<String, Integer> pair = pairHandler.read(VALUES_STRING);

        assertNotNull(pair);
        assertEquals(VALUE_1, pair.getItem1());
        assertEquals(VALUE_2, pair.getItem2());
        verify(MOCK_PERSISTENT_VALUES_HANDLER, times(1)).getTypeHandler(VALUE_1_TYPE);
        verify(MOCK_PERSISTENT_VALUES_HANDLER, times(1)).getTypeHandler(VALUE_2_TYPE);
        verify(MOCK_STRING_HANDLER, times(1)).read(VALUE_1);
        verify(MOCK_INTEGER_HANDLER, times(1)).read(VALUE_2.toString());
    }

    @Test
    void testReadWithInvalidParameters() {
        assertThrows(IllegalArgumentException.class, () -> pairHandler.read(null));
        assertThrows(IllegalArgumentException.class, () -> pairHandler.read(""));
    }

    @Test
    void testGenerateArchetype() {
        String archetype1type = randomString();
        String archetype2type = randomString();
        String archetype1 = randomString();
        Integer archetype2 = randomInt();
        when(MOCK_PERSISTENT_VALUES_HANDLER.generateArchetype(archetype1type)).thenReturn(archetype1);
        when(MOCK_PERSISTENT_VALUES_HANDLER.generateArchetype(archetype2type)).thenReturn(archetype2);

        //noinspection unchecked
        Pair<String, Integer> generatedArchetype = pairHandler.generateArchetype(archetype1type, archetype2type);

        assertNotNull(generatedArchetype);
        assertNotNull(generatedArchetype.getFirstArchetype());
        assertNotNull(generatedArchetype.getSecondArchetype());
        assertEquals(archetype1, generatedArchetype.getFirstArchetype());
        assertEquals(archetype2, generatedArchetype.getSecondArchetype());
    }

    @Test
    void testGenerateArchetypeWithInvalidParams() {
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
