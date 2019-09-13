package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentVariableCacheHandler;
import inaugural.soliloquy.common.test.stubs.PersistentValuesHandlerStub;
import inaugural.soliloquy.common.test.stubs.VariableCacheFactoryStub;
import inaugural.soliloquy.common.test.stubs.VariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.common.infrastructure.PersistentValuesHandler;
import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.infrastructure.ReadableMap;

import static org.junit.jupiter.api.Assertions.*;

class PersistentVariableCacheHandlerTests {
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER =
            new PersistentValuesHandlerStub();
    private final VariableCacheFactory PERSISTENT_VARIABLE_CACHE_FACTORY =
            new VariableCacheFactoryStub();
    private final VariableCache PERSISTENT_VARIABLE_CACHE =
            new VariableCacheStub();

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
                        PERSISTENT_VARIABLE_CACHE_FACTORY);
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
        ReadableMap<String,Object> representation = pVarCache.variablesRepresentation();
        assertEquals(VariableCacheStub.VARIABLE_1_VALUE,
                representation.get(VariableCacheStub.VARIABLE_1_NAME));
        assertEquals(VariableCacheStub.VARIABLE_2_VALUE,
                representation.get(VariableCacheStub.VARIABLE_2_NAME));
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
}
