package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.PersistentVariableCacheHandler;
import inaugural.soliloquy.common.test.fakes.FakePersistentValuesHandler;
import inaugural.soliloquy.common.test.fakes.FakePersistentVariableCacheHandler;
import inaugural.soliloquy.common.test.fakes.FakeVariableCache;
import inaugural.soliloquy.common.test.fakes.FakeVariableCacheFactory;
import inaugural.soliloquy.tools.persistence.PersistentTypeHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.PersistentValueTypeHandler;
import soliloquy.specs.common.persistence.PersistentValuesHandler;

import static org.junit.jupiter.api.Assertions.*;

class PersistentVariableCacheHandlerTests {
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER =
            new FakePersistentValuesHandler();
    private final VariableCacheFactory VARIABLE_CACHE_FACTORY =
            new FakeVariableCacheFactory();
    private final VariableCache PERSISTENT_VARIABLE_CACHE =
            new FakeVariableCache();

    private final String VALUES_STRING =
            "[{\"name\":\"variable1\",\"typeName\":\"java.lang.Integer\",\"serializedValue\":\"456456\"}," +
                    "{\"name\":\"variable2\",\"typeName\":\"java.lang.String\",\"serializedValue\":\"variable2value\"}]";

    private PersistentValueTypeHandler<VariableCache>
            _persistentVariablePersistentCachePersistenceHandler;

    @BeforeEach
    void setUp() {
        _persistentVariablePersistentCachePersistenceHandler =
                new PersistentVariableCacheHandler(
                        PERSISTENT_VALUES_HANDLER,
                        VARIABLE_CACHE_FACTORY);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(PersistentValueTypeHandler.class.getCanonicalName() + "<" +
                VariableCache.class.getCanonicalName() + ">",
                _persistentVariablePersistentCachePersistenceHandler.getInterfaceName());
    }

    @Test
    void testWrite() {
        assertEquals(VALUES_STRING,
                _persistentVariablePersistentCachePersistenceHandler
                        .write(PERSISTENT_VARIABLE_CACHE));
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _persistentVariablePersistentCachePersistenceHandler.write(null));
    }

    @Test
    void testRead() {
        VariableCache pVarCache =
                _persistentVariablePersistentCachePersistenceHandler.read(VALUES_STRING);

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
                _persistentVariablePersistentCachePersistenceHandler.read("[]");

        assertNotNull(pVarCache);
        assertEquals(0, pVarCache.size());
    }

    @Test
    void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _persistentVariablePersistentCachePersistenceHandler.read(null));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentVariablePersistentCachePersistenceHandler.read(""));
    }

    @Test
    void testHashCode() {
        assertEquals((PersistentValueTypeHandler.class.getCanonicalName() + "<" +
                        VariableCache.class.getCanonicalName() + ">").hashCode(),
                _persistentVariablePersistentCachePersistenceHandler.hashCode());
    }

    @Test
    void testEquals() {
        PersistentTypeHandler<VariableCache> equalHandler =
                new PersistentVariableCacheHandler(PERSISTENT_VALUES_HANDLER,
                        VARIABLE_CACHE_FACTORY);
        PersistentValueTypeHandler<VariableCache> unequalHandler =
                new FakePersistentVariableCacheHandler();

        assertEquals(_persistentVariablePersistentCachePersistenceHandler, equalHandler);
        assertNotEquals(_persistentVariablePersistentCachePersistenceHandler, unequalHandler);
        assertNotEquals(null, _persistentVariablePersistentCachePersistenceHandler);
    }

    @Test
    void testToString() {
        assertEquals(PersistentValueTypeHandler.class.getCanonicalName() + "<" +
                        VariableCache.class.getCanonicalName() + ">",
                _persistentVariablePersistentCachePersistenceHandler.toString());
    }
}
