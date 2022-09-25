package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.infrastructure.VariableCache;
import soliloquy.specs.common.persistence.TypeHandler;

public class FakeVariableCacheHandler implements TypeHandler<VariableCache> {
    @Override
    public VariableCache read(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String write(VariableCache variableCache) {
        return null;
    }

    @Override
    public VariableCache getArchetype() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
