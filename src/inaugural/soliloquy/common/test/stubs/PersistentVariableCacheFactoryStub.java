package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.entities.IPersistentVariableCache;
import soliloquy.specs.common.factories.IPersistentVariableCacheFactory;

public class PersistentVariableCacheFactoryStub implements IPersistentVariableCacheFactory {
    @Override
    public IPersistentVariableCache make() {
        return new PersistentVariableCacheStub();
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
