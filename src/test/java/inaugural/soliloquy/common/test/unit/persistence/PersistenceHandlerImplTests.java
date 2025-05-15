package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.PersistenceHandlerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.common.persistence.PersistenceHandler;
import soliloquy.specs.common.persistence.TypeHandler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class PersistenceHandlerImplTests {
    @Mock @SuppressWarnings("rawtypes") private TypeHandler mockIntegerHandler;
    @Mock @SuppressWarnings("rawtypes") private TypeHandler mockStringHandler;

    private PersistenceHandlerImpl PersistenceHandler;

    @BeforeEach
    public void setUp() {
        lenient().when(mockIntegerHandler.typeHandled())
                .thenReturn(Integer.class.getCanonicalName());

        lenient().when(mockStringHandler.typeHandled())
                .thenReturn(String.class.getCanonicalName());

        PersistenceHandler = new PersistenceHandlerImpl();
    }

    @Test
    public void testAddAndGetTypeHandler() {
        PersistenceHandler.addTypeHandler(mockIntegerHandler);

        assertSame(mockIntegerHandler,
                PersistenceHandler.getTypeHandler(Integer.class.getCanonicalName()));
    }

    @Test
    public void testAddAndGetTypeHandlerWithTypeParameters() {
        PersistenceHandler.addTypeHandler(mockIntegerHandler);

        assertSame(mockIntegerHandler,
                PersistenceHandler.getTypeHandler(Integer.class.getCanonicalName()));
    }

    @Test
    public void testGetTypeHandlerWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () ->
                PersistenceHandler.getTypeHandler(null));
        assertThrows(IllegalArgumentException.class, () ->
                PersistenceHandler.getTypeHandler(""));
        assertThrows(IllegalArgumentException.class, () ->
                PersistenceHandler.getTypeHandler(Float.class.getCanonicalName()));
    }

    @Test
    public void testAddTypeHandlerTwiceException() {
        assertThrows(IllegalArgumentException.class, () -> {
            PersistenceHandler.addTypeHandler(mockIntegerHandler);
            PersistenceHandler.addTypeHandler(mockIntegerHandler);
        });
    }

    @Test
    public void testRemoveTypeHandler() {
        assertFalse(PersistenceHandler.removeTypeHandler(Integer.class.getCanonicalName()));
        PersistenceHandler.addTypeHandler(mockIntegerHandler);
        assertSame(PersistenceHandler.<Integer>getTypeHandler(
                        Integer.class.getCanonicalName()),
                mockIntegerHandler);
        assertTrue(PersistenceHandler.removeTypeHandler(Integer.class.getCanonicalName()));
    }

    @Test
    public void testAddGetAndRemoveTypeHandlerWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> PersistenceHandler.addTypeHandler(null));
        assertThrows(IllegalArgumentException.class,
                () -> PersistenceHandler.removeTypeHandler(null));
        assertThrows(IllegalArgumentException.class,
                () -> PersistenceHandler.removeTypeHandler(""));
        assertThrows(IllegalArgumentException.class,
                () -> PersistenceHandler.getTypeHandler(null));
        assertThrows(IllegalArgumentException.class,
                () -> PersistenceHandler.getTypeHandler(""));
    }

    @Test
    public void testTypesHandled() {
        assertTrue(PersistenceHandler.typesHandled().isEmpty());

        PersistenceHandler.addTypeHandler(mockIntegerHandler);
        PersistenceHandler.addTypeHandler(mockStringHandler);

        assertEquals(2, PersistenceHandler.typesHandled().size());
        assertTrue(PersistenceHandler.typesHandled()
                .contains(Integer.class.getCanonicalName()));
        assertTrue(PersistenceHandler.typesHandled()
                .contains(String.class.getCanonicalName()));
    }

    @Test
    public void testHashCode() {
        assertEquals(PersistenceHandlerImpl.class.getCanonicalName().hashCode(),
                PersistenceHandler.hashCode());
    }

    @Test
    public void testEquals() {
        var equalPersistenceHandler = new PersistenceHandlerImpl();
        var unequalPersistenceHandler = mock(PersistenceHandler.class);

        assertEquals(PersistenceHandler, equalPersistenceHandler);
        assertNotEquals(PersistenceHandler, unequalPersistenceHandler);
        assertNotEquals(null, PersistenceHandler);
    }

    @Test
    public void testToString() {
        assertEquals(PersistenceHandlerImpl.class.getCanonicalName(),
                PersistenceHandler.toString());
    }
}
