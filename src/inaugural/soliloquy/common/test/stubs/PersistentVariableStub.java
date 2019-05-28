package inaugural.soliloquy.common.test.stubs;

import soliloquy.common.specs.IPersistentVariable;

public class PersistentVariableStub implements IPersistentVariable {
    private final String NAME;
    private final Object VALUE;

    public PersistentVariableStub(String name, Object value) {
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
