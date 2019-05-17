package inaugural.soliloquy.common.persistentvaluetypehandlers;

import soliloquy.common.specs.*;

public class PersistentVariableCachePersistenceHandler
        extends PersistentTypeHandler<IPersistentVariableCache>
        implements IPersistentValueTypeHandler<IPersistentVariableCache> {
    private final IPersistentVariableCache ARCHETYPE = new PersistentVariableCacheStub();
    private final IPersistentValuesHandler PERSISTENT_VALUES_HANDLER;
    private final IPersistentVariableCacheFactory PERSISTENT_VARIABLE_CACHE_FACTORY;
    private final IPersistentVariableFactory PERSISTENT_VARIABLE_FACTORY;

    private final String DELIMITER_OUTER = "\u000e";
    private final String DELIMITER_INNER = "\u000f";

    public PersistentVariableCachePersistenceHandler(
            IPersistentValuesHandler persistentValuesHandler,
            IPersistentVariableCacheFactory persistentVariableCacheFactory,
            IPersistentVariableFactory persistentVariableFactory) {
        PERSISTENT_VALUES_HANDLER = persistentValuesHandler;
        PERSISTENT_VARIABLE_CACHE_FACTORY = persistentVariableCacheFactory;
        PERSISTENT_VARIABLE_FACTORY = persistentVariableFactory;
    }

    @Override
    public IPersistentVariableCache getArchetype() {
        return ARCHETYPE;
    }

    @Override
    public IPersistentVariableCache read(String data) throws IllegalArgumentException {
        IPersistentVariableCache persistentVariableCache = PERSISTENT_VARIABLE_CACHE_FACTORY.make();
        // TODO: Complete this
        return null;
    }

    @Override
    public String write(IPersistentVariableCache iPersistentVariableCache) {
        return null;
    }

    private class PersistentVariableCacheStub implements IPersistentVariableCache {

        @Override
        public void put(IPersistentVariable iPersistentVariable) throws IllegalArgumentException {

        }

        @Override
        public boolean remove(String s) {
            return false;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public ICollection<String> persistentVariableNamesRepresentation() {
            return null;
        }

        @Override
        public void clear() {

        }

        @Override
        public IPersistentVariable get(String s) {
            return null;
        }

        @Override
        public String getInterfaceName() {
            return IPersistentVariableCache.class.getCanonicalName();
        }
    }
}
