package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;


public class FakeVariableCacheFactory implements VariableCacheFactory {
    @Override
    public VariableCache make() {
        return new FakeVariableCache();
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
