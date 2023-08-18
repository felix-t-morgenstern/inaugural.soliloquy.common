package inaugural.soliloquy.common.test.unit.infrastructure;

import inaugural.soliloquy.common.infrastructure.VariableCacheImpl;
import org.junit.Before;
import org.junit.Test;
import soliloquy.specs.common.infrastructure.VariableCache;

import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.Assert.*;

public class VariableCacheImplTests {
    private final String VARIABLE_1 = randomString();
    private final String VARIABLE_2 = randomString();
    private final String VARIABLE_3 = randomString();
    private final String VALUE_1 = randomString();
    private final String VALUE_2 = randomString();
    private final String VALUE_3 = randomString();

    private VariableCache variableCache;

    @Before
    public void setUp() {
        variableCache = new VariableCacheImpl();
    }

    @Test
    public void testPutAndSize() {
        variableCache.setVariable(VARIABLE_1, VALUE_1);
        variableCache.setVariable(VARIABLE_2, VALUE_2);
        variableCache.setVariable(VARIABLE_3, VALUE_3);

        assertEquals(3, variableCache.size());
    }

    @Test
    public void testPutAndGet() {
        assertNull(variableCache.getVariable(VARIABLE_1));

        variableCache.setVariable(VARIABLE_1, VALUE_1);

        assertEquals(VALUE_1, variableCache.getVariable(VARIABLE_1));
    }

    @Test
    public void testSetGetAndRemoveWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> variableCache.setVariable(null, 0));
        assertThrows(IllegalArgumentException.class, () -> variableCache.setVariable("", 0));
        assertThrows(IllegalArgumentException.class,
                () -> variableCache.setVariable(randomString(), null));
        assertThrows(IllegalArgumentException.class,
                () -> variableCache.setVariable(randomString(), ""));
        assertThrows(IllegalArgumentException.class, () -> variableCache.getVariable(null));
        assertThrows(IllegalArgumentException.class, () -> variableCache.getVariable(""));
        assertThrows(IllegalArgumentException.class, () -> variableCache.remove(null));
        assertThrows(IllegalArgumentException.class, () -> variableCache.remove(""));
    }

    @Test
    public void testNamesRepresentation() {
        variableCache.setVariable(VARIABLE_1, VALUE_1);
        variableCache.setVariable(VARIABLE_2, VALUE_2);
        variableCache.setVariable(VARIABLE_3, VALUE_3);

        var namesRepresentation = variableCache.namesRepresentation();

        assertNotNull(namesRepresentation);
        assertEquals(3, namesRepresentation.size());
        assertTrue(namesRepresentation.contains(VARIABLE_1));
        assertTrue(namesRepresentation.contains(VARIABLE_2));
        assertTrue(namesRepresentation.contains(VARIABLE_3));
    }

    @Test
    public void testRemove() {
        assertFalse(variableCache.remove(VARIABLE_1));

        variableCache.setVariable(VARIABLE_1, VALUE_1);

        assertTrue(variableCache.remove(VARIABLE_1));
        assertFalse(variableCache.remove(VARIABLE_1));
        assertEquals(0, variableCache.size());
    }

    @Test
    public void testClear() {
        assertEquals(0, variableCache.size());

        variableCache.setVariable(VARIABLE_1, VALUE_1);
        variableCache.setVariable(VARIABLE_2, VALUE_2);
        variableCache.setVariable(VARIABLE_3, VALUE_3);

        assertEquals(3, variableCache.size());

        variableCache.clear();

        assertEquals(0, variableCache.size());
    }

    @Test
    public void testVariablesRepresentation() {
        variableCache.setVariable(VARIABLE_1, VALUE_1);
        variableCache.setVariable(VARIABLE_2, VALUE_2);
        variableCache.setVariable(VARIABLE_3, VALUE_3);

        var variablesRepresentation = variableCache.variablesRepresentation();

        assertNotNull(variablesRepresentation);
        assertEquals(3, variablesRepresentation.size());
        assertEquals(VALUE_1, variablesRepresentation.get(VARIABLE_1));
        assertEquals(VALUE_2, variablesRepresentation.get(VARIABLE_2));
        assertEquals(VALUE_3, variablesRepresentation.get(VARIABLE_3));
    }

    @Test
    public void testMakeClone() {
        variableCache.setVariable(VARIABLE_1, VALUE_1);
        variableCache.setVariable(VARIABLE_2, VALUE_2);
        variableCache.setVariable(VARIABLE_3, VALUE_3);

        var clone = variableCache.makeClone();
        variableCache.setVariable(VARIABLE_1, randomString());

        assertNotNull(clone);
        assertEquals(VALUE_1, clone.getVariable(VARIABLE_1));
        assertEquals(VALUE_2, clone.getVariable(VARIABLE_2));
        assertEquals(VALUE_3, clone.getVariable(VARIABLE_3));
    }
}
