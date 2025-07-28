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
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class PersistenceHandlerImplTests {
    @Mock private TypeHandler<Integer> mockIntegerHandler;
    @Mock private TypeHandler<String> mockStringHandler;

    private PersistenceHandlerImpl persistenceHandler;

    @BeforeEach
    public void setUp() {
        persistenceHandler = new PersistenceHandlerImpl();
    }

    @Test
    public void testAddAndGetTypeHandler() {
        persistenceHandler.addTypeHandler(Integer.class, mockIntegerHandler);

        assertSame(mockIntegerHandler,
                persistenceHandler.getTypeHandler(Integer.class.getCanonicalName()));
    }

    @Test
    public void testAddAndGetTypeHandlerWithTypeParameters() {
        persistenceHandler.addTypeHandler(Integer.class, mockIntegerHandler);

        assertSame(mockIntegerHandler,
                persistenceHandler.getTypeHandler(Integer.class.getCanonicalName()));
    }

    @Test
    public void testGetTypeHandlerWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () ->
                persistenceHandler.getTypeHandler(null));
        assertThrows(IllegalArgumentException.class, () ->
                persistenceHandler.getTypeHandler(""));
        assertThrows(IllegalArgumentException.class, () ->
                persistenceHandler.getTypeHandler(Float.class.getCanonicalName()));
    }

    @Test
    public void testAddTypeHandlerTwiceException() {
        assertThrows(IllegalArgumentException.class, () -> {
            persistenceHandler.addTypeHandler(Integer.class, mockIntegerHandler);
            persistenceHandler.addTypeHandler(Integer.class, mockIntegerHandler);
        });
    }

    @Test
    public void testRemoveTypeHandler() {
        assertFalse(persistenceHandler.removeTypeHandler(Integer.class));

        persistenceHandler.addTypeHandler(Integer.class, mockIntegerHandler);

        assertTrue(persistenceHandler.removeTypeHandler(Integer.class));
    }

    @Test
    public void testAddGetAndRemoveTypeHandlerWithInvalidArgs() {
        assertThrows(IllegalArgumentException.class,
                () -> persistenceHandler.addTypeHandler(null, mockIntegerHandler));
        assertThrows(IllegalArgumentException.class,
                () -> persistenceHandler.addTypeHandler(Integer.class, null));
        assertThrows(IllegalArgumentException.class,
                () -> persistenceHandler.removeTypeHandler(null));
        assertThrows(IllegalArgumentException.class,
                () -> persistenceHandler.getTypeHandler(null));
        assertThrows(IllegalArgumentException.class,
                () -> persistenceHandler.getTypeHandler(""));
    }

    @Test
    public void testTypesHandled() {
        assertTrue(persistenceHandler.typesHandled().isEmpty());

        persistenceHandler.addTypeHandler(Integer.class, mockIntegerHandler);
        persistenceHandler.addTypeHandler(String.class, mockStringHandler);

        assertNotSame(persistenceHandler.typesHandled(), persistenceHandler.typesHandled());
        assertEquals(2, persistenceHandler.typesHandled().size());
        assertTrue(persistenceHandler.typesHandled().contains(Integer.class));
        assertTrue(persistenceHandler.typesHandled().contains(String.class));
    }

    @Test
    public void testHashCode() {
        assertEquals(PersistenceHandlerImpl.class.getCanonicalName().hashCode(),
                persistenceHandler.hashCode());
    }

    @Test
    public void testEquals() {
        var equalPersistenceHandler = new PersistenceHandlerImpl();
        var unequalPersistenceHandler = mock(PersistenceHandler.class);

        assertEquals(persistenceHandler, equalPersistenceHandler);
        assertNotEquals(persistenceHandler, unequalPersistenceHandler);
        assertNotEquals(null, persistenceHandler);
    }

    @Test
    public void testToString() {
        assertEquals(PersistenceHandlerImpl.class.getCanonicalName(),
                persistenceHandler.toString());
    }
}
