package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.MapHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentest4j.AssertionFailedError;
import soliloquy.specs.common.persistence.PersistenceHandler;
import soliloquy.specs.common.persistence.TypeHandler;

import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.mapOf;
import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static soliloquy.specs.common.valueobjects.Pair.pairOf;

@ExtendWith(MockitoExtension.class)
public class MapHandlerTests {
    private final String KEY_1 = randomString();
    private final String KEY_2 = randomString();
    private final Integer VALUE_1 = randomInt();
    private final Integer VALUE_2 = randomInt();
    private final String MAP_STRING_FORMAT = "{\"keyType\":%s,\"valueType\":%s,\"keys\":[%s,%s]," +
            "\"values\":[%s,%s]}";
    private final String MAP_STRING = String.format(
            "{\"keyType\":\"%s\",\"valueType\":\"%s\",\"keys\":[\"%s\",\"%s\"]," +
                    "\"values\":[\"%d\",\"%d\"]}",
            String.class.getCanonicalName(), Integer.class.getCanonicalName(),
            KEY_1, KEY_2, VALUE_1, VALUE_2);
    private final String MAP_STRING_SECOND_KEY_NULL = String.format(
            "{\"keyType\":\"%s\",\"valueType\":\"%s\",\"keys\":[\"%s\",null]," +
                    "\"values\":[\"%d\",\"%d\"]}",
            String.class.getCanonicalName(), Integer.class.getCanonicalName(),
            KEY_1, VALUE_1, VALUE_2);
    private final String MAP_STRING_ALL_NULL_KEYS = String.format(
            "{\"valueType\":\"%s\",\"keys\":[null],\"values\":[\"%d\"]}",
            Integer.class.getCanonicalName(), VALUE_1);
    private final String MAP_STRING_OPPOSITE_NULL_KEY_AND_VALUE = String.format(
            "{\"keyType\":\"%s\",\"valueType\":\"%s\",\"keys\":[\"%s\",null],\"values\":[null," +
                    "\"%d\"]}",
            String.class.getCanonicalName(), Integer.class.getCanonicalName(),
            KEY_1, VALUE_2);

    @SuppressWarnings("rawtypes") private TypeHandler mockTypeHandler1;
    @SuppressWarnings("rawtypes") private TypeHandler mockTypeHandler2;

    @Mock private PersistenceHandler mockPersistenceHandler;

    private MapHandler handler;

    @BeforeEach
    public void setUp() {
        mockTypeHandler1 = passthroughMockHandler();
        mockTypeHandler2 = passthroughMockHandler();

        //noinspection unchecked
        lenient().when(mockPersistenceHandler.getTypeHandler(anyString()))
                .thenReturn(mockTypeHandler1).thenReturn(mockTypeHandler2);

        handler = new MapHandler(mockPersistenceHandler);
    }

