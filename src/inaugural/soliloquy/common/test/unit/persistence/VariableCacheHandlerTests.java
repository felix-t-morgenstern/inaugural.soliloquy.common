package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.VariableCacheHandler;
import inaugural.soliloquy.common.test.fakes.FakePersistentValuesHandler;
import inaugural.soliloquy.common.test.fakes.FakeVariableCacheHandler;
import inaugural.soliloquy.common.test.fakes.FakeVariableCache;
import inaugural.soliloquy.common.test.fakes.FakeVariableCacheFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class VariableCacheHandlerTests {
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER =
            new FakePersistentValuesHandler();
    private final VariableCacheFactory VARIABLE_CACHE_FACTORY =
            new FakeVariableCacheFactory();
    private final VariableCache PERSISTENT_VARIABLE_CACHE =
            new FakeVariableCache();

    private final String VALUES_STRING =
            "[{\"name\":\"variable1\",\"typeName\":\"java.lang.Integer\",\"serializedValue\":\"456456\"}," +
                    "{\"name\":\"variable2\",\"typeName\":\"java.lang.String\",\"serializedValue\":\"variable2value\"}]";

    private TypeHandler<VariableCache> _variableCacheHandler;

    @BeforeEach
    void setUp() {
        _variableCacheHandler = new VariableCacheHandler(
                        PERSISTENT_VALUES_HANDLER,
                        VARIABLE_CACHE_FACTORY);
    }

    // TODO: Test constructor with invalid params

    @Test
    void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                VariableCache.class.getCanonicalName() + ">",
                _variableCacheHandler.getInterfaceName());
    }

    @Test
    void testWrite() {
        assertEquals(VALUES_STRING, _variableCacheHandler.write(PERSISTENT_VARIABLE_CACHE));
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _variableCacheHandler.write(null));
    }

    @Test
    void testRead() {
        VariableCache pVarCache =
                _variableCacheHandler.read(VALUES_STRING);

        assertNotNull(pVarCache);
        assertEquals(2, pVarCache.size());
        Map<String,Object> representation = pVarCache.variablesRepresentation();
        assertEquals(FakeVariableCache.VARIABLE_1_VALUE,
                representation.get(FakeVariableCache.VARIABLE_1_NAME));
        assertEquals(FakeVariableCache.VARIABLE_2_VALUE,
                representation.get(FakeVariableCache.VARIABLE_2_NAME));
    }

    @Test
    void testReadEmptyCache() {
        VariableCache pVarCache =
                _variableCacheHandler.read("[]");

        assertNotNull(pVarCache);
        assertEquals(0, pVarCache.size());
    }

    @Test
    void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _variableCacheHandler.read(null));
        assertThrows(IllegalArgumentException.class,
                () -> _variableCacheHandler.read(""));
    }

    @Test
    void testHashCode() {
        assertEquals((TypeHandler.class.getCanonicalName() + "<" +
                        VariableCache.class.getCanonicalName() + ">").hashCode(),
                _variableCacheHandler.hashCode());
    }

    @Test
    void testEquals() {
        TypeHandler<VariableCache> equalHandler = new VariableCacheHandler(
                PERSISTENT_VALUES_HANDLER, VARIABLE_CACHE_FACTORY);
        TypeHandler<VariableCache> unequalHandler = new FakeVariableCacheHandler();

        assertEquals(_variableCacheHandler, equalHandler);
        assertNotEquals(_variableCacheHandler, unequalHandler);
        assertNotEquals(null, _variableCacheHandler);
    }

    @Test
    void testToString() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        VariableCache.class.getCanonicalName() + ">",
                _variableCacheHandler.toString());
    }
}
