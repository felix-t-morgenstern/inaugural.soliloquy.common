package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.PersistentPairHandler;

public class PersistentPairHandlerStub implements PersistentPairHandler {
    @Override
    public Pair read(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String write(Pair Pair) {
        return null;
    }

    @Override
    public Pair getArchetype() {
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

    @Override
    public Pair generateArchetype(String s) throws IllegalArgumentException {
        return null;
    }
}
