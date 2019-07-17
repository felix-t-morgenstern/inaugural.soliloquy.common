package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.factories.PersistentVariableCacheFactory;
import soliloquy.specs.common.infrastructure.PersistentVariableCache;

public class PersistentVariableCacheFactoryStub implements PersistentVariableCacheFactory {
    @Override
    public PersistentVariableCache make() {
        return new PersistentVariableCacheStub();
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
