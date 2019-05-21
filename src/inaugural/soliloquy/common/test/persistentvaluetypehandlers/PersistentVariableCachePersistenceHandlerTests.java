package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentVariableCachePersistenceHandler;
import inaugural.soliloquy.common.test.stubs.PersistentVariableCacheFactoryStub;
import inaugural.soliloquy.common.test.stubs.PersistentVariableFactoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.common.specs.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersistentVariableCachePersistenceHandlerTests {
    private final IPersistentValuesHandler PERSISTENT_VALUES_HANDLER =
            new PersistentValuesHandlerStub();
    private final IPersistentVariableCacheFactory PERSISTENT_VARIABLE_CACHE_FACTORY =
            new PersistentVariableCacheFactoryStub();
    private final IPersistentVariableFactory PERSISTENT_VARIABLE_FACTORY =
            new PersistentVariableFactoryStub();

    private IPersistentValueTypeHandler<IPersistentVariableCache>
            _persistentVariablePersistentCachePersistenceHandler;

    @BeforeEach
    void setUp() {
        _persistentVariablePersistentCachePersistenceHandler =
                new PersistentVariableCachePersistenceHandler(
                        PERSISTENT_VALUES_HANDLER,
                        PERSISTENT_VARIABLE_CACHE_FACTORY,
                        PERSISTENT_VARIABLE_FACTORY);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(IPersistentValueTypeHandler.class.getCanonicalName() + "<" +
                IPersistentVariableCache.class.getCanonicalName() + ">",
                _persistentVariablePersistentCachePersistenceHandler.getInterfaceName());
    }

    @Test
    void testRead() {
        // TODO: Test and implement!
    }

    private class PersistentValuesHandlerStub implements IPersistentValuesHandler {

        @Override
        public void addPersistentValueTypeHandler(IPersistentValueTypeHandler<?> persistentValueTypeHandler)
                throws IllegalArgumentException {
            // Stub method; not implemented
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removePersistentValueTypeHandler(String persistentValueType) {
            // Stub method; not implemented
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> IPersistentValueTypeHandler<T> getPersistentValueTypeHandler(String persistentValueType)
                throws UnsupportedOperationException {
            // Stub method; not implemented
            throw new UnsupportedOperationException();
        }

        @Override
        public Object generateArchetype(String s) throws IllegalArgumentException {
            // Stub method; not implemented
            throw new UnsupportedOperationException();
        }

        @Override
        public ICollection<String> persistentValueTypesHandled() {
            // Stub method; not implemented
            throw new UnsupportedOperationException();
        }

        @Override
        public void registerPersistentPairHandler(IPersistentPairHandler iPersistentPairHandler) {

        }

        @Override
        public void registerPersistentCollectionHandler(IPersistentCollectionHandler iPersistentCollectionHandler) {

        }

        @Override
        public void registerPersistentMapHandler(IPersistentMapHandler iPersistentMapHandler) {

        }

        @Override
        public String getInterfaceName() {
            // Stub; not implemented
            throw new UnsupportedOperationException();
        }

    }
}
