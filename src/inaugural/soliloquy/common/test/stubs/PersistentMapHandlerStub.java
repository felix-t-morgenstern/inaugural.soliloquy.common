package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.infrastructure.IMap;
import soliloquy.specs.common.infrastructure.IPersistentMapHandler;

public class PersistentMapHandlerStub implements IPersistentMapHandler {
    @Override
    public IMap read(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String write(IMap iMap) {
        return null;
    }

    @Override
    public IMap getArchetype() {
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
    public IMap generateArchetype(String s) throws IllegalArgumentException {
        return null;
    }
}
