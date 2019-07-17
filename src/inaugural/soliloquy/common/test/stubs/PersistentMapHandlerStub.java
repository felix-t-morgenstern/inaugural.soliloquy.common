package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.PersistentMapHandler;

public class PersistentMapHandlerStub implements PersistentMapHandler {
    @Override
    public Map read(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String write(Map Map) {
        return null;
    }

    @Override
    public Map getArchetype() {
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
    public Map generateArchetype(String s) throws IllegalArgumentException {
        return null;
    }
}
