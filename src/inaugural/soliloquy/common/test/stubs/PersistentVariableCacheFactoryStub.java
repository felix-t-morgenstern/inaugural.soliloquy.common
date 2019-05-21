package inaugural.soliloquy.common.test.stubs;

import soliloquy.common.specs.IPersistentVariableCache;
import soliloquy.common.specs.IPersistentVariableCacheFactory;

public class PersistentVariableCacheFactoryStub implements IPersistentVariableCacheFactory {
    @Override
    public IPersistentVariableCache make() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
