package inaugural.soliloquy.common.test.stubs;

import soliloquy.common.specs.IPair;
import soliloquy.common.specs.IPersistentPairHandler;

public class PersistentPairHandlerStub implements IPersistentPairHandler {
    @Override
    public IPair read(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String write(IPair iPair) {
        return null;
    }

    @Override
    public IPair getArchetype() {
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
    public IPair generateArchetype(String s) throws IllegalArgumentException {
        return null;
    }
}
