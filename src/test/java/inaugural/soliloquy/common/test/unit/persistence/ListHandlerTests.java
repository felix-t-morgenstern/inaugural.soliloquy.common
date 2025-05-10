package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.ListHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Pair;

import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.arrayOf;
import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.testing.Mock.generateMockPersistentValuesHandlerWithSimpleHandlers;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListHandlerTests {
    private final Integer INTEGER_1 = randomInt();
    private final Integer INTEGER_2 = randomInt();
    private final Integer INTEGER_3 = randomInt();
    private final String VALUES_STRING =
            String.format("{\"type\":\"%s\",\"values\":[\"%d\",\"%d\",\"%d\"]}",
                    Integer.class.getCanonicalName(),
                    INTEGER_1, INTEGER_2, INTEGER_3);
    private final Integer GENERATED_ARCHETYPE = randomInt();
    @SuppressWarnings("rawtypes")
    private final Pair<PersistentValuesHandler, Map<String, TypeHandler>>
            MOCK_PERSISTENT_VALUES_HANDLER_AND_TYPE_HANDLERS =
            generateMockPersistentValuesHandlerWithSimpleHandlers(
                    arrayOf(INTEGER_1, INTEGER_2, INTEGER_3));
    @SuppressWarnings("rawtypes") private final TypeHandler MOCK_INTEGER_HANDLER =
            MOCK_PERSISTENT_VALUES_HANDLER_AND_TYPE_HANDLERS.item2()
                    .get(Integer.class.getCanonicalName());
    private final PersistentValuesHandler MOCK_PERSISTENT_VALUES_HANDLER =
            MOCK_PERSISTENT_VALUES_HANDLER_AND_TYPE_HANDLERS.item1();

    @SuppressWarnings("rawtypes") @Mock private List mockList;
    @Mock private ListFactory mockListFactory;

    private ListHandler listHandler;

    @BeforeEach
    public void setUp() {
        lenient().when(mockList.archetype()).thenReturn(new Object());

        mockListFactory = mock(ListFactory.class);
        //noinspection unchecked
        lenient().when(mockListFactory.make(any())).thenReturn(mockList);

        lenient().when(MOCK_PERSISTENT_VALUES_HANDLER.generateArchetype(anyString()))
                .thenReturn(GENERATED_ARCHETYPE);

        listHandler = new ListHandler(MOCK_PERSISTENT_VALUES_HANDLER, mockListFactory);
    }

    @Test
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new ListHandler(null, mockListFactory));
        assertThrows(IllegalArgumentException.class,
                () -> new ListHandler(MOCK_PERSISTENT_VALUES_HANDLER, null));
    }

    @Test
    public void testWrite() {
        when(mockList.size()).thenReturn(3);
        when(mockList.get(0)).thenReturn(INTEGER_1);
        when(mockList.get(1)).thenReturn(INTEGER_2);
        when(mockList.get(2)).thenReturn(INTEGER_3);
        when(mockList.archetype()).thenReturn(randomInt());

        var output = listHandler.write(mockList);

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
    public void testWriteNull() {
        assertThrows(IllegalArgumentException.class,
                () -> listHandler.write(null));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testRead() {
        var list = listHandler.read(VALUES_STRING);

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
    public void testReadInvalidValues() {
        assertThrows(IllegalArgumentException.class,
                () -> listHandler.read(null));
        assertThrows(IllegalArgumentException.class,
                () -> listHandler.read(""));
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        List.class.getCanonicalName() + ">",
                listHandler.getInterfaceName());
    }

    @Test
    public void testArchetype() {
        assertNotNull(listHandler.archetype());
        assertEquals(List.class.getCanonicalName(),
                listHandler.archetype().getInterfaceName());
    }

    @Test
    public void testGenerateArchetype() {
        var generatedArchetype = listHandler.generateArchetype(Integer.class.getCanonicalName());

        assertNotNull(generatedArchetype);
        assertNotNull(generatedArchetype.archetype());
    }

    @Test
    public void testGenerateArchetypeWithInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () ->
                listHandler.generateArchetype(null));
        assertThrows(IllegalArgumentException.class, () ->
                listHandler.generateArchetype(""));
    }

    @Test
    public void testHashCode() {
        assertEquals((TypeHandler.class.getCanonicalName() + "<" +
                        List.class.getCanonicalName() + ">").hashCode(),
                listHandler.hashCode());
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    public void testEquals() {
        var equalHandler = new ListHandler(MOCK_PERSISTENT_VALUES_HANDLER, mockListFactory);
        TypeHandler<List> unequalHandler = mock(TypeHandler.class);

        assertEquals(listHandler, equalHandler);
        assertNotEquals(listHandler, unequalHandler);
        assertNotEquals(null, listHandler);
    }

    @Test
    public void testToString() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        List.class.getCanonicalName() + ">",
                listHandler.toString());
    }
}
