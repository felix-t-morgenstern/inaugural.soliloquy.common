package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.PersistentValuesHandlerImpl;
import inaugural.soliloquy.common.test.fakes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.persistence.PersistentValuesHandler;

import static org.junit.jupiter.api.Assertions.*;

class PersistentValuesHandlerImplTests {
    private final FakeIntegerHandler INTEGER_HANDLER = new FakeIntegerHandler();
    private final FakeStringHandler STRING_HANDLER = new FakeStringHandler();
    private final FakeListHandler LIST_HANDLER = new FakeListHandler();
    private final FakeMapHandler MAP_HANDLER = new FakeMapHandler();

    private PersistentValuesHandlerImpl _persistentValuesHandler;

    @BeforeEach
    void setUp() {
        _persistentValuesHandler = new PersistentValuesHandlerImpl();
    }

    @Test
    void testAddAndGetTypeHandler() {
        _persistentValuesHandler.addTypeHandler(INTEGER_HANDLER);

        assertSame(_persistentValuesHandler.<Integer>getTypeHandler(
                Integer.class.getCanonicalName()),
                INTEGER_HANDLER);
    }

    @Test
    void testAddAndGetTypeHandlerWithTypeParameters() {
        _persistentValuesHandler.addTypeHandler(LIST_HANDLER);

        assertSame(LIST_HANDLER,
                _persistentValuesHandler.getTypeHandler(List.class.getCanonicalName() +
                        "<irrelevant>"));
    }

