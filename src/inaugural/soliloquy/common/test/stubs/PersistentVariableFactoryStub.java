package inaugural.soliloquy.common.test.stubs;


import inaugural.soliloquy.common.test.persistentvaluetypehandlers.PersistentVariableCachePersistenceHandlerTests;
import soliloquy.common.specs.IPersistentVariable;
import soliloquy.common.specs.IPersistentVariableFactory;

public class PersistentVariableFactoryStub implements IPersistentVariableFactory {

    @Override
    public <T> IPersistentVariable make(String name, T value) {
        return new PersistentVariableStub(name, value);
    }

    @Override
    public String getInterfaceName() {
        // Stub; not implemented
        throw new UnsupportedOperationException();
    }

    private class PersistentVariableStub implements IPersistentVariable {
        private final String NAME;
        private final Object VALUE;

        private PersistentVariableStub(String name, Object value) {
            NAME = name;
            VALUE = value;
        }

        @Override
        public String getName() {
            return NAME;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> T getValue() {
            return (T) VALUE;
        }

        @Override
        public <T> void setValue(T value) {
            // Stub method; not implemented
            throw new UnsupportedOperationException();
        }

        @Override
        public String getInterfaceName() {
            // Stub; not implemented
            throw new UnsupportedOperationException();
        }

    }
}
