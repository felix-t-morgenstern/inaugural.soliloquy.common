package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.ListHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Pair;

import java.util.Map;

import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.testing.Mock.generateMockPersistentValuesHandlerWithSimpleHandlers;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ListHandlerTests {
    private final Integer INTEGER_1 = randomInt();
    private final Integer INTEGER_2 = randomInt();
    private final Integer INTEGER_3 = randomInt();
    private final String VALUES_STRING =
            String.format("{\"typeName\":\"%s\",\"serializedValues\":[\"%d\",\"%d\",\"%d\"]}",
                    Integer.class.getCanonicalName(),
                    INTEGER_1, INTEGER_2, INTEGER_3);
    private final Integer GENERATED_ARCHETYPE = randomInt();
    @SuppressWarnings("rawtypes")
    private final Pair<PersistentValuesHandler, Map<String, TypeHandler>>
            MOCK_PERSISTENT_VALUES_HANDLER_AND_TYPE_HANDLERS =
            generateMockPersistentValuesHandlerWithSimpleHandlers(
                    new Integer[]{INTEGER_1, INTEGER_2, INTEGER_3});
    @SuppressWarnings("rawtypes") private final TypeHandler MOCK_INTEGER_HANDLER =
            MOCK_PERSISTENT_VALUES_HANDLER_AND_TYPE_HANDLERS.getItem2()
                    .get(Integer.class.getCanonicalName());
    private final PersistentValuesHandler MOCK_PERSISTENT_VALUES_HANDLER =
            MOCK_PERSISTENT_VALUES_HANDLER_AND_TYPE_HANDLERS.getItem1();

    @SuppressWarnings("rawtypes") @Mock private List mockList;
    @Mock private ListFactory mockListFactory;

    private ListHandler listHandler;

    @BeforeEach
    void setUp() {
        mockList = mock(List.class);
        when(mockList.getArchetype()).thenReturn(new Object());

        mockListFactory = mock(ListFactory.class);
        //noinspection unchecked
        when(mockListFactory.make(any())).thenReturn(mockList);

        when(MOCK_PERSISTENT_VALUES_HANDLER.generateArchetype(anyString()))
                .thenReturn(GENERATED_ARCHETYPE);

        listHandler = new ListHandler(MOCK_PERSISTENT_VALUES_HANDLER, mockListFactory);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new ListHandler(null, mockListFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new ListHandler(MOCK_PERSISTENT_VALUES_HANDLER, null));
    }

    @Test
    void testWrite() {
        when(mockList.size()).thenReturn(3);
        when(mockList.get(0)).thenReturn(INTEGER_1);
        when(mockList.get(1)).thenReturn(INTEGER_2);
        when(mockList.get(2)).thenReturn(INTEGER_3);
        when(mockList.getArchetype()).thenReturn(randomInt());

        String output = listHandler.write(mockList);

        assertEquals(VALUES_STRING, output);
        verify(MOCK_PERSISTENT_VALUES_HANDLER, times(1))
                .getTypeHandler(Integer.class.getCanonicalName());
        //noinspection unchecked
        verify(MOCK_INTEGER_HANDLER, times(1)).write(INTEGER_1);
        //noinspection unchecked
        verify(MOCK_INTEGER_HANDLER, times(1)).write(INTEGER_2);
        //noinspection unchecked
        verify(MOCK_INTEGER_HANDLER, times(1)).write(INTEGER_3);
    }

    @Test
    void testWriteNull() {
        assertThrows(IllegalArgumentException.class,
                () -> listHandler.write(null));
    }

    @SuppressWarnings("unchecked")
    @Test
    void testRead() {
        List<Integer> list = listHandler.read(VALUES_STRING);

        assertNotNull(list);
        assertSame(mockList, list);
        verify(MOCK_PERSISTENT_VALUES_HANDLER, times(1))
                .generateArchetype(Integer.class.getCanonicalName());
        verify(MOCK_PERSISTENT_VALUES_HANDLER, times(1))
                .getTypeHandler(Integer.class.getCanonicalName());
        verify(MOCK_INTEGER_HANDLER, times(1)).read(INTEGER_1.toString());
        verify(MOCK_INTEGER_HANDLER, times(1)).read(INTEGER_2.toString());
        verify(MOCK_INTEGER_HANDLER, times(1)).read(INTEGER_3.toString());
        verify(mockList, times(1)).add(INTEGER_1);
        verify(mockList, times(1)).add(INTEGER_2);
        verify(mockList, times(1)).add(INTEGER_3);
    }

    @Test
    void testReadInvalidValues() {
        assertThrows(IllegalArgumentException.class,
                () -> listHandler.read(null));
        assertThrows(IllegalArgumentException.class,
                () -> listHandler.read(""));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        List.class.getCanonicalName() + ">",
                listHandler.getInterfaceName());
    }

    @Test
    void testGetArchetype() {
        assertNotNull(listHandler.getArchetype());
        assertEquals(List.class.getCanonicalName(),
                listHandler.getArchetype().getInterfaceName());
    }

    @Test
    void testGenerateArchetype() {
        //noinspection unchecked
        List<Integer> generatedArchetype = listHandler.generateArchetype(
                Integer.class.getCanonicalName());

        assertNotNull(generatedArchetype);
        assertNotNull(generatedArchetype.getArchetype());
    }

    @Test
    void testGenerateArchetypeWithInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () ->
                listHandler.generateArchetype(null));
        assertThrows(IllegalArgumentException.class, () ->
                listHandler.generateArchetype(""));
    }

    @Test
    void testHashCode() {
        assertEquals((TypeHandler.class.getCanonicalName() + "<" +
                        List.class.getCanonicalName() + ">").hashCode(),
                listHandler.hashCode());
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    void testEquals() {
        TypeHandler<List> equalHandler =
                new ListHandler(MOCK_PERSISTENT_VALUES_HANDLER, mockListFactory);
        TypeHandler<List> unequalHandler = mock(TypeHandler.class);

        assertEquals(listHandler, equalHandler);
        assertNotEquals(listHandler, unequalHandler);
        assertNotEquals(null, listHandler);
    }

    @Test
    void testToString() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        List.class.getCanonicalName() + ">",
                listHandler.toString());
    }
}
