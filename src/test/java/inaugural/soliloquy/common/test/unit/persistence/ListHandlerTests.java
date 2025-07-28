package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.ListHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.common.persistence.PersistenceHandler;
import soliloquy.specs.common.persistence.TypeHandler;

import java.util.List;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.random.Random.randomInt;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListHandlerTests {
    private final Integer VALUE_1 = randomInt();
    private final Integer VALUE_2 = randomInt();
    private final Integer VALUE_3 = randomInt();
    private final String VALUES_STRING =
            String.format("{\"type\":\"%s\",\"values\":[\"%d\",\"%d\",\"%d\"]}",
                    Integer.class.getCanonicalName(),
                    VALUE_1, VALUE_2, VALUE_3);
    private final String VALUES_STRING_SECOND_NULL =
            String.format("{\"type\":\"%s\",\"values\":[\"%d\",null,\"%d\"]}",
                    Integer.class.getCanonicalName(),
                    VALUE_1, VALUE_3);
    private final String VALUES_ONLY_NULL = "{\"values\":[null,null,null]}";

    @SuppressWarnings("rawtypes") @Mock private TypeHandler mockTypeHandler;
    @Mock private PersistenceHandler mockPersistenceHandler;

    @SuppressWarnings("rawtypes") private TypeHandler<List> handler;

    @BeforeEach
    public void setUp() {
        //noinspection unchecked
        lenient().when(mockPersistenceHandler.getTypeHandler(anyString()))
                .thenReturn(mockTypeHandler);
        lenient().when(mockTypeHandler.read(anyString()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        //noinspection unchecked
        lenient().when(mockTypeHandler.write(any()))
                .thenAnswer(invocation -> invocation.getArgument(0).toString());

        handler = new ListHandler(mockPersistenceHandler);
    }

    @Test
    public void testConstructorWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> new ListHandler(null));
    }

    @Test
    public void testWrite() {
        var output = handler.write(listOf(VALUE_1, VALUE_2, VALUE_3));

        assertEquals(VALUES_STRING, output);
        verify(mockPersistenceHandler, times(1))
                .getTypeHandler(Integer.class.getCanonicalName());
        //noinspection unchecked
        verify(mockTypeHandler, times(1)).write(VALUE_1);
        //noinspection unchecked
        verify(mockTypeHandler, times(1)).write(VALUE_2);
        //noinspection unchecked
        verify(mockTypeHandler, times(1)).write(VALUE_3);
    }

    @Test
    public void testWriteWithNullEntry() {
        var output = handler.write(listOf(VALUE_1, null, VALUE_3));

        assertEquals(VALUES_STRING_SECOND_NULL, output);
        verify(mockPersistenceHandler, times(1))
                .getTypeHandler(Integer.class.getCanonicalName());
        //noinspection unchecked
        verify(mockTypeHandler, times(1)).write(VALUE_1);
        //noinspection unchecked
        verify(mockTypeHandler, times(1)).write(VALUE_3);
    }

    @Test
    public void testWriteWithAllNullEntries() {
        var output = handler.write(listOf(null, null, null));

        assertEquals(VALUES_ONLY_NULL, output);
        verify(mockPersistenceHandler, never()).getTypeHandler(anyString());
        //noinspection unchecked
        verify(mockTypeHandler, never()).write(any());
    }

    @Test
    public void testWriteWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> handler.write(null));
    }

    @Test
    public void testRead() {
        when(mockTypeHandler.read(anyString()))
                .thenReturn(VALUE_1).thenReturn(VALUE_2).thenReturn(VALUE_3);

        var list = handler.read(VALUES_STRING);

        assertNotNull(list);
        assertEquals(listOf(VALUE_1, VALUE_2, VALUE_3), list);
        verify(mockPersistenceHandler, times(1))
                .getTypeHandler(Integer.class.getCanonicalName());
        verify(mockTypeHandler, times(1)).read(VALUE_1.toString());
        verify(mockTypeHandler, times(1)).read(VALUE_2.toString());
        verify(mockTypeHandler, times(1)).read(VALUE_3.toString());
    }

    @Test
    public void testReadWithNullEntry() {
        when(mockTypeHandler.read(anyString()))
                .thenReturn(VALUE_1).thenReturn(VALUE_3);

        var list = handler.read(VALUES_STRING_SECOND_NULL);

        assertNotNull(list);
        assertEquals(listOf(VALUE_1, null, VALUE_3), list);
        verify(mockPersistenceHandler, times(1))
                .getTypeHandler(Integer.class.getCanonicalName());
        verify(mockTypeHandler, times(2)).read(anyString());
        verify(mockTypeHandler, times(1)).read(VALUE_1.toString());
        verify(mockTypeHandler, never()).read(VALUE_2.toString());
        verify(mockTypeHandler, times(1)).read(VALUE_3.toString());
    }

    @Test
    public void testReadWithAllNullEntries() {
        var output = handler.read(VALUES_ONLY_NULL);

        assertNotNull(output);
        assertEquals(listOf(null, null, null), output);
        verify(mockPersistenceHandler, never()).getTypeHandler(anyString());
        verify(mockTypeHandler, never()).read(anyString());
    }

    @Test
    public void testReadWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> handler.read(null));
        assertThrows(IllegalArgumentException.class, () -> handler.read(""));
    }
}
