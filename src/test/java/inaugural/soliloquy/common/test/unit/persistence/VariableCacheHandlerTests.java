package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.VariableCacheHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Pair;

import java.util.ArrayList;
import java.util.Map;

import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static inaugural.soliloquy.tools.testing.Mock.generateMockPersistentValuesHandlerWithSimpleHandlers;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VariableCacheHandlerTests {
    private final String VARIABLE_1_NAME = randomString();
    private final Integer VARIABLE_1_VALUE = randomInt();
    private final String VARIABLE_2_NAME = randomString();
    private final String VARIABLE_2_VALUE = randomString();
    private final String VALUES_STRING = String.format(
            "[{\"name\":\"%s\",\"typeName\":\"java.lang.Integer\",\"serializedValue\":\"%d\"}," +
                    "{\"name\":\"%s\",\"typeName\":\"java.lang.String\"," +
                    "\"serializedValue\":\"%s\"}]",
            VARIABLE_1_NAME, VARIABLE_1_VALUE, VARIABLE_2_NAME, VARIABLE_2_VALUE);

    @SuppressWarnings("rawtypes")
    private final Pair<PersistentValuesHandler, Map<String, TypeHandler>>
            MOCK_PERSISTENT_VALUES_HANDLER_AND_TYPE_HANDLERS =
            generateMockPersistentValuesHandlerWithSimpleHandlers(
                    new Integer[]{VARIABLE_1_VALUE},
                    new String[]{VARIABLE_2_VALUE});
    private final PersistentValuesHandler MOCK_PERSISTENT_VALUES_HANDLER =
            MOCK_PERSISTENT_VALUES_HANDLER_AND_TYPE_HANDLERS.getItem1();
    @SuppressWarnings("rawtypes") private final TypeHandler MOCK_STRING_HANDLER =
            MOCK_PERSISTENT_VALUES_HANDLER_AND_TYPE_HANDLERS.getItem2()
                    .get(String.class.getCanonicalName());
    @SuppressWarnings("rawtypes") private final TypeHandler MOCK_INTEGER_HANDLER =
            MOCK_PERSISTENT_VALUES_HANDLER_AND_TYPE_HANDLERS.getItem2()
                    .get(Integer.class.getCanonicalName());
    @Mock private VariableCache mockVariableCache;
    @Mock private VariableCacheFactory mockVariableCacheFactory;

    private TypeHandler<VariableCache> variableCacheHandler;

    @BeforeEach
    void setUp() {
        mockVariableCache = mock(VariableCache.class);
        mockVariableCacheFactory = mock(VariableCacheFactory.class);
        when(mockVariableCacheFactory.make()).thenReturn(mockVariableCache);

        variableCacheHandler = new VariableCacheHandler(
                MOCK_PERSISTENT_VALUES_HANDLER,
                mockVariableCacheFactory);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new VariableCacheHandler(null, mockVariableCacheFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new VariableCacheHandler(MOCK_PERSISTENT_VALUES_HANDLER, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        VariableCache.class.getCanonicalName() + ">",
                variableCacheHandler.getInterfaceName());
    }

    @Test
    void testWrite() {
        VariableCache variableCache = mock(VariableCache.class);
        when(variableCache.namesRepresentation()).thenReturn(new ArrayList<>() {{
            add(VARIABLE_1_NAME);
            add(VARIABLE_2_NAME);
        }});
        when(variableCache.getVariable(VARIABLE_1_NAME)).thenReturn(VARIABLE_1_VALUE);
        when(variableCache.getVariable(VARIABLE_2_NAME)).thenReturn(VARIABLE_2_VALUE);

        String output = variableCacheHandler.write(variableCache);

        assertEquals(VALUES_STRING, output);
        verify(variableCache, times(1)).namesRepresentation();
        verify(variableCache, times(1)).getVariable(VARIABLE_1_NAME);
        verify(variableCache, times(1)).getVariable(VARIABLE_2_NAME);
        verify(MOCK_PERSISTENT_VALUES_HANDLER, times(1))
                .getTypeHandler(String.class.getCanonicalName());
        verify(MOCK_PERSISTENT_VALUES_HANDLER, times(1))
                .getTypeHandler(Integer.class.getCanonicalName());
        //noinspection unchecked
        verify(MOCK_INTEGER_HANDLER, times(1)).write(VARIABLE_1_VALUE);
        //noinspection unchecked
        verify(MOCK_STRING_HANDLER, times(1)).write(VARIABLE_2_VALUE);
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> variableCacheHandler.write(null));
    }

    @Test
    void testRead() {
        VariableCache readValue = variableCacheHandler.read(VALUES_STRING);

        assertNotNull(readValue);
        assertSame(mockVariableCache, readValue);
        verify(mockVariableCacheFactory, times(1)).make();
        verify(MOCK_PERSISTENT_VALUES_HANDLER, times(1))
                .getTypeHandler(String.class.getCanonicalName());
        verify(MOCK_PERSISTENT_VALUES_HANDLER, times(1))
                .getTypeHandler(Integer.class.getCanonicalName());
        verify(MOCK_INTEGER_HANDLER, times(1)).read(VARIABLE_1_VALUE.toString());
        verify(MOCK_STRING_HANDLER, times(1)).read(VARIABLE_2_VALUE);
        verify(mockVariableCache, times(1)).setVariable(VARIABLE_1_NAME, VARIABLE_1_VALUE);
        verify(mockVariableCache, times(1)).setVariable(VARIABLE_2_NAME, VARIABLE_2_VALUE);
    }

    @Test
    void testReadEmptyCache() {
        VariableCache pVarCache =
                variableCacheHandler.read("[]");

        assertNotNull(pVarCache);
        assertEquals(0, pVarCache.size());
    }

    @Test
    void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> variableCacheHandler.read(null));
        assertThrows(IllegalArgumentException.class,
                () -> variableCacheHandler.read(""));
    }

    @Test
    void testHashCode() {
        assertEquals((TypeHandler.class.getCanonicalName() + "<" +
                        VariableCache.class.getCanonicalName() + ">").hashCode(),
                variableCacheHandler.hashCode());
    }

    @Test
    void testEquals() {
        TypeHandler<VariableCache> equalHandler = new VariableCacheHandler(
                MOCK_PERSISTENT_VALUES_HANDLER, mockVariableCacheFactory);
        //noinspection unchecked
        TypeHandler<VariableCache> unequalHandler = mock(TypeHandler.class);

        assertEquals(variableCacheHandler, equalHandler);
        assertNotEquals(variableCacheHandler, unequalHandler);
        assertNotEquals(null, variableCacheHandler);
    }

    @Test
    void testToString() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        VariableCache.class.getCanonicalName() + ">",
                variableCacheHandler.toString());
    }
}
