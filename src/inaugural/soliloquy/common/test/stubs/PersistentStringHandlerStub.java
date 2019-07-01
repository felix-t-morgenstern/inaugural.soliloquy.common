package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.infrastructure.IPersistentValueTypeHandler;

public class PersistentStringHandlerStub implements IPersistentValueTypeHandler<String> {
    public final static String ARCHETYPE = "This is the archetype!";

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
        return ARCHETYPE;
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
