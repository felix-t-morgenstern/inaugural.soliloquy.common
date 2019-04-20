package inaugural.soliloquy.common.test.stubs;

import soliloquy.common.specs.IMap;
import soliloquy.common.specs.IPersistentValueTypeHandler;

public class PersistentMapHandlerStub implements IPersistentValueTypeHandler<IMap> {
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
}
