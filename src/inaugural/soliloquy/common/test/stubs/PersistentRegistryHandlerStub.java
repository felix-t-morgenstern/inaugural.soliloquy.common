package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.infrastructure.PersistentRegistryHandler;
import soliloquy.specs.common.infrastructure.Registry;

public class PersistentRegistryHandlerStub implements PersistentRegistryHandler {
    @Override
    public Registry generateArchetype(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Registry read(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String write(Registry registry) {
        return null;
    }

    @Override
    public Registry getArchetype() {
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