    @SuppressWarnings("rawtypes")
    private TypeHandler passthroughMockHandler() {
        var mockTypeHandler = mock(TypeHandler.class);
        //noinspection unchecked
        lenient().when(mockTypeHandler.write(any()))
                .thenAnswer(invocation -> invocation.getArgument(0).toString());
        lenient().when(mockTypeHandler.read(anyString()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        return mockTypeHandler;
    }

    @Test
    public void testTypeHandled() {
        assertEquals(Map.class.getCanonicalName(), handler.typeHandled());
    }

    @Test
    public void testWrite() {
        var output = handler.write(mapOf(
                pairOf(KEY_1, VALUE_1),
                pairOf(KEY_2, VALUE_2)
        ));

        assertMapOutputStringMatches(output, MAP_STRING_FORMAT,
                String.class.getCanonicalName(), Integer.class.getCanonicalName(),
                KEY_1, KEY_2, VALUE_1, VALUE_2);
        verify(mockPersistenceHandler, times(1)).getTypeHandler(String.class.getCanonicalName());
        verify(mockPersistenceHandler, times(1)).getTypeHandler(Integer.class.getCanonicalName());
        //noinspection unchecked
        verify(mockTypeHandler1, times(2)).write(any());
        //noinspection unchecked
        verify(mockTypeHandler1, times(1)).write(KEY_1);
        //noinspection unchecked
        verify(mockTypeHandler1, times(1)).write(KEY_2);
        //noinspection unchecked
        verify(mockTypeHandler2, times(2)).write(any());
        //noinspection unchecked
        verify(mockTypeHandler2, times(1)).write(VALUE_1);
        //noinspection unchecked
        verify(mockTypeHandler2, times(1)).write(VALUE_2);
    }

    @Test
    public void testWriteWithNullKey() {
        var output = handler.write(mapOf(
                pairOf(KEY_1, VALUE_1),
                pairOf(null, VALUE_2)
        ));

        assertMapOutputStringMatches(output, MAP_STRING_FORMAT,
                String.class.getCanonicalName(), Integer.class.getCanonicalName(),
                KEY_1, null, VALUE_1, VALUE_2);
        verify(mockPersistenceHandler, times(1)).getTypeHandler(String.class.getCanonicalName());
        verify(mockPersistenceHandler, times(1)).getTypeHandler(Integer.class.getCanonicalName());
        //noinspection unchecked
        verify(mockTypeHandler1, times(1)).write(any());
        //noinspection unchecked
        verify(mockTypeHandler1, times(1)).write(KEY_1);
        //noinspection unchecked
        verify(mockTypeHandler2, times(2)).write(any());
        //noinspection unchecked
        verify(mockTypeHandler2, times(1)).write(VALUE_1);
        //noinspection unchecked
        verify(mockTypeHandler2, times(1)).write(VALUE_2);
    }

    @Test
    public void testWriteWithAllNullKeys() {
        var output = handler.write(mapOf(
                pairOf(null, VALUE_1)
        ));

        assertEquals(MAP_STRING_ALL_NULL_KEYS, output);
        verify(mockPersistenceHandler, times(1)).getTypeHandler(Integer.class.getCanonicalName());
        // (mockPersistenceHandler retrieves mockTypeHandler1 first, so it will be used to write
        // values.)
        //noinspection unchecked
        verify(mockTypeHandler2, never()).write(any());
        //noinspection unchecked
        verify(mockTypeHandler1, times(1)).write(any());
        //noinspection unchecked
        verify(mockTypeHandler1, times(1)).write(VALUE_1);
    }

    @Test
    public void testWriteWithNullValue() {
        var output = handler.write(mapOf(
                pairOf(KEY_1, VALUE_1),
                pairOf(KEY_2, null)
        ));

        assertMapOutputStringMatches(output, MAP_STRING_FORMAT,
                String.class.getCanonicalName(), Integer.class.getCanonicalName(),
                KEY_1, KEY_2, VALUE_1, null);
        verify(mockPersistenceHandler, times(1)).getTypeHandler(String.class.getCanonicalName());
        verify(mockPersistenceHandler, times(1)).getTypeHandler(Integer.class.getCanonicalName());
        //noinspection unchecked
        verify(mockTypeHandler1, times(2)).write(any());
        //noinspection unchecked
        verify(mockTypeHandler1, times(1)).write(KEY_1);
        //noinspection unchecked
        verify(mockTypeHandler1, times(1)).write(KEY_2);
        //noinspection unchecked
        verify(mockTypeHandler2, times(1)).write(any());
        //noinspection unchecked
        verify(mockTypeHandler2, times(1)).write(VALUE_1);
    }

    private String wrapNonNull(Object val) {
        return val == null ? "null" : String.format("\"%s\"", val);
    }

    @Test
    public void testWriteWithAllNullValues() {
        var output = handler.write(mapOf(
                pairOf(KEY_1, null),
                pairOf(KEY_2, null)
        ));

        String mapStringAllNullValuesFormat =
                "{\"keyType\":%s,\"keys\":[%s,%s],\"values\":[null,null]}";
        assertMapOutputStringMatches(output, mapStringAllNullValuesFormat,
                String.class.getCanonicalName(), null, KEY_1, KEY_2, null, null,
                true, false);
        verify(mockPersistenceHandler, times(1)).getTypeHandler(String.class.getCanonicalName());
        //noinspection unchecked
        verify(mockTypeHandler1, times(2)).write(any());
        //noinspection unchecked
        verify(mockTypeHandler1, times(1)).write(KEY_1);
        //noinspection unchecked
        verify(mockTypeHandler1, times(1)).write(KEY_2);
        //noinspection unchecked
        verify(mockTypeHandler2, never()).write(any());
    }

    @Test
    public void testWriteWithOppositeNullKeyAndValue() {
        var output = handler.write(mapOf(
                pairOf(KEY_1, null),
                pairOf(null, VALUE_2)
        ));

        assertMapOutputStringMatches(output, MAP_STRING_FORMAT, String.class.getCanonicalName(), Integer.class.getCanonicalName(), KEY_1, null, null, VALUE_2);
        verify(mockPersistenceHandler, times(1)).getTypeHandler(String.class.getCanonicalName());
        verify(mockPersistenceHandler, times(1)).getTypeHandler(Integer.class.getCanonicalName());
        //noinspection unchecked
        verify(mockTypeHandler1, times(1)).write(any());
        //noinspection unchecked
        verify(mockTypeHandler1, times(1)).write(KEY_1);
        //noinspection unchecked
        verify(mockTypeHandler2, times(1)).write(any());
        //noinspection unchecked
        verify(mockTypeHandler2, times(1)).write(VALUE_2);
    }

    private void assertMapOutputStringMatches(String output, String format,
                                              String keyType, String valType,
                                              String key1, String key2, Integer val1,
                                              Integer val2, boolean... includeTypes) {
        var keyTypeWrapped = wrapNonNull(keyType);
        var valTypeWrapped = wrapNonNull(valType);
        var key1Wrapped = wrapNonNull(key1);
        var key2Wrapped = wrapNonNull(key2);
        var val1Wrapped = wrapNonNull(val1);
        var val2Wrapped = wrapNonNull(val2);
        String expectedPermutation1;
        String expectedPermutation2;
        var includeKeyType = includeTypes == null || includeTypes.length < 1 || includeTypes[0];
        var includeValType = includeTypes == null || includeTypes.length < 2 || includeTypes[1];
        if (includeKeyType) {
            if (includeValType) {
                expectedPermutation1 = String.format(format, keyTypeWrapped, valTypeWrapped,
                        key1Wrapped, key2Wrapped, val1Wrapped, val2Wrapped);
                expectedPermutation2 = String.format(format, keyTypeWrapped, valTypeWrapped,
                        key2Wrapped, key1Wrapped, val2Wrapped, val1Wrapped);
            }
            else {
                expectedPermutation1 = String.format(format, keyTypeWrapped,
                        key1Wrapped, key2Wrapped, val1Wrapped, val2Wrapped);
                expectedPermutation2 = String.format(format, keyTypeWrapped,
                        key2Wrapped, key1Wrapped, val2Wrapped, val1Wrapped);
            }
        }
        else {
            if (includeValType) {
                expectedPermutation1 = String.format(format, valTypeWrapped,
                        key1Wrapped, key2Wrapped, val1Wrapped, val2Wrapped);
                expectedPermutation2 = String.format(format, valTypeWrapped,
                        key2Wrapped, key1Wrapped, val2Wrapped, val1Wrapped);
            }
            else {
                expectedPermutation1 = String.format(format,
                        key1Wrapped, key2Wrapped, val1Wrapped, val2Wrapped);
                expectedPermutation2 = String.format(format,
                        key2Wrapped, key1Wrapped, val2Wrapped, val1Wrapped);
            }
        }

        try {
            assertEquals(expectedPermutation1, output);
        }
        catch (AssertionFailedError e1) {
            assertEquals(expectedPermutation2, output);
        }
    }

    @Test
    public void testWriteWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> handler.write(null));
    }

