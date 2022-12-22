package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.MapHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Pair;

import java.util.ArrayList;
import java.util.Set;

import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static inaugural.soliloquy.tools.testing.Mock.generateMockPersistentValuesHandlerWithSimpleHandlers;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MapHandlerTests {
    private final String KEY_1 = randomString();
    private final String KEY_2 = randomString();
    private final String KEY_3 = randomString();
    private final Integer VALUE_1 = randomInt();
    private final Integer VALUE_2 = randomInt();
    private final Integer VALUE_3 = randomInt();
    private final String KEY_ARCHETYPE = randomString();
    private final Integer VALUE_ARCHETYPE = randomInt();
    @SuppressWarnings("rawtypes")
    private final Pair<PersistentValuesHandler, java.util.Map<String, TypeHandler>>
            MOCK_PERSISTENT_VALUES_HANDLER_AND_TYPE_HANDLERS =
            generateMockPersistentValuesHandlerWithSimpleHandlers(
                    new String[]{KEY_1, KEY_2, KEY_3},
                    new Integer[]{VALUE_1, VALUE_2, VALUE_3});
    private final PersistentValuesHandler MOCK_PERSISTENT_VALUES_HANDLER =
            MOCK_PERSISTENT_VALUES_HANDLER_AND_TYPE_HANDLERS.getItem1();
    @SuppressWarnings("rawtypes") private final TypeHandler MOCK_STRING_HANDLER =
            MOCK_PERSISTENT_VALUES_HANDLER_AND_TYPE_HANDLERS.getItem2()
                    .get(String.class.getCanonicalName());
    @SuppressWarnings("rawtypes") private final TypeHandler MOCK_INTEGER_HANDLER =
            MOCK_PERSISTENT_VALUES_HANDLER_AND_TYPE_HANDLERS.getItem2()
                    .get(Integer.class.getCanonicalName());
    private final String VALUES_STRING = String.format(
            "{\"keyValueType\":\"%s\",\"valueValueType\":\"%s\"," +
                    "\"keySerializedValues\":[\"%s\",\"%s\",\"%s\"]," +
                    "\"valueSerializedValues\":[\"%d\",\"%d\",\"%d\"]}",
            String.class.getCanonicalName(), Integer.class.getCanonicalName(),
            KEY_1, KEY_2, KEY_3, VALUE_1, VALUE_2, VALUE_3);

    @SuppressWarnings("rawtypes") @Mock private Map mockMap;
    @Mock private MapFactory mockMapFactory;

    private MapHandler mapHandler;

    @BeforeEach
    void setUp() {
        mockMap = mock(Map.class);

        mockMapFactory = mock(MapFactory.class);
        //noinspection unchecked
        when(mockMapFactory.make(any(), any())).thenReturn(mockMap);

        when(MOCK_PERSISTENT_VALUES_HANDLER.generateArchetype(String.class.getCanonicalName()))
                .thenReturn(KEY_ARCHETYPE);
        when(MOCK_PERSISTENT_VALUES_HANDLER.generateArchetype(Integer.class.getCanonicalName()))
                .thenReturn(VALUE_ARCHETYPE);

        mapHandler = new MapHandler(MOCK_PERSISTENT_VALUES_HANDLER, mockMapFactory);
    }

    @Test
    void testWrite() {
        when(mockMap.getFirstArchetype()).thenReturn(KEY_ARCHETYPE);
        when(mockMap.getSecondArchetype()).thenReturn(VALUE_ARCHETYPE);
        when(mockMap.size()).thenReturn(3);
        // NB: Making a list just to get its iterator is done to ensure that the keys are read in
        // order to guarantee an output that matches VALUES_STRING; Set iterators are indeterminate
        ArrayList<String> keyList = new ArrayList<>() {{
            add(KEY_1);
            add(KEY_2);
            add(KEY_3);
        }};
        //noinspection unchecked
        Set<String> mockKeySet = mock(Set.class);
        when(mockKeySet.iterator()).thenReturn(keyList.iterator());
        when(mockMap.keySet()).thenReturn(mockKeySet);
        when(mockMap.get(KEY_1)).thenReturn(VALUE_1);
        when(mockMap.get(KEY_2)).thenReturn(VALUE_2);
        when(mockMap.get(KEY_3)).thenReturn(VALUE_3);

        String output = mapHandler.write(mockMap);

        assertEquals(VALUES_STRING, output);
        verify(MOCK_PERSISTENT_VALUES_HANDLER, times(1))
                .getTypeHandler(String.class.getCanonicalName());
        verify(MOCK_PERSISTENT_VALUES_HANDLER, times(1))
                .getTypeHandler(Integer.class.getCanonicalName());
        //noinspection unchecked
        verify(MOCK_STRING_HANDLER, times(1)).write(KEY_1);
        //noinspection unchecked
        verify(MOCK_STRING_HANDLER, times(1)).write(KEY_2);
        //noinspection unchecked
        verify(MOCK_STRING_HANDLER, times(1)).write(KEY_3);
        //noinspection unchecked
        verify(MOCK_INTEGER_HANDLER, times(1)).write(VALUE_1);
        //noinspection unchecked
        verify(MOCK_INTEGER_HANDLER, times(1)).write(VALUE_2);
        //noinspection unchecked
        verify(MOCK_INTEGER_HANDLER, times(1)).write(VALUE_3);
    }

    @Test
    void testWriteNull() {
        assertThrows(IllegalArgumentException.class,
                () -> mapHandler.write(null));
    }

    @SuppressWarnings("unchecked")
    @Test
    void testRead() {
        Map<String, Integer> map = mapHandler.read(VALUES_STRING);

        assertSame(mockMap, map);
        verify(MOCK_PERSISTENT_VALUES_HANDLER, times(1))
                .getTypeHandler(String.class.getCanonicalName());
        verify(MOCK_PERSISTENT_VALUES_HANDLER, times(1))
                .getTypeHandler(Integer.class.getCanonicalName());
        verify(MOCK_PERSISTENT_VALUES_HANDLER, times(1))
                .getTypeHandler(String.class.getCanonicalName());
        verify(MOCK_PERSISTENT_VALUES_HANDLER, times(1))
                .getTypeHandler(Integer.class.getCanonicalName());
        verify(mockMapFactory, times(1)).make(KEY_ARCHETYPE, VALUE_ARCHETYPE);
        verify(MOCK_STRING_HANDLER, times(1)).read(KEY_1);
        verify(MOCK_STRING_HANDLER, times(1)).read(KEY_2);
        verify(MOCK_STRING_HANDLER, times(1)).read(KEY_3);
        verify(MOCK_INTEGER_HANDLER, times(1)).read(VALUE_1.toString());
        verify(MOCK_INTEGER_HANDLER, times(1)).read(VALUE_2.toString());
        verify(MOCK_INTEGER_HANDLER, times(1)).read(VALUE_3.toString());
        verify(mockMap, times(1)).put(KEY_1, VALUE_1);
        verify(mockMap, times(1)).put(KEY_2, VALUE_2);
        verify(mockMap, times(1)).put(KEY_3, VALUE_3);
    }

    @Test
    void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> mapHandler.read(null));
        assertThrows(IllegalArgumentException.class, () -> mapHandler.read(""));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Map.class.getCanonicalName() + ">",
                mapHandler.getInterfaceName());
    }

    @Test
    void testGetArchetype() {
        assertNotNull(mapHandler.getArchetype());
        assertEquals(Map.class.getCanonicalName(), mapHandler.getArchetype().getInterfaceName());
    }

    @Test
    void testGenerateArchetype() {
        //noinspection unchecked
        Map<String, Integer> generatedArchetype = mapHandler.generateArchetype(
                String.class.getCanonicalName(), Integer.class.getCanonicalName());

        assertNotNull(generatedArchetype);
        assertSame(mockMap, generatedArchetype);
        verify(mockMapFactory, times(1)).make(KEY_ARCHETYPE, VALUE_ARCHETYPE);
    }

    @Test
    void testGenerateArchetypeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                mapHandler.generateArchetype(null, Integer.class.getCanonicalName()));
        assertThrows(IllegalArgumentException.class, () ->
                mapHandler.generateArchetype("", Integer.class.getCanonicalName()));
        assertThrows(IllegalArgumentException.class, () ->
                mapHandler.generateArchetype(Integer.class.getCanonicalName(), null));
        assertThrows(IllegalArgumentException.class, () ->
                mapHandler.generateArchetype(Integer.class.getCanonicalName(), ""));
    }

    @Test
    void testHashCode() {
        assertEquals((TypeHandler.class.getCanonicalName() + "<" +
                        Map.class.getCanonicalName() + ">").hashCode(),
                mapHandler.hashCode());
    }

    @SuppressWarnings("rawtypes")
    @Test
    void testEquals() {
        TypeHandler<Map> equalHandler =
                new MapHandler(MOCK_PERSISTENT_VALUES_HANDLER, mockMapFactory);
        @SuppressWarnings("unchecked") TypeHandler<Map> unequalHandler = mock(TypeHandler.class);

        assertEquals(mapHandler, equalHandler);
        assertNotEquals(mapHandler, unequalHandler);
        assertNotEquals(null, mapHandler);
    }

    @Test
    void testToString() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Map.class.getCanonicalName() + ">",
                mapHandler.toString());
    }
}
