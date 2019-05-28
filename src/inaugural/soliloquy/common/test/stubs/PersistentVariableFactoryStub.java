package inaugural.soliloquy.common.test.stubs;


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
}