    @Test
    public void testRead() {
        Map<String, Integer> output = handler.read(MAP_STRING);

        var expected = mapOf(
                pairOf(KEY_1, VALUE_1),
                pairOf(KEY_2, VALUE_2)
        );
        assertMapsEqual(expected, output);
        verify(mockPersistenceHandler, times(2)).getTypeHandler(anyString());
        verify(mockPersistenceHandler, times(1)).getTypeHandler(String.class.getCanonicalName());
        verify(mockPersistenceHandler, times(1)).getTypeHandler(Integer.class.getCanonicalName());
        verify(mockTypeHandler1, times(2)).read(anyString());
        verify(mockTypeHandler1, times(1)).read(eq(KEY_1));
        verify(mockTypeHandler1, times(1)).read(eq(KEY_2));
        verify(mockTypeHandler2, times(2)).read(anyString());
        verify(mockTypeHandler2, times(1)).read(eq(VALUE_1.toString()));
        verify(mockTypeHandler2, times(1)).read(eq(VALUE_2.toString()));
    }

    @Test
    public void testReadWithNullKey() {
        Map<String, Integer> output = handler.read(MAP_STRING_SECOND_KEY_NULL);

        var expected = mapOf(
                pairOf(KEY_1, VALUE_1),
                pairOf(null, VALUE_2)
        );
        assertMapsEqual(expected, output);
        verify(mockPersistenceHandler, times(2)).getTypeHandler(anyString());
        verify(mockPersistenceHandler, times(1)).getTypeHandler(String.class.getCanonicalName());
        verify(mockPersistenceHandler, times(1)).getTypeHandler(Integer.class.getCanonicalName());
        verify(mockTypeHandler1, times(1)).read(anyString());
        verify(mockTypeHandler1, times(1)).read(eq(KEY_1));
        verify(mockTypeHandler2, times(2)).read(anyString());
        verify(mockTypeHandler2, times(1)).read(eq(VALUE_1.toString()));
        verify(mockTypeHandler2, times(1)).read(eq(VALUE_2.toString()));
    }

