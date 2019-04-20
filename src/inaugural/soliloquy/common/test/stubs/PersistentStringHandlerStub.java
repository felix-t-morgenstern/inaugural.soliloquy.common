package inaugural.soliloquy.common.test.stubs;

import soliloquy.common.specs.IPersistentValueTypeHandler;

public class PersistentStringHandlerStub implements IPersistentValueTypeHandler<String> {
    @Override
    public String read(String s) throws IllegalArgumentException {
        return s;
    }

    @Override
    public String write(String s) {
        return s;
    }

    @Override
    public String getArchetype() {
        return null;
    }

    @Override
    public String getUnparameterizedInterfaceName() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
