package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentVariableCachePersistenceHandler;
import inaugural.soliloquy.common.test.stubs.PersistentValuesHandlerStub;
import inaugural.soliloquy.common.test.stubs.PersistentVariableCacheFactoryStub;
import inaugural.soliloquy.common.test.stubs.PersistentVariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.PersistentVariableCacheFactory;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.common.infrastructure.PersistentValuesHandler;
import soliloquy.specs.common.infrastructure.PersistentVariableCache;
import soliloquy.specs.common.infrastructure.ReadOnlyMap;

import static org.junit.jupiter.api.Assertions.*;

class PersistentVariableCachePersistenceHandlerTests {
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER =
            new PersistentValuesHandlerStub();
    private final PersistentVariableCacheFactory PERSISTENT_VARIABLE_CACHE_FACTORY =
            new PersistentVariableCacheFactoryStub();
    private final PersistentVariableCache PERSISTENT_VARIABLE_CACHE =
            new PersistentVariableCacheStub();

    private final String VALUES_STRING =
            "[{\"name\":\"variable1\",\"typeName\":\"java.lang.Integer\",\"serializedValue\":\"456456\"}," +
                    "{\"name\":\"variable2\",\"typeName\":\"java.lang.String\",\"serializedValue\":\"variable2value\"}]";

    private PersistentValueTypeHandler<PersistentVariableCache>
            _persistentVariablePersistentCachePersistenceHandler;

    @BeforeEach
    void setUp() {
        _persistentVariablePersistentCachePersistenceHandler =
                new PersistentVariableCachePersistenceHandler(
                        PERSISTENT_VALUES_HANDLER,
                        PERSISTENT_VARIABLE_CACHE_FACTORY);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(PersistentValueTypeHandler.class.getCanonicalName() + "<" +
                PersistentVariableCache.class.getCanonicalName() + ">",
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
        PersistentVariableCache pVarCache =
                _persistentVariablePersistentCachePersistenceHandler.read(VALUES_STRING);

        assertNotNull(pVarCache);
        assertEquals(2, pVarCache.size());
        ReadOnlyMap<String,Object> representation = pVarCache.variablesRepresentation();
        assertEquals(PersistentVariableCacheStub.VARIABLE_1_VALUE,
                representation.get(PersistentVariableCacheStub.VARIABLE_1_NAME));
        assertEquals(PersistentVariableCacheStub.VARIABLE_2_VALUE,
                representation.get(PersistentVariableCacheStub.VARIABLE_2_NAME));
    }

    @Test
    void testReadEmptyCache() {
        PersistentVariableCache pVarCache =
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
