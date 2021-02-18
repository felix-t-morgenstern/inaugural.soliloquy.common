package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.common.infrastructure.VariableCache;

public class FakePersistentVariableCacheHandler
        implements PersistentValueTypeHandler<VariableCache> {
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