    @Test
    void testGetTypeHandlerWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _persistentValuesHandler.getTypeHandler(null));
        assertThrows(IllegalArgumentException.class, () ->
                _persistentValuesHandler.getTypeHandler(""));
        assertThrows(IllegalArgumentException.class, () ->
                _persistentValuesHandler.getTypeHandler(Integer.class.getCanonicalName()));
    }

    @Test
    void testAddTypeHandlerTwiceException() {
        assertThrows(IllegalArgumentException.class, () -> {
            _persistentValuesHandler.addTypeHandler(INTEGER_HANDLER);
            _persistentValuesHandler.addTypeHandler(INTEGER_HANDLER);
        });
    }

    @Test
    void testRemoveTypeHandler() {
        assertFalse(_persistentValuesHandler.removeTypeHandler(Integer.class.getCanonicalName()));
        _persistentValuesHandler.addTypeHandler(INTEGER_HANDLER);
        assertSame(_persistentValuesHandler.<Integer>getTypeHandler(
                Integer.class.getCanonicalName()),
                INTEGER_HANDLER);
        assertTrue(_persistentValuesHandler.removeTypeHandler(Integer.class.getCanonicalName()));
    }

    @Test
    void testAddGetAndRemoveTypeHandlerWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _persistentValuesHandler.addTypeHandler(null));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentValuesHandler.removeTypeHandler(null));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentValuesHandler.removeTypeHandler(""));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentValuesHandler.getTypeHandler(null));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentValuesHandler.getTypeHandler(""));
    }

    @Test
    void testTypesHandled() {
        assertTrue(_persistentValuesHandler.typesHandled().isEmpty());
        _persistentValuesHandler.addTypeHandler(INTEGER_HANDLER);
        _persistentValuesHandler.addTypeHandler(STRING_HANDLER);
        assertEquals(2, _persistentValuesHandler.typesHandled().size());
        assertTrue(_persistentValuesHandler.typesHandled()
                .contains(Integer.class.getCanonicalName()));
        assertTrue(_persistentValuesHandler.typesHandled()
                .contains(String.class.getCanonicalName()));
    }

    @Test
    void testGenerateArchetype() {
        _persistentValuesHandler.addTypeHandler(INTEGER_HANDLER);

        assertNotNull(_persistentValuesHandler
                .generateArchetype(Integer.class.getCanonicalName()));
    }

    @Test
    void testGenerateArchetypeWithOneGenericParameter() {
        String innerType = "innerType";
        //noinspection rawtypes
        FakeList generatedArchetypeOutput = new FakeList();
        LIST_HANDLER.GenerateArchetypeOutput = generatedArchetypeOutput;
        _persistentValuesHandler.addTypeHandler(LIST_HANDLER);

        //noinspection rawtypes
        List generatedArchetype = _persistentValuesHandler
                .generateArchetype(List.class.getCanonicalName() + "<" + innerType + ">");

        assertSame(generatedArchetypeOutput, generatedArchetype);
        assertEquals(innerType, LIST_HANDLER.GenerateArchetypeInput);
    }

    @Test
    void testGenerateArchetypeWithTwoGenericParameters() {
        String innerType1 = "innerType1";
        String innerType2 = "innerType2";
        //noinspection rawtypes
        FakeMap generatedArchetypeOutput = new FakeMap();
        MAP_HANDLER.GenerateArchetypeOutput = generatedArchetypeOutput;
        _persistentValuesHandler.addTypeHandler(MAP_HANDLER);

        //noinspection rawtypes
        Map generatedArchetype = _persistentValuesHandler.generateArchetype(
                Map.class.getCanonicalName() + "<" + innerType1 + "," + innerType2 + ">");

        assertSame(generatedArchetypeOutput, generatedArchetype);
        assertEquals(innerType1, MAP_HANDLER.GenerateArchetypeInput1);
        assertEquals(innerType2, MAP_HANDLER.GenerateArchetypeInput2);
    }

    @Test
    void testGenerateArchetypeWithInvalidParams() {
        _persistentValuesHandler.addTypeHandler(INTEGER_HANDLER);
        _persistentValuesHandler.addTypeHandler(LIST_HANDLER);
        _persistentValuesHandler.addTypeHandler(MAP_HANDLER);

        assertThrows(IllegalArgumentException.class, () ->
                _persistentValuesHandler.generateArchetype(null));
        assertThrows(IllegalArgumentException.class, () ->
                _persistentValuesHandler.generateArchetype(""));
        assertThrows(IllegalArgumentException.class, () ->
                _persistentValuesHandler.generateArchetype("not a valid class"));
        assertThrows(IllegalArgumentException.class, () ->
                _persistentValuesHandler.generateArchetype(List.class.getCanonicalName()));
        assertThrows(IllegalArgumentException.class, () ->
                _persistentValuesHandler.generateArchetype(List.class.getCanonicalName() + "<"));
        assertThrows(IllegalArgumentException.class, () ->
                _persistentValuesHandler.generateArchetype(List.class.getCanonicalName() +
                        "<not a valid class"));
        assertThrows(IllegalArgumentException.class, () ->
                _persistentValuesHandler.generateArchetype(List.class.getCanonicalName() +
                        "<" + Integer.class.getCanonicalName()));
        assertThrows(IllegalArgumentException.class, () ->
                _persistentValuesHandler.generateArchetype(Map.class.getCanonicalName() + "<"));
        assertThrows(IllegalArgumentException.class, () ->
                _persistentValuesHandler.generateArchetype(Map.class.getCanonicalName() +
                        "<not a valid class"));
        assertThrows(IllegalArgumentException.class, () ->
                _persistentValuesHandler.generateArchetype(Map.class.getCanonicalName() +
                        "<" + Integer.class.getCanonicalName()));
        assertThrows(IllegalArgumentException.class, () ->
                _persistentValuesHandler.generateArchetype(Map.class.getCanonicalName() +
                        "<" + Integer.class.getCanonicalName() + ","));
        assertThrows(IllegalArgumentException.class, () ->
                _persistentValuesHandler.generateArchetype(Map.class.getCanonicalName() +
                        "<" + Integer.class.getCanonicalName() + ",not a valid class"));
        assertThrows(IllegalArgumentException.class, () ->
                _persistentValuesHandler.generateArchetype(Map.class.getCanonicalName() +
                        "<" + Integer.class.getCanonicalName() + "," +
                        Integer.class.getCanonicalName()));
    }

    @Test
    void testHashCode() {
        assertEquals(PersistentValuesHandlerImpl.class.getCanonicalName().hashCode(),
                _persistentValuesHandler.hashCode());
    }

    @Test
    void testEquals() {
        PersistentValuesHandler equalPersistentValuesHandler = new PersistentValuesHandlerImpl();
        PersistentValuesHandler unequalPersistentValuesHandler = new FakePersistentValuesHandler();

        assertEquals(_persistentValuesHandler, equalPersistentValuesHandler);
        assertNotEquals(_persistentValuesHandler, unequalPersistentValuesHandler);
        assertNotEquals(null, _persistentValuesHandler);
    }

    @Test
    void testToString() {
        assertEquals(PersistentValuesHandlerImpl.class.getCanonicalName(),
                _persistentValuesHandler.toString());
    }
}
