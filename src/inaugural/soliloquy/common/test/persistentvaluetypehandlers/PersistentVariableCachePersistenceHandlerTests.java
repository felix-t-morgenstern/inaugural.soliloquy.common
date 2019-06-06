package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentVariableCachePersistenceHandler;
import inaugural.soliloquy.common.test.stubs.PersistentValuesHandlerStub;
import inaugural.soliloquy.common.test.stubs.PersistentVariableCacheFactoryStub;
import inaugural.soliloquy.common.test.stubs.PersistentVariableCacheStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.common.specs.*;

import static org.junit.jupiter.api.Assertions.*;

class PersistentVariableCachePersistenceHandlerTests {
    private final IPersistentValuesHandler PERSISTENT_VALUES_HANDLER =
            new PersistentValuesHandlerStub();
    private final IPersistentVariableCacheFactory PERSISTENT_VARIABLE_CACHE_FACTORY =
            new PersistentVariableCacheFactoryStub();
    private final IPersistentVariableCache PERSISTENT_VARIABLE_CACHE =
            new PersistentVariableCacheStub();

    private final String VALUES_STRING =
            "[{\"name\":\"variable1\",\"typeName\":\"java.lang.Integer\",\"valueString\":\"456456\"}," +
                    "{\"name\":\"variable2\",\"typeName\":\"java.lang.String\",\"valueString\":\"variable2value\"}]";

    private IPersistentValueTypeHandler<IPersistentVariableCache>
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
        assertEquals(IPersistentValueTypeHandler.class.getCanonicalName() + "<" +
                IPersistentVariableCache.class.getCanonicalName() + ">",
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
        IPersistentVariableCache pVarCache =
                _persistentVariablePersistentCachePersistenceHandler.read(VALUES_STRING);

        assertNotNull(pVarCache);
        assertEquals(2, pVarCache.size());
        IMap<String,Object> representation = pVarCache.getVariablesRepresentation();
        assertEquals(PersistentVariableCacheStub.VARIABLE_1_VALUE,
                representation.get(PersistentVariableCacheStub.VARIABLE_1_NAME));
        assertEquals(PersistentVariableCacheStub.VARIABLE_2_VALUE,
                representation.get(PersistentVariableCacheStub.VARIABLE_2_NAME));
    }

    @Test
    void testReadEmptyCache() {
        IPersistentVariableCache pVarCache =
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
