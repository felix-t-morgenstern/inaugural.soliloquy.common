package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.persistence.PersistentMapHandler;

public class FakePersistentMapHandler implements PersistentMapHandler {
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
    public String getInterfaceName() {
        return null;
    }

    @Override
    public Map generateArchetype(String s) throws IllegalArgumentException {
        return null;
    }
}
