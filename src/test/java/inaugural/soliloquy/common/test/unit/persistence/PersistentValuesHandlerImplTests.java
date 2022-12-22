package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.PersistentValuesHandlerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.persistence.TypeWithOneGenericParamHandler;
import soliloquy.specs.common.persistence.TypeWithTwoGenericParamsHandler;

import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PersistentValuesHandlerImplTests {
    @Mock @SuppressWarnings("rawtypes") private TypeHandler mockIntegerHandler;
    @Mock @SuppressWarnings("rawtypes") private TypeHandler mockStringHandler;
    @Mock @SuppressWarnings("rawtypes") private TypeWithOneGenericParamHandler mockListHandler;
    @Mock @SuppressWarnings("rawtypes") private TypeWithTwoGenericParamsHandler mockMapHandler;

    private PersistentValuesHandlerImpl persistentValuesHandler;

    @BeforeEach
    void setUp() {
        mockIntegerHandler = mock(TypeHandler.class);
        when(mockIntegerHandler.getInterfaceName())
                .thenReturn("<" + Integer.class.getCanonicalName() + ">");

        mockStringHandler = mock(TypeHandler.class);
        when(mockStringHandler.getInterfaceName())
                .thenReturn("<" + String.class.getCanonicalName() + ">");

        mockListHandler = mock(TypeWithOneGenericParamHandler.class);
        when(mockListHandler.getInterfaceName())
                .thenReturn("<" + List.class.getCanonicalName() + ">");

        mockMapHandler = mock(TypeWithTwoGenericParamsHandler.class);
        when(mockMapHandler.getInterfaceName())
                .thenReturn("<" + Map.class.getCanonicalName() + ">");

        persistentValuesHandler = new PersistentValuesHandlerImpl();
    }

    @Test
    void testAddAndGetTypeHandler() {
        persistentValuesHandler.addTypeHandler(mockIntegerHandler);

        assertSame(mockIntegerHandler,
                persistentValuesHandler.getTypeHandler(Integer.class.getCanonicalName()));
    }

    @Test
    void testAddAndGetTypeHandlerWithTypeParameters() {
        persistentValuesHandler.addTypeHandler(mockListHandler);

        assertSame(mockListHandler, persistentValuesHandler
                .getTypeHandler(List.class.getCanonicalName() + "<" + randomString() + ">"));
    }

    @Test
    void testGetTypeHandlerWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                persistentValuesHandler.getTypeHandler(null));
        assertThrows(IllegalArgumentException.class, () ->
                persistentValuesHandler.getTypeHandler(""));
        assertThrows(IllegalArgumentException.class, () ->
                persistentValuesHandler.getTypeHandler(Integer.class.getCanonicalName()));
    }

    @Test
    void testAddTypeHandlerTwiceException() {
        assertThrows(IllegalArgumentException.class, () -> {
            persistentValuesHandler.addTypeHandler(mockIntegerHandler);
            persistentValuesHandler.addTypeHandler(mockIntegerHandler);
        });
    }

    @Test
    void testRemoveTypeHandler() {
        assertFalse(persistentValuesHandler.removeTypeHandler(Integer.class.getCanonicalName()));
        persistentValuesHandler.addTypeHandler(mockIntegerHandler);
        assertSame(persistentValuesHandler.<Integer>getTypeHandler(
                Integer.class.getCanonicalName()),
                mockIntegerHandler);
        assertTrue(persistentValuesHandler.removeTypeHandler(Integer.class.getCanonicalName()));
    }

    @Test
    void testAddGetAndRemoveTypeHandlerWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> persistentValuesHandler.addTypeHandler(null));
        assertThrows(IllegalArgumentException.class,
                () -> persistentValuesHandler.removeTypeHandler(null));
        assertThrows(IllegalArgumentException.class,
                () -> persistentValuesHandler.removeTypeHandler(""));
        assertThrows(IllegalArgumentException.class,
                () -> persistentValuesHandler.getTypeHandler(null));
        assertThrows(IllegalArgumentException.class,
                () -> persistentValuesHandler.getTypeHandler(""));
    }

    @Test
    void testTypesHandled() {
        assertTrue(persistentValuesHandler.typesHandled().isEmpty());
        persistentValuesHandler.addTypeHandler(mockIntegerHandler);
        persistentValuesHandler.addTypeHandler(mockStringHandler);
        assertEquals(2, persistentValuesHandler.typesHandled().size());
        assertTrue(persistentValuesHandler.typesHandled()
                .contains(Integer.class.getCanonicalName()));
        assertTrue(persistentValuesHandler.typesHandled()
                .contains(String.class.getCanonicalName()));
    }

    @Test
    void testGenerateArchetype() {
        persistentValuesHandler.addTypeHandler(mockIntegerHandler);
        Integer archetypeFromTypeHandler = randomInt();
        when(mockIntegerHandler.getArchetype()).thenReturn(archetypeFromTypeHandler);

        Integer generatedArchetype = persistentValuesHandler.generateArchetype(Integer.class.getCanonicalName());

        assertNotNull(generatedArchetype);
        assertEquals(archetypeFromTypeHandler, generatedArchetype);
    }

    @Test
    void testGenerateArchetypeWithOneGenericParameter() {
        String innerType = randomString();
        //noinspection rawtypes
        List generatedArchetypeOutput = mock(List.class);
        when(mockListHandler.generateArchetype(anyString())).thenReturn(generatedArchetypeOutput);
        persistentValuesHandler.addTypeHandler(mockListHandler);

        //noinspection rawtypes
        List generatedArchetype = persistentValuesHandler
                .generateArchetype(List.class.getCanonicalName() + "<" + innerType + ">");

        assertSame(generatedArchetypeOutput, generatedArchetype);
        verify(mockListHandler, times(1)).generateArchetype(innerType);
    }

    @Test
    void testGenerateArchetypeWithTwoGenericParameters() {
        String innerType1 = randomString();
        String innerType2 = randomString();
        //noinspection rawtypes
        Map generatedArchetypeOutput = mock(Map.class);
        when(mockMapHandler.generateArchetype(anyString(), anyString()))
                .thenReturn(generatedArchetypeOutput);
        persistentValuesHandler.addTypeHandler(mockMapHandler);

        //noinspection rawtypes
        Map generatedArchetype = persistentValuesHandler.generateArchetype(
                Map.class.getCanonicalName() + "<" + innerType1 + "," + innerType2 + ">");

        assertSame(generatedArchetypeOutput, generatedArchetype);
        verify(mockMapHandler, times(1)).generateArchetype(innerType1, innerType2);
    }

    @Test
    void testGenerateArchetypeWithInvalidParams() {
        persistentValuesHandler.addTypeHandler(mockIntegerHandler);
        persistentValuesHandler.addTypeHandler(mockListHandler);
        persistentValuesHandler.addTypeHandler(mockMapHandler);

        assertThrows(IllegalArgumentException.class, () ->
                persistentValuesHandler.generateArchetype(null));
        assertThrows(IllegalArgumentException.class, () ->
                persistentValuesHandler.generateArchetype(""));
        assertThrows(IllegalArgumentException.class, () ->
                persistentValuesHandler.generateArchetype("not a valid class"));
        assertThrows(IllegalArgumentException.class, () ->
                persistentValuesHandler.generateArchetype(List.class.getCanonicalName()));
        assertThrows(IllegalArgumentException.class, () ->
                persistentValuesHandler.generateArchetype(List.class.getCanonicalName() + "<"));
        assertThrows(IllegalArgumentException.class, () ->
                persistentValuesHandler.generateArchetype(List.class.getCanonicalName() +
                        "<not a valid class"));
        assertThrows(IllegalArgumentException.class, () ->
                persistentValuesHandler.generateArchetype(List.class.getCanonicalName() +
                        "<" + Integer.class.getCanonicalName()));
        assertThrows(IllegalArgumentException.class, () ->
                persistentValuesHandler.generateArchetype(Map.class.getCanonicalName() + "<"));
        assertThrows(IllegalArgumentException.class, () ->
                persistentValuesHandler.generateArchetype(Map.class.getCanonicalName() +
                        "<not a valid class"));
        assertThrows(IllegalArgumentException.class, () ->
                persistentValuesHandler.generateArchetype(Map.class.getCanonicalName() +
                        "<" + Integer.class.getCanonicalName()));
        assertThrows(IllegalArgumentException.class, () ->
                persistentValuesHandler.generateArchetype(Map.class.getCanonicalName() +
                        "<" + Integer.class.getCanonicalName() + ","));
        assertThrows(IllegalArgumentException.class, () ->
                persistentValuesHandler.generateArchetype(Map.class.getCanonicalName() +
                        "<" + Integer.class.getCanonicalName() + ",not a valid class"));
        assertThrows(IllegalArgumentException.class, () ->
                persistentValuesHandler.generateArchetype(Map.class.getCanonicalName() +
                        "<" + Integer.class.getCanonicalName() + "," +
                        Integer.class.getCanonicalName()));
    }

    @Test
    void testHashCode() {
        assertEquals(PersistentValuesHandlerImpl.class.getCanonicalName().hashCode(),
                persistentValuesHandler.hashCode());
    }

    @Test
    void testEquals() {
        PersistentValuesHandler equalPersistentValuesHandler = new PersistentValuesHandlerImpl();
        PersistentValuesHandler unequalPersistentValuesHandler = mock(PersistentValuesHandler.class);

        assertEquals(persistentValuesHandler, equalPersistentValuesHandler);
        assertNotEquals(persistentValuesHandler, unequalPersistentValuesHandler);
        assertNotEquals(null, persistentValuesHandler);
    }

    @Test
    void testToString() {
        assertEquals(PersistentValuesHandlerImpl.class.getCanonicalName(),
                persistentValuesHandler.toString());
    }
}