    @Test
    public void testReadWithAllNullKeys() {
        Map<String, Integer> output = handler.read(MAP_STRING_ALL_NULL_KEYS);

        var expected = mapOf(
                pairOf(null, VALUE_1)
        );
        assertMapsEqual(expected, output);
        verify(mockPersistenceHandler, times(1)).getTypeHandler(anyString());
        verify(mockPersistenceHandler, times(1)).getTypeHandler(Integer.class.getCanonicalName());
        verify(mockTypeHandler1, times(1)).read(anyString());
        verify(mockTypeHandler1, times(1)).read(eq(VALUE_1.toString()));
        verify(mockTypeHandler2, never()).read(anyString());
    }

    @Test
    public void testReadWithOppositeNullKeyAndValue() {
        Map<String, Integer> output = handler.read(MAP_STRING_OPPOSITE_NULL_KEY_AND_VALUE);

        var expected = mapOf(
                pairOf(KEY_1, null),
                pairOf(null, VALUE_2)
        );
        assertMapsEqual(expected, output);
        verify(mockPersistenceHandler, times(2)).getTypeHandler(anyString());
        verify(mockPersistenceHandler, times(1)).getTypeHandler(String.class.getCanonicalName());
        verify(mockPersistenceHandler, times(1)).getTypeHandler(Integer.class.getCanonicalName());
        verify(mockTypeHandler1, times(1)).read(anyString());
        verify(mockTypeHandler1, times(1)).read(eq(KEY_1));
        verify(mockTypeHandler2, times(1)).read(anyString());
        verify(mockTypeHandler2, times(1)).read(eq(VALUE_2.toString()));
    }

    @SuppressWarnings("rawtypes")
    private void assertMapsEqual(Map expected, Map output) {
        assertEquals(expected.size(), output.size());
        for (var expectedKey : expected.keySet()) {
            assertTrue(output.containsKey(expectedKey));
            if (expected.get(expectedKey) == null) {
                assertNull(output.get(expectedKey));
            }
            else {
                assertEquals(expected.get(expectedKey).toString(),
                        output.get(expectedKey).toString());
            }
        }
    }

    @Test
    public void testReadWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> handler.read(null));
        assertThrows(IllegalArgumentException.class, () -> handler.read(""));
    }
}
