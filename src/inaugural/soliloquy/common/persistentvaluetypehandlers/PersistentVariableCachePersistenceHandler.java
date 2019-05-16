package inaugural.soliloquy.common.persistentvaluetypehandlers;

import soliloquy.common.specs.*;
import soliloquy.game.primary.specs.IGame;
import soliloquy.logger.specs.ILogger;

public class PersistentVariableCachePersistenceHandler
        extends PersistentTypeHandler<IPersistentVariableCache>
        implements IPersistentValueTypeHandler<IPersistentVariableCache> {
    private final IPersistentVariableCache ARCHETYPE = new PersistentVariableCacheStub();

    public PersistentVariableCachePersistenceHandler() {
    }

    @Override
    public IPersistentVariableCache getArchetype() {
        return ARCHETYPE;
    }

    @Override
    public IPersistentVariableCache read(String data) throws IllegalArgumentException {
        return null;
//        PERSISTENT_VALUES_HANDLER.readValues(data, PROCESS_READ_VALUE_ACTION, overridePreviousData);
    }

    @Override
    public String write(IPersistentVariableCache iPersistentVariableCache) {
//        IPersistentValueToWrite collectionArchetype = new PersistentValueToWrite("", "");
//        ICollection<IPersistentValueToWrite<?>> persistentValuesToWrite = COLLECTION_FACTORY
//                .make(collectionArchetype);
//        for (Entry<String, IPersistentVariable> entry : PERSISTENT_VARIABLES.entrySet()) {
//            persistentValuesToWrite.add(new PersistentValueToWrite(entry.getValue().getName(),
//                    entry.getValue().getValue()));
//        }
//        return PERSISTENT_VALUES_HANDLER.writeValues(persistentValuesToWrite);
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

    protected class PersistentValueToWrite<T> implements IPersistentValueToWrite<T> {
        public String typeName;
        public String name;
        public T value;

        public PersistentValueToWrite(String name, T value)
        {
            this.typeName = value instanceof IHasGenericParams ?
                    ((ISoliloquyClass) value).getInterfaceName() :
                    value.getClass().getCanonicalName();
            this.name = name;
            this.value = value;
        }

        @Override
        public String typeName() {
            return this.typeName;
        }

        @Override
        public String name() {
            return this.name;
        }

        @Override
        public T value() {
            return this.value;
        }

        @Override
        public String getInterfaceName() {
            // Stub method
            throw new UnsupportedOperationException();
        }

        @Override
        public T getArchetype() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getUnparameterizedInterfaceName() {
            throw new UnsupportedOperationException();
        }

    }

    private class ProcessReadValueAction
            implements IAction<IPair<IPersistentValueToWrite<?>,IPersistentVariableCache>> {
        private final IPersistentVariableFactory PERSISTENT_VARIABLE_FACTORY;

        private ProcessReadValueAction(IPersistentVariableFactory persistentVariableFactory) {
            PERSISTENT_VARIABLE_FACTORY = persistentVariableFactory;
        }

        @Override
        public String id() throws IllegalStateException {
            // Stub method; not implemented
            throw new UnsupportedOperationException();
        }

        @Override
        public IPair<IPersistentValueToWrite<?>, IPersistentVariableCache> getArchetype() {
            // Stub method; not implemented
            throw new UnsupportedOperationException();
        }

        @Override
        public String getInterfaceName() {
            // Stub method; not implemented
            throw new UnsupportedOperationException();
        }

        @SuppressWarnings("rawtypes")
        @Override
        public void run(IPair<IPersistentValueToWrite<?>, IPersistentVariableCache> input)
                throws IllegalArgumentException {
            IPersistentValueToWrite inputValue = input.getItem1();
            IPersistentVariableCache cache = input.getItem2();

            IPersistentVariable persistentVariable = PERSISTENT_VARIABLE_FACTORY
                    .make(inputValue.name(), inputValue.value());
            cache.put(persistentVariable);
        }

        @Override
        public String getUnparameterizedInterfaceName() {
            // Stub method; not implemented
            throw new UnsupportedOperationException();
        }

        @Override
        public IGame game() {
            // Stub method; not implemented
            throw new UnsupportedOperationException();
        }

        @Override
        public ILogger logger() {
            // Stub method; not implemented
            throw new UnsupportedOperationException();
        }
    }
}
