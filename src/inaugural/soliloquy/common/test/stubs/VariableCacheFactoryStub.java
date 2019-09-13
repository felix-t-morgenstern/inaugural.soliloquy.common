package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;


public class VariableCacheFactoryStub implements VariableCacheFactory {
    @Override
    public VariableCache make() {
        return new VariableCacheStub();
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
