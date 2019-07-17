package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;

public class PersistentStringHandlerStub implements PersistentValueTypeHandler<String> {
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
