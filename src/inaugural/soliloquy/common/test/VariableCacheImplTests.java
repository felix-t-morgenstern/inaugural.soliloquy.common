package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.test.fakes.FakeMapFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.VariableCacheImpl;
import inaugural.soliloquy.common.test.fakes.FakeListFactory;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.VariableCache;

import static org.junit.jupiter.api.Assertions.*;

class VariableCacheImplTests {
    private VariableCache _variableCache;

    private final ListFactory LIST_FACTORY = new FakeListFactory();
    private final MapFactory MAP_FACTORY = new FakeMapFactory();

    @BeforeEach
    void setUp() {
        // archetype not necessary for test suite
        _variableCache = new VariableCacheImpl(LIST_FACTORY, MAP_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new VariableCacheImpl(null, MAP_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new VariableCacheImpl(LIST_FACTORY, null));
    }
    
    @Test
    void testPutAndSize() {
        _variableCache.setVariable("variable1", "value1");
        _variableCache.setVariable("variable2", "value2");
        _variableCache.setVariable("variable3", "value3");

        assertEquals(3, _variableCache.size());
    }

    @Test
    void testPutAndGet() {
        assertNull(_variableCache.getVariable("variable1"));

        _variableCache.setVariable("variable1", "value1");

        assertEquals("value1", _variableCache.getVariable("variable1"));
    }

    @Test
    void testSetGetAndRemoveWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _variableCache.setVariable(null, 0));
        assertThrows(IllegalArgumentException.class, () -> _variableCache.setVariable("", 0));
        assertThrows(IllegalArgumentException.class,
                () -> _variableCache.setVariable("variable", null));
        assertThrows(IllegalArgumentException.class,
                () -> _variableCache.setVariable("variable", ""));
        assertThrows(IllegalArgumentException.class, () -> _variableCache.getVariable(null));
        assertThrows(IllegalArgumentException.class, () -> _variableCache.getVariable(""));
        assertThrows(IllegalArgumentException.class, () -> _variableCache.remove(null));
        assertThrows(IllegalArgumentException.class, () -> _variableCache.remove(""));
    }

    @Test
    void testNamesRepresentation() {
        _variableCache.setVariable("variable1", "value1");
        _variableCache.setVariable("variable2", "value2");
        _variableCache.setVariable("variable3", "value3");

        List<String> namesRepresentation = _variableCache.namesRepresentation();

        assertNotNull(namesRepresentation);
        assertEquals(3, namesRepresentation.size());
        assertTrue(namesRepresentation.contains("variable1"));
        assertTrue(namesRepresentation.contains("variable2"));
        assertTrue(namesRepresentation.contains("variable3"));
    }

    @Test
    void testRemove() {
        assertFalse(_variableCache.remove("variable1"));

        _variableCache.setVariable("variable1", "value1");

        assertTrue(_variableCache.remove("variable1"));
        assertFalse(_variableCache.remove("variable1"));
        assertEquals(0, _variableCache.size());
    }

    @Test
    void testClear() {
        assertEquals(0, _variableCache.size());

        _variableCache.setVariable("variable1", "value1");
        _variableCache.setVariable("variable2", "value2");
        _variableCache.setVariable("variable3", "value3");

        assertEquals(3, _variableCache.size());

        _variableCache.clear();

        assertEquals(0, _variableCache.size());
    }

    @Test
    void testVariablesRepresentation() {
        _variableCache.setVariable("variable1", "value1");
        _variableCache.setVariable("variable2", "value2");
        _variableCache.setVariable("variable3", "value3");

        Map<String,Object> variablesRepresentation = _variableCache.variablesRepresentation();

        assertNotNull(variablesRepresentation);
        assertNotNull(variablesRepresentation.getFirstArchetype());
        assertNotNull(variablesRepresentation.getSecondArchetype());
        assertEquals(3, variablesRepresentation.size());
        assertEquals("value1", variablesRepresentation.get("variable1"));
        assertEquals("value2", variablesRepresentation.get("variable2"));
        assertEquals("value3", variablesRepresentation.get("variable3"));
    }

    @Test
    void testMakeClone() {
        _variableCache.setVariable("variable1", "value1");
        _variableCache.setVariable("variable2", "value2");
        _variableCache.setVariable("variable3", "value3");

        VariableCache clone = _variableCache.makeClone();
        _variableCache.setVariable("variable1", "value4");

        assertNotNull(clone);
        assertEquals("value1", clone.getVariable("variable1"));
        assertEquals("value2", clone.getVariable("variable2"));
        assertEquals("value3", clone.getVariable("variable3"));
    }
}
