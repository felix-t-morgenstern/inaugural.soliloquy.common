package inaugural.soliloquy.common.factories;

import inaugural.soliloquy.common.infrastructure.VariableCacheImpl;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;

public class VariableCacheFactoryImpl implements VariableCacheFactory {

    @Override
    public VariableCache make() {
        return new VariableCacheImpl();
    }

    @Override
    public String getInterfaceName() {
        return VariableCacheFactory.class.getCanonicalName();
    }

    @Override
    public int hashCode() {
        return VariableCacheFactoryImpl.class.getCanonicalName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof VariableCacheFactoryImpl && obj.hashCode() == hashCode();
    }

    @Override
    public String toString() {
        return VariableCacheFactoryImpl.class.getCanonicalName();
    }
}
